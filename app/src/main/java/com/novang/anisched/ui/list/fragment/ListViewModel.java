package com.novang.anisched.ui.list.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.Calendar;
import java.util.List;

public class ListViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;

    public MutableLiveData<List<Anime>> animeList;

    public ListViewModel() {
        anissiaRepository = new AnissiaRepository();
        animeList = new MutableLiveData<>();
    }

    public void callSchedule(int week) {
        anissiaRepository.callSchedule(week).observeForever(animes -> {
            animeList.postValue(animes);
        });
    }

}