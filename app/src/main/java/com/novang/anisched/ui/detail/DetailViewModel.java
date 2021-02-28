package com.novang.anisched.ui.detail;

import android.graphics.Bitmap;

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

    public MutableLiveData<Anime> anissiaAnime;
    public MutableLiveData<Movie> tmdbMovie;
    public MutableLiveData<TV> tmdbTV;
    public MutableLiveData<List<Video>> tmdbVideos;

    public MutableLiveData<String> mediaType;
    public MutableLiveData<Boolean> loadingStatus;
    public MutableLiveData<DynamicBackground> gradientBackground;

    public DetailViewModel() {
        anissiaRepository = new AnissiaRepository();
        tmdbRepository = new TMDBRepository();

        anissiaAnime = new MutableLiveData<>();
        tmdbMovie = new MutableLiveData<>();
        tmdbTV = new MutableLiveData<>();
        tmdbVideos = new MutableLiveData<>();

        mediaType = new MutableLiveData<>();
        loadingStatus = new MutableLiveData<>(true);
        gradientBackground = new MutableLiveData<>();
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
                mediaType.postValue("movie");
            });
        } else if (type.equals("tv")) {
            tmdbRepository.requestTv(apiKey, "ko-KR", id).observeForever(tv -> {
                tmdbTV.postValue(tv);
                mediaType.postValue("tv");
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
}