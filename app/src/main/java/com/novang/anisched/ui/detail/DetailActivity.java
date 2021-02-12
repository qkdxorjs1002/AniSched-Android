package com.novang.anisched.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.novang.anisched.R;
import com.novang.anisched.adapter.CaptionListAdapter;
import com.novang.anisched.adapter.GenreListAdapter;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;
    private int id;

    private ImageView animeThumbnail;
    private TextView animeSubject;
    private TextView animeStartDate;
    private TextView animeEndDate;
    private Button animeWebsite;

    private RecyclerView genreListView;
    private GenreListAdapter genreListViewAdapter;
    private RecyclerView captionListView;
    private CaptionListAdapter captionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        id = getIntent().getIntExtra("id", -1);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callAnimeInfo(id);
    }

    public void initReferences() {
        animeThumbnail = findViewById(R.id.anime_info_thumbnail);
        animeSubject = findViewById(R.id.anime_info_subject);
        animeStartDate = findViewById(R.id.anime_info_startdate);
        animeEndDate = findViewById(R.id.anime_info_enddate);
        animeWebsite = findViewById(R.id.anime_info_website);
        genreListView = findViewById(R.id.anime_info_genre_list_view);
        genreListViewAdapter = new GenreListAdapter();
        captionListView = findViewById(R.id.caption_list_View);
        captionListAdapter = new CaptionListAdapter();

        genreListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genreListView.setAdapter(genreListViewAdapter);
        captionListView.setLayoutManager(new LinearLayoutManager(this));
        captionListView.setAdapter(captionListAdapter);
    }

    public void initObservers() {
        viewModel.animeInfo.observe(this, anime -> {
            animeSubject.setText(anime.getSubject());
            animeStartDate.setText(anime.getStartDate());
            animeEndDate.setText(anime.getEndDate());
            animeWebsite.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getWebsite())));
            });
            genreListViewAdapter.updateList(anime.getGenres());
            captionListAdapter.updateList(anime.getCaptionList());
        });
    }

    public void initEvents() {
        captionListAdapter.setOnItemClickListener((v, caption) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(caption.getWebsite())));
        });
    }
}