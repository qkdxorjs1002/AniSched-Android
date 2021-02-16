package com.novang.anisched.ui.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.tmdb.Result;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.tmdb.TMDBRepository;
import com.novang.anisched.tool.Levenshtein;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class DetailViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;
    private TMDBRepository tmdbRepository;

    public MutableLiveData<Anime> animeInfo;
    public MutableLiveData<List<Caption>> captionList;
    public MutableLiveData<Result> tmdbResult;

    public DetailViewModel() {
        anissiaRepository = new AnissiaRepository();
        tmdbRepository = new TMDBRepository();

        animeInfo = new MutableLiveData<>();
        captionList = new MutableLiveData<>();
        tmdbResult = new MutableLiveData<>();
    }

    public void callAnimeInfo(int id) {
        anissiaRepository.callAnimeInfo(id).observeForever(anime -> {
            animeInfo.postValue(anime);
        });
    }

    public void callCaption(int id) {
        anissiaRepository.callCaption(id).observeForever(captions -> {
            captionList.postValue(captions);
        });
    }

    public void searchTMDB(String apiKey, String keyword) {
        searchTMDB(apiKey, keyword, keyword);
    }

    private void searchTMDB(String apiKey, String keyword, String originalKeyword) {
        String filtered = keyword.replaceAll("\\s\\d기", "")
                .replace("OVA", "")
                .replace("OAD", "")
                .replaceAll("\\s시즌(\\d|\\s\\d|)", "")
                .replaceAll("\\s\\d[n-t][dhrt]", "")
                .replaceAll("\\sSeason\\s\\d", "")
                .replaceAll("[-~].*[-~]", " ");

        tmdbRepository.search(apiKey, "ko-KR", filtered).observeForever(searches -> {
            List<Result> result = searches.getResultList();

            if (result != null) {
                if (result.size() > 1) {
                    tmdbResult.postValue(selectBestResult(result, keyword));
                } else if (result.size() == 1) {
                    tmdbResult.postValue(result.get(0));
                } else {
                    if (filtered.matches(".*[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s].*")) {
                        searchTMDB(apiKey, filtered.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", " "), originalKeyword);
                    } else if (filtered.contains(" ")) {
                        searchTMDB(apiKey, filtered.replace(" ", ""), originalKeyword);
                    }
                }
            }
        });
    }

    private Result selectBestResult(List<Result> result, String keyword) {
        int similar = 0, last_diff = 100;

        for (int idx = 0; result.size() > idx; idx++) {
            Result target = result.get(idx);

            if (target.getMediaType().equals("tv") || target.getMediaType().equals("movie")) {
                int diff = Levenshtein.getDistance(target.getFlexibleName(), keyword)
                        + Levenshtein.getDistance(target.getFlexibleOriginalName(), keyword);

                if (last_diff >= diff) {
                    similar = idx;
                    last_diff = diff;
                }
            }
        }

        return result.get(similar);
    }
}