package com.novang.anisched.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Rank;
import com.novang.anisched.model.github.Release;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.github.GithubRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewModel extends ViewModel {

    GithubRepository githubRepository;
    AnissiaRepository anissiaRepository;

    MutableLiveData<Release> release;
    MutableLiveData<List<Rank>> rankList;
    MutableLiveData<Integer> rankPage;

    Timer rankTimer;

    public MainViewModel() {
        githubRepository = new GithubRepository("qkdxorjs1002", "AniSched-Android");
        anissiaRepository = new AnissiaRepository();

        release = new MutableLiveData<>();
        rankList = new MutableLiveData<>();
        rankPage = new MutableLiveData<>(0);

        rankTimer = new Timer();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        rankTimer.cancel();
        rankTimer.purge();
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

    public void startTimer(long delay, long period) {
        rankTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int page = rankPage.getValue();

                if (page >= 29) {
                    rankPage.postValue(0);
                } else {
                    rankPage.postValue(page + 1);
                }
            }
        }, delay, period);
    }

    public void restartTimer(long delay, long period) {
        rankTimer.cancel();
        rankTimer.purge();
        rankTimer = new Timer();
        startTimer(delay, period);
    }

}