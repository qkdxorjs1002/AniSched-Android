package com.novang.anisched.ui.schedule.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.List;

public class ScheduleViewModel extends ViewModel {

    private final AnissiaRepository anissiaRepository;

    private MutableLiveData<List<Anime>> animeList;

    public ScheduleViewModel() {
        anissiaRepository = new AnissiaRepository();
    }

    public void callSchedule(int week) {
        anissiaRepository.requestSchedule(week).observeForever(animes -> {
            animeList.postValue(animes);
        });
    }

    public LiveData<List<Anime>> getAnimeList() {
        if (animeList == null) {
            animeList = new MutableLiveData<>();
        }
        return animeList;
    }
}