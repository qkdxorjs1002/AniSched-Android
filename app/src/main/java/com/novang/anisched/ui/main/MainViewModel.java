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

    private GithubRepository githubRepository;
    private AnissiaRepository anissiaRepository;

    private MutableLiveData<Release> release;
    private MutableLiveData<List<Rank>> rankList;
    private MutableLiveData<Integer> rankPage;

    private Timer rankTimer;

    public MainViewModel() {
        githubRepository = new GithubRepository("qkdxorjs1002", "AniSched-Android");
        anissiaRepository = new AnissiaRepository();

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
        if (!versionName.contains("dev")) {
            return;
        }

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
        stopTimer();
        startTimer(delay, period);
    }

    public void stopTimer() {
        rankTimer.cancel();
        rankTimer.purge();
        rankTimer = new Timer();
    }

    public LiveData<Release> getRelease() {
        if (release == null) {
            release = new MutableLiveData<>();
        }
        return release;
    }

    public LiveData<List<Rank>> getRankList() {
        if (rankList == null) {
            rankList = new MutableLiveData<>();
        }
        return rankList;
    }

    public LiveData<Integer> getRankPage() {
        if (rankPage == null) {
            rankPage = new MutableLiveData<>(0);
        }
        return rankPage;
    }
}