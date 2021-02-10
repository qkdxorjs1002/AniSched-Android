package com.novang.anisched.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.Caption;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;

    public MutableLiveData<List<Anime>> animeList;
    public MutableLiveData<List<Caption>> captionList;

    public MainViewModel() {
        anissiaRepository = new AnissiaRepository();
        animeList = new MutableLiveData<>();
        captionList = new MutableLiveData<>();
    }

    public void callSchedule(int week) {
        anissiaRepository.callSchedule(week).observeForever(animes -> {
            animeList.postValue(animes);
        });
    }

    public void callCaption(int id) {
        anissiaRepository.callCaption(id).observeForever(captions -> {
            captionList.postValue(captions);
        });
    }

}