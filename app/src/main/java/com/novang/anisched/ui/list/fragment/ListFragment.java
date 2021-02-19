package com.novang.anisched.ui.list.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import com.novang.anisched.ui.detail.DetailActivity;

public class ListFragment extends Fragment {

    private int week;
    private ListViewModel viewModel;

    private RecyclerView animeListView;
    private AnimeListAdapter animeListAdapter;

    public static ListFragment newInstance(int week) {
        return new ListFragment(week);
    }

    public ListFragment(int week) {
        this.week = week;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callSchedule(week);
    }

    private void initReferences() {
        animeListView = getView().findViewById(R.id.anime_list_View);
        animeListAdapter = new AnimeListAdapter();
        animeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        animeListView.setAdapter(animeListAdapter);
    }

    private void initObservers() {
        viewModel.animeList.observe(this, animes -> {
            animeListAdapter.updateList(animes);
        });
    }

    private void initEvents() {
        animeListAdapter.setOnItemClickListener((v, anime) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("id", anime.getId());
            startActivity(intent);
        });
    }
}