package com.novang.anisched.ui.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;

    public MutableLiveData<Anime> animeInfo;
    public MutableLiveData<List<Caption>> captionList;

    public DetailViewModel() {
        anissiaRepository = new AnissiaRepository();
        animeInfo = new MutableLiveData<>();
        captionList = new MutableLiveData<>();
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

}