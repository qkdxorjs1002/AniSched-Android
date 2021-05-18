package com.novang.anisched.ui.recent.caption.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.anissia.RecentCaption;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.List;

public class RecentCaptionViewModel extends ViewModel {

    private final AnissiaRepository anissiaRepository;

    private MutableLiveData<List<RecentCaption>> captionList;

    public RecentCaptionViewModel() {
        anissiaRepository = new AnissiaRepository();
    }

    public void callRecentCaption() {
        anissiaRepository.requestRecentCaption().observeForever(recentCaptions -> {
            captionList.postValue(recentCaptions);
        });
    }

    public LiveData<List<RecentCaption>> getCaptionList() {
        if (captionList == null) {
            captionList = new MutableLiveData<>();
        }
        return captionList;
    }
}