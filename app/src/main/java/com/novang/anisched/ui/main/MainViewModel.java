package com.novang.anisched.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Rank;
import com.novang.anisched.model.github.Release;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.github.GithubRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    AnissiaRepository anissiaRepository;
    GithubRepository githubRepository;

    MutableLiveData<List<Rank>> rankList;
    MutableLiveData<Release> release;

    public MainViewModel() {
        anissiaRepository = new AnissiaRepository();
        githubRepository = new GithubRepository("qkdxorjs1002", "AniSched-Android");

        rankList = new MutableLiveData<>();
        release = new MutableLiveData<>();
    }

    public void requestRanking() {
        anissiaRepository.requestRanking(AnissiaRepository.FACTOR.WEEK).observeForever(ranks -> {
            rankList.postValue(ranks);
        });
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