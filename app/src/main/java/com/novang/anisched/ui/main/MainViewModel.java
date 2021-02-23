package com.novang.anisched.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.github.Release;
import com.novang.anisched.repository.github.GithubRepository;

public class MainViewModel extends ViewModel {

    GithubRepository githubRepository;

    MutableLiveData<Release> release;

    public MainViewModel() {
        githubRepository = new GithubRepository("qkdxorjs1002", "AniSched-Android");
        release = new MutableLiveData<>();
    }

    public void requestRelease(String versionName) {
        githubRepository.requestRelease().observeForever(releases -> {
            if (releases == null) {
                return;
            }
            Release release = releases.get(0);

            if (!versionName.equals(release.getTagName())) {
                this.release.postValue(release);
            }
        });
    }

}