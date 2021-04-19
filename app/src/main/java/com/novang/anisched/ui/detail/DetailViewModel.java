package com.novang.anisched.ui.detail;

import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.tmdb.Movie;
import com.novang.anisched.model.tmdb.TV;
import com.novang.anisched.model.tmdb.child.common.Video;
import com.novang.anisched.model.tmdb.child.search.Result;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.tmdb.TMDBHelper;
import com.novang.anisched.repository.tmdb.TMDBRepository;
import com.novang.anisched.tool.DynamicBackground;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private AnissiaRepository anissiaRepository;
    private TMDBRepository tmdbRepository;

    private MutableLiveData<Anime> anissiaAnime;
    private MutableLiveData<Movie> tmdbMovie;
    private MutableLiveData<TV> tmdbTV;
    private MutableLiveData<List<Video>> tmdbVideos;

    private MutableLiveData<String> mediaType;
    private MutableLiveData<Boolean> loadingStatus;
    private MutableLiveData<DynamicBackground> gradientBackground;

    public DetailViewModel() {
        anissiaRepository = new AnissiaRepository();
        tmdbRepository = new TMDBRepository();
    }

    public void dynamicBackground(Bitmap bitmap) {
        DynamicBackground dynamicBackground = DynamicBackground.generate(bitmap);

        if (dynamicBackground != null) {
            gradientBackground.postValue(dynamicBackground);
        }
    }

    public void requestAnime(int id) {
        anissiaRepository.requestAnime(id).observeForever(anime -> {
            anissiaAnime.postValue(anime);
        });
    }

    public void requestDetail(String apiKey, String type, int id) {
        if (type.equals("movie")) {
            tmdbRepository.requestMovie(apiKey, "ko-KR", id).observeForever(movie -> {
                tmdbMovie.postValue(movie);
                if (movie != null) {
                    mediaType.postValue("movie");
                }
            });
        } else if (type.equals("tv")) {
            tmdbRepository.requestTv(apiKey, "ko-KR", id).observeForever(tv -> {
                tmdbTV.postValue(tv);
                if (tv != null) {
                    mediaType.postValue("tv");
                }
            });
        }

        if (type.equals("movie") || type.equals("tv")) {
            requestVideos(apiKey, type, id);
        }
    }

    public void requestVideos(String apiKey, String type, int id) {
        requestVideos(apiKey, "ko-KR", type, id);
    }

    public void requestVideos(String apiKey, String lang, String type, int id) {
        tmdbRepository.requestVideos(apiKey, lang, type, id).observeForever(videos -> {
            List<Video> videoList = videos.getVideoList();

            if (videos.isNullOrEmpty(videoList)) {
                if (lang.equals("ko-KR")) {
                    requestVideos(apiKey, "ja-JP", type, id);
                } else if (lang.equals("ja-JP")) {
                    requestVideos(apiKey, "en-US", type, id);
                }
            } else {
                tmdbVideos.postValue(videos.getVideoList());
            }
        });
    }

    public void searchTMDB(String apiKey, Anime anime) {
        TMDBHelper tmdbHelper = new TMDBHelper(tmdbRepository, new TMDBHelper.OnResultListener() {
            @Override
            public void onFind(String apiKey, Result result) {
                requestDetail(apiKey, result.getMediaType(), result.getId());
            }

            @Override
            public void onFailed() {
                loadingStatus.postValue(false);
            }
        });

        tmdbHelper.searchWithFilter(apiKey, anime);
    }

    public LiveData<Anime> getAnissiaAnime() {
        if (anissiaAnime == null) {
            anissiaAnime = new MutableLiveData<>();
        }
        return anissiaAnime;
    }

    public LiveData<Movie> getTmdbMovie() {
        if (tmdbMovie == null) {
            tmdbMovie = new MutableLiveData<>();
        }
        return tmdbMovie;
    }

    public LiveData<TV> getTmdbTV() {
        if (tmdbTV == null) {
            tmdbTV = new MutableLiveData<>();
        }
        return tmdbTV;
    }

    public LiveData<List<Video>> getTmdbVideos() {
        if (tmdbVideos == null) {
            tmdbVideos = new MutableLiveData<>();
        }
        return tmdbVideos;
    }

    public LiveData<String> getMediaType() {
        if (mediaType == null) {
            mediaType = new MutableLiveData<>();
        }
        return mediaType;
    }

    public MutableLiveData<Boolean> getLoadingStatus() {
        if (loadingStatus == null) {
            loadingStatus = new MutableLiveData<>();
        }
        return loadingStatus;
    }

    public LiveData<DynamicBackground> getGradientBackground() {
        if (gradientBackground == null) {
            gradientBackground = new MutableLiveData<>();
        }
        return gradientBackground;
    }
}