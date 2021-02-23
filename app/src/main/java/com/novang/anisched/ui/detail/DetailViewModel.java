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
import com.novang.anisched.repository.tmdb.TMDBRepository;
import com.novang.anisched.tool.DynamicBackground;
import com.novang.anisched.tool.Levenshtein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    public void callAnimeInfo(int id) {
        anissiaRepository.callAnimeInfo(id).observeForever(anime -> {
            anissiaAnime.postValue(anime);
        });
    }

    public void getDetail(String apiKey, String type, int id) {
        if (type.equals("movie")) {
            tmdbRepository.requestMovieDetail(apiKey, "ko-KR", id).observeForever(movie -> {
                tmdbMovie.postValue(movie);
                mediaType.postValue("movie");
            });
        } else if (type.equals("tv")) {
            tmdbRepository.requestTVDetail(apiKey, "ko-KR", id).observeForever(tv -> {
                tmdbTV.postValue(tv);
                mediaType.postValue("tv");
            });
        }

        if (type.equals("movie") || type.equals("tv")) {
            getVideos(apiKey, type, id);
        }
    }

    public void getVideos(String apiKey, String type, int id) {
        getVideos(apiKey, "ko-KR", type, id);
    }

    public void getVideos(String apiKey, String lang, String type, int id) {
        tmdbRepository.requestVideos(apiKey, lang, type, id).observeForever(videos -> {
            List<Video> videoList = videos.getVideoList();

            if (videoList.isEmpty()) {
                if (lang.equals("ko-KR")) {
                    getVideos(apiKey, "ja-JP", type, id);
                } else if (lang.equals("ja-JP")) {
                    getVideos(apiKey, "en-US", type, id);
                }
            } else {
                tmdbVideos.postValue(videos.getVideoList());
            }
        });
    }

    public void searchTMDB(String apiKey, Anime anime) {
        List<String> regexList = new ArrayList<>(Arrays.asList(
                "(\\s\\d기)|(\\s(OVA|OAD))", "(\\s(IX|IV|V?I{0,3})$)|(\\s\\d(?i)[snrt](?i)[tdh])|(((\\s(?i)the)(\\s\\w+|)|)\\s(시즌|(?i)season)(\\d|\\s\\d|))",
                "(((\\s(?i)the)(\\s\\w+|)|)\\s((?i)animation)(\\d|\\s\\d|))", "[-~].*[-~]", "[^\\uAC00-\\uD7A30-9A-z\\s]", "\\s"
        ));

        searchTMDB(apiKey, anime.getSubject(), anime, regexList.iterator());
    }

    private void searchTMDB(String apiKey, String keyword, Anime anime, Iterator<String> iterator) {
        tmdbRepository.search(apiKey, "ko-KR", keyword).observeForever(searches -> {
            List<Result> result = searches.getResultList();

            if (!result.isEmpty()) {
                int idx = selectBestResult(result, keyword, anime);
                if (idx != -1) {
                    getDetail(apiKey, result.get(idx).getMediaType(), result.get(idx).getId());
                    return;
                }
            }

            if (!iterator.hasNext()) {
                loadingStatus.postValue(false);
                return;
            }
            String filtered = keyword.replaceAll(iterator.next(), "");
            searchTMDB(apiKey, filtered, anime, iterator);
        });
    }

    private int selectBestResult(List<Result> result, String keyword, Anime anime) {
        int similar = -1;
        double last_diff = -1;

        for (int idx = 0; result.size() > idx; idx++) {
            Result target = result.get(idx);

            if (Objects.equals(target.getFirstAirDate(), anime.getStartDate())) {
                similar = idx;
                break;
            }

            if (target.getGenreIdList().contains(16) && ((target.getMediaType().equals("tv") || target.getMediaType().equals("movie")))) {
                String targetString = target.getFlexibleName().replace(" ", "");

                double diff = (Levenshtein.getDistance(targetString, anime.getSubject().replace(" ", ""))
                        + Levenshtein.getDistance(targetString, keyword.replace(" ", ""))) / 2;

                if (last_diff < diff) {
                    similar = idx;
                    last_diff = diff;
                }
            }
        }

        return similar;
    }
}