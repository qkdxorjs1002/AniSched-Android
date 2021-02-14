package com.novang.anisched.ui.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.model.tmdb.Result;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.tmdb.TMDBRepository;

import java.util.List;

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
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        String keywordWoSp = keyword.replaceAll(match, " ");

        tmdbRepository.search(apiKey, "ko-KR", keywordWoSp).observeForever(searches -> {
            List<Result> result = searches.getResultList();

            if(result != null) {
                if(result.size() > 0) {
                    tmdbResult.postValue(searches.getResultList().get(0));
                }
            }
        });
    }
}