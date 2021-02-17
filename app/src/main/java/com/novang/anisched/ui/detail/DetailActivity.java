package com.novang.anisched.ui.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.novang.anisched.R;
import com.novang.anisched.adapter.CaptionListAdapter;
import com.novang.anisched.adapter.GenreListAdapter;
import com.novang.anisched.tool.GlideApp;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;

    private ConstraintLayout loadingContainer;
    private ImageView loadingIcon;
    private AppBarLayout appBar;
    private ConstraintLayout animeStatusOffNotice;
    private ImageView animeThumbnail;
    private TextView animeSubject;
    private ImageView animeTmdbPoster;
    private TextView animeTime;
    private TextView animeStartDate;
    private TextView animeEndDate;
    private ImageView animeStatusLive;
    private ImageView animeStatusOff;
    private TextView tmdbTitle;
    private TextView tmdbOverview;
    private ProgressBar tmdbRating;
    private TextView tmdbRatingCount;
    private TextView tmdbRatingDecimal;
    private ConstraintLayout websiteHeader;

    private RecyclerView genreListView;
    private GenreListAdapter genreListViewAdapter;
    private RecyclerView captionListView;
    private CaptionListAdapter captionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callAnimeInfo(getIntent().getIntExtra("id", -1));
    }

    public void initReferences() {
        loadingContainer = findViewById(R.id.loading_container);
        loadingIcon = findViewById(R.id.loading_icon);
        appBar = findViewById(R.id.appBar);
        animeStatusOffNotice = findViewById(R.id.anime_status_off_notice);
        animeThumbnail = findViewById(R.id.anime_info_thumbnail);
        animeSubject = findViewById(R.id.anime_info_subject);
        animeTmdbPoster = findViewById(R.id.anime_info_tmdb_poster);
        animeTime = findViewById(R.id.anime_info_time);
        animeStartDate = findViewById(R.id.anime_info_startdate);
        animeEndDate = findViewById(R.id.anime_info_enddate);
        animeStatusLive = findViewById(R.id.anime_info_status_live);
        animeStatusOff = findViewById(R.id.anime_info_status_off);
        tmdbTitle = findViewById(R.id.tmdb_title);
        tmdbOverview = findViewById(R.id.tmdb_overview);
        tmdbRating = findViewById(R.id.tmdb_rating);
        tmdbRatingCount = findViewById(R.id.tmdb_rating_count);
        tmdbRatingDecimal = findViewById(R.id.tmdb_rating_decimal);
        websiteHeader = findViewById(R.id.website_header);
        genreListView = findViewById(R.id.anime_info_genre_list_view);
        genreListViewAdapter = new GenreListAdapter();
        captionListView = findViewById(R.id.caption_list_View);
        captionListAdapter = new CaptionListAdapter();

        genreListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genreListView.setAdapter(genreListViewAdapter);
        captionListView.setLayoutManager(new LinearLayoutManager(this));
        captionListView.setAdapter(captionListAdapter);

        loadingContainer.setVisibility(View.VISIBLE);
        loadingIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));
        appBar.setExpanded(false, false);
        animeStatusOffNotice.setVisibility(View.GONE);
        animeStatusLive.setVisibility(View.GONE);
        animeStatusOff.setVisibility(View.GONE);
    }

    public void initObservers() {
        viewModel.animeInfo.observe(this, anime -> {
            viewModel.searchTMDB(getString(R.string.tmdb_api_key), anime.getSubject());
            animeSubject.setText(anime.getSubject());
            animeTime.setText(anime.getTime());
            animeStartDate.setText(anime.getStartDate());
            animeEndDate.setText(anime.getEndDate());
            if (anime.isStatus()) {
                animeStatusLive.setVisibility(View.VISIBLE);
            } else {
                animeStatusOffNotice.setVisibility(View.VISIBLE);
                animeStatusOff.setVisibility(View.VISIBLE);
            }
            tmdbTitle.setText(anime.getSubject());
            websiteHeader.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getWebsite())));
            });
            genreListViewAdapter.updateList(anime.getGenreList());
            captionListAdapter.updateList(anime.getCaptionList());
            loadingContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            loadingContainer.setVisibility(View.GONE);
        });

        viewModel.tmdbResult.observe(this, result -> {
            tmdbTitle.setText(tmdbTitle.getText().toString().concat("\n").concat(result.getFlexibleOriginalName()));
            tmdbOverview.setText(result.getOverview());
            tmdbRating.setProgress(result.getVoteDecimal());
            tmdbRatingCount.setText(String.valueOf(result.getVoteCount()));
            tmdbRatingDecimal.setText(String.valueOf(result.getVoteDecimal()));

            GlideApp.with(this)
                    .load(result.getBackdropURL("original"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            appBar.setExpanded(true, true);
                            return false;
                        }
                    })
                    .into(animeThumbnail);

            GlideApp.with(this)
                    .load(result.getPosterURL("w400"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(Target.SIZE_ORIGINAL)
                    .into(animeTmdbPoster);
        });
    }

    public void initEvents() {
        captionListAdapter.setOnItemClickListener((v, caption) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(caption.getWebsite())));
        });
    }
}