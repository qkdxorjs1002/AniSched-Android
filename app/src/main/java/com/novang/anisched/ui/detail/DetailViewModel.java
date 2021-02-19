package com.novang.anisched.ui.detail;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.tmdb.Movie;
import com.novang.anisched.model.tmdb.TV;
import com.novang.anisched.model.tmdb.search.Result;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.tmdb.TMDBRepository;
import com.novang.anisched.tool.DynamicBackground;
import com.novang.anisched.tool.Levenshtein;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;
    private TMDBRepository tmdbRepository;

    public MutableLiveData<Anime> anissiaAnime;
    public MutableLiveData<Movie> tmdbMovie;
    public MutableLiveData<TV> tmdbTV;

    public MutableLiveData<Boolean> loadingStatus;
    public MutableLiveData<DynamicBackground> gradientBackground;

    public DetailViewModel() {
        anissiaRepository = new AnissiaRepository();
        tmdbRepository = new TMDBRepository();

        anissiaAnime = new MutableLiveData<>();
        tmdbMovie = new MutableLiveData<>();
        tmdbTV = new MutableLiveData<>();

        loadingStatus = new MutableLiveData<>(true);
        gradientBackground = new MutableLiveData<>();
    }

    public void dynamicBackground(Bitmap bitmap) {
        DynamicBackground dynamicBackground = DynamicBackground.generate(bitmap);

        if (dynamicBackground != null) {
            gradientBackground.postValue(dynamicBackground);
        }
    }

    public void callAnimeInfo(int id) {
        anissiaRepository.callAnimeInfo(id).observeForever(anime -> {
            anissiaAnime.postValue(anime);
        });
    }

    public void getDetail(String apiKey, String type, int id) {
        if (type.equals("movie")) {
            tmdbRepository.movieDetail(apiKey, "ko-KR", id).observeForever(movie -> {
                tmdbMovie.postValue(movie);
            });
        } else if (type.equals("tv")) {
            tmdbRepository.tvDetail(apiKey, "ko-KR", id).observeForever(tv -> {
                tmdbTV.postValue(tv);
            });
        }
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
                if (result.size() > 0) {
                    int idx = selectBestResult(result, originalKeyword);
                    if (idx != -1) {
                        getDetail(apiKey, result.get(idx).getMediaType(), result.get(idx).getId());
                    }
                } else {
                    if (filtered.matches(".*[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s].*")) {
                        searchTMDB(apiKey, filtered.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", " "), originalKeyword);
                    } else if (filtered.matches(".*[^\uAC00-\uD7A3xfe0-9\\s].*")) {
                        searchTMDB(apiKey, filtered.replaceAll("[^\uAC00-\uD7A3xfe0-9\\s]", " "), originalKeyword);
                    } else if (filtered.contains(" ")) {
                        searchTMDB(apiKey, filtered.replace(" ", ""), originalKeyword);
                    } else {
                        loadingStatus.postValue(false);
                    }
                }
            }
        });
    }

    private int selectBestResult(List<Result> result, String keyword) {
        int similar = -1, last_diff = 100;

        for (int idx = 0; result.size() > idx; idx++) {
            Result target = result.get(idx);

            if (target.getGenreIdList().contains(16)) {
                if (target.getMediaType().equals("tv") || target.getMediaType().equals("movie")) {
                    int diff = Levenshtein.getDistance(
                            target.getFlexibleName().replace(" ", ""),
                            keyword.replace(" ", ""));

                    if (last_diff >= diff) {
                        similar = idx;
                        last_diff = diff;
                    }
                }
            }
        }

        return similar;
    }
}