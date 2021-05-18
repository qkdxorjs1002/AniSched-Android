package com.novang.anisched.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.AutoCorrect;
import com.novang.anisched.repository.anissia.AnissiaRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final AnissiaRepository anissiaRepository;

    private MutableLiveData<List<AutoCorrect>> autoCorrectList;

    public SearchViewModel() {
        anissiaRepository = new AnissiaRepository();
    }

    public void requestAutoCorrect(String query) {
        anissiaRepository.requestAutoCorrect(query).observeForever(autoCorrects -> {
            autoCorrectList.postValue(autoCorrects);
        });
    }

    public LiveData<List<AutoCorrect>> getAutoCorrectList() {
        if (autoCorrectList == null) {
            autoCorrectList = new MutableLiveData<>();
        }
        return autoCorrectList;
    }
}