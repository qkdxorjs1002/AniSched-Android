package com.novang.anisched.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novang.anisched.R;
import com.novang.anisched.adapter.AnimeListAdapter;
import com.novang.anisched.adapter.CaptionListAdapter;

public class MainFragment extends Fragment {

    private MainViewModel viewModel;

    private RecyclerView animeListView;
    private AnimeListAdapter animeListAdapter;
    private RecyclerView captionListView;
    private CaptionListAdapter captionListAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callSchedule(0);
    }

    public void initReferences() {
        animeListView = getView().findViewById(R.id.anime_list_View);
        animeListAdapter = new AnimeListAdapter();
        captionListView = getView().findViewById(R.id.caption_list_View);
        captionListAdapter = new CaptionListAdapter();

        animeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        animeListView.setAdapter(animeListAdapter);
        captionListView.setLayoutManager(new LinearLayoutManager(getContext()));
        captionListView.setAdapter(captionListAdapter);
    }

    public void initObservers() {
        viewModel.animeList.observe(getViewLifecycleOwner(), animes -> {
            animeListAdapter.updateList(animes);
        });

        viewModel.captionList.observe(getViewLifecycleOwner(), captions -> {
            captionListAdapter.updateList(captions);
        });
    }

    public void initEvents() {
        animeListAdapter.setOnItemClickListener((v, anime) -> {
            viewModel.callCaption(anime.getId());
        });
    }

}