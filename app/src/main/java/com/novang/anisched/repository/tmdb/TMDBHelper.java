package com.novang.anisched.repository.tmdb;

import com.novang.anisched.model.anissia.Anime;
import com.novang.anisched.model.tmdb.child.search.Result;
import com.novang.anisched.tool.Levenshtein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TMDBHelper {

    private final TMDBRepository tmdbRepository;
    private final OnResultListener onResultListener;

    private final List<String> regexList = new ArrayList<>(Arrays.asList(
            "", "(\\s\\d기)|(\\s(OVA|OAD))|(\\s(IX|IV|V?I{0,3})$)|(\\s\\d(?i)[snrt](?i)[tdh])|(((\\s(?i)the)(\\s\\w+|)|)\\s(시즌|(?i)season)(\\d|\\s\\d|))"
    ));

    public TMDBHelper(TMDBRepository repository, OnResultListener listener) {
        this.tmdbRepository = repository;
        this.onResultListener = listener;
    }

    public interface OnResultListener {
        void onFind(String apiKey, Result result);
        void onFailed();
    }

    public void searchWithFilter(String apiKey, String keyword) {
        search(apiKey, keyword, keyword, regexList.iterator());
    }

    public void searchWithFilter(String apiKey, Anime anime) {
        search(apiKey, anime.getSubject(), anime, regexList.iterator());
    }

    private void search(String apiKey, String keyword, String originalKeyword, Iterator<String> iterator) {
        tmdbRepository.search(apiKey, "ko-KR", keyword).observeForever(searches -> {
            List<Result> result = searches.getResultList();

            if (!searches.isNullOrEmpty(result)) {
                int idx = selectBestResult(result, keyword, originalKeyword);
                if (idx != -1) {
                    onResultListener.onFind(apiKey, result.get(idx));
                    return;
                }
            }

            if (!iterator.hasNext()) {
                onResultListener.onFailed();
                return;
            }
            String filtered = keyword.replaceAll(iterator.next(), "");
            search(apiKey, filtered, originalKeyword, iterator);
        });
    }

    private void search(String apiKey, String keyword, Anime anime, Iterator<String> iterator) {
        tmdbRepository.search(apiKey, "ko-KR", keyword).observeForever(searches -> {
            List<Result> result = searches.getResultList();

            if (!searches.isNullOrEmpty(result)) {
                int idx = selectBestResult(result, keyword, anime);
                if (idx != -1) {
                    onResultListener.onFind(apiKey, result.get(idx));
                    return;
                }
            }

            if (!iterator.hasNext()) {
                onResultListener.onFailed();
                return;
            }
            String filtered = keyword.replaceAll(iterator.next(), "");
            search(apiKey, filtered, anime, iterator);
        });
    }

    private int selectBestResult(List<Result> result, String keyword, String originalKeyword) {
        int similar = -1;
        double last_diff = -1;

        for (int idx = 0; result.size() > idx; idx++) {
            Result target = result.get(idx);

            List<Integer> genreIdList = target.getGenreIdList();
            if (target.isNullOrEmpty(genreIdList)) {
                continue;
            }

            if (genreIdList.contains(16) && ((target.getMediaType().equals("tv") || target.getMediaType().equals("movie")))) {
                String targetString = target.getFlexibleName().replace(" ", "");

                double diff = (Levenshtein.getDistance(targetString, originalKeyword.replace(" ", ""))
                        + Levenshtein.getDistance(targetString, keyword.replace(" ", ""))) / 2;

                if (last_diff < diff) {
                    similar = idx;
                    last_diff = diff;
                }
            }
        }

        return similar;
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

            List<Integer> genreIdList = target.getGenreIdList();
            if (target.isNullOrEmpty(genreIdList)) {
                continue;
            }

            if (genreIdList.contains(16) && ((target.getMediaType().equals("tv") || target.getMediaType().equals("movie")))) {
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
