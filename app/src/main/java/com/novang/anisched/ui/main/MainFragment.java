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

public class MainFragment extends Fragment {

    private MainViewModel viewModel;

    private RecyclerView animeListView;
    private AnimeListAdapter animeListAdapter;

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

        animeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        animeListView.setAdapter(animeListAdapter);
    }

    public void initObservers() {
        viewModel.animeList.observe(getViewLifecycleOwner(), animes -> {
            animeListAdapter.updateList(animes);
        });
    }

    public void initEvents() {

    }

}