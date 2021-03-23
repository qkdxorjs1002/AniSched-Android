package com.novang.anisched.ui.schedule.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novang.anisched.R;
import com.novang.anisched.adapter.AnimeListAdapter;
import com.novang.anisched.base.BaseFragment;
import com.novang.anisched.ui.detail.DetailActivity;

public class ListFragment extends BaseFragment {

    private ListViewModel viewModel;

    private RecyclerView animeListView;
    private AnimeListAdapter animeListAdapter;

    public static ListFragment newInstance(int week) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("week", week);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        super.init(savedInstanceState);
        viewModel.callSchedule(getArguments().getInt("week"));
    }

    @Override
    protected void initReferences() {
        animeListView = getView().findViewById(R.id.anime_list_View);
        animeListAdapter = new AnimeListAdapter();
    }

    @Override
    protected void initViews() {
        animeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        animeListView.setAdapter(animeListAdapter);
    }

    @Override
    protected void initObservers() {
        viewModel.getAnimeList().observe(this, animes -> {
            animeListAdapter.updateList(animes);
        });
    }

    @Override
    protected void initEvents() {
        animeListAdapter.setOnItemClickListener((v, anime) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("id", anime.getId());
            startActivity(intent);
        });
    }
}