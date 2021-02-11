package com.novang.anisched.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.novang.anisched.R;
import com.novang.anisched.adapter.AnimeListAdapter;
import com.novang.anisched.ui.detail.DetailActivity;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private RecyclerView animeListView;
    private AnimeListAdapter animeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callSchedule(0);
    }

    public void initReferences() {
        animeListView = findViewById(R.id.anime_list_View);
        animeListAdapter = new AnimeListAdapter();
        animeListView.setLayoutManager(new LinearLayoutManager(this));
        animeListView.setAdapter(animeListAdapter);
    }

    public void initObservers() {
        viewModel.animeList.observe(this, animes -> {
            animeListAdapter.updateList(animes);
        });
    }

    public void initEvents() {
        animeListAdapter.setOnItemClickListener((v, anime) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", anime.getId());
            startActivity(intent);
        });
    }
}