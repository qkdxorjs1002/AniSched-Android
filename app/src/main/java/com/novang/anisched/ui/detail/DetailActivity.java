package com.novang.anisched.ui.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.novang.anisched.R;
import com.novang.anisched.adapter.CaptionListAdapter;
import com.novang.anisched.adapter.GenreListAdapter;
import com.novang.anisched.adapter.SeasonListAdapter;
import com.novang.anisched.adapter.VideoListAdapter;
import com.novang.anisched.tool.GlideApp;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;

    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout appBar;
    private ImageView animeTmdbBackdrop;
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
    private TextView tmdbNetworks;
    private TextView tmdbProduction;
    private ImageView loadingIcon;

    private CoordinatorLayout container;
    private ConstraintLayout loadingContainer;
    private ConstraintLayout animeStatusOffNotice;
    private ConstraintLayout tmdbVideoListContainer;
    private ConstraintLayout tmdbDetailContainer;
    private ConstraintLayout tmdbNetworksContainer;
    private ConstraintLayout tmdbSeasonContainer;
    private ConstraintLayout websiteHeader;
    private ConstraintLayout apiLogoContainer;

    private RecyclerView genreListView;
    private GenreListAdapter genreListViewAdapter;
    private RecyclerView tmdbVideoListView;
    private VideoListAdapter tmdbVideoListAdapter;
    private SnapHelper tmdbVideoListSnapHelper;
    private RecyclerView tmdbSeasonListView;
    private SeasonListAdapter tmdbSeasonListAdapter;
    private SnapHelper tmdbSeasonListSnapHelper;
    private RecyclerView captionListView;
    private CaptionListAdapter captionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        initReferences();
        initViews();
        initObservers();
        initEvents();

        viewModel.callAnimeInfo(getIntent().getIntExtra("id", -1));
    }

    private void initReferences() {
        toolbarLayout = findViewById(R.id.toolbar_layout);
        loadingIcon = findViewById(R.id.loading_icon);
        appBar = findViewById(R.id.appBar);
        animeTmdbBackdrop = findViewById(R.id.anime_info_tmdb_backdrop);
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
        tmdbNetworks = findViewById(R.id.tmdb_networks);
        tmdbProduction = findViewById(R.id.tmdb_production);
        websiteHeader = findViewById(R.id.website_header);

        container = findViewById(R.id.container);
        loadingContainer = findViewById(R.id.loading_container);
        tmdbVideoListContainer = findViewById(R.id.tmdb_video_list_container);
        animeStatusOffNotice = findViewById(R.id.anime_status_off_notice);
        tmdbDetailContainer = findViewById(R.id.tmdb_detail_container);
        tmdbNetworksContainer = findViewById(R.id.tmdb_networks_container);
        tmdbSeasonContainer = findViewById(R.id.tmdb_season_container);
        apiLogoContainer = findViewById(R.id.api_logo_container);

        genreListView = findViewById(R.id.anime_info_genre_list_view);
        genreListViewAdapter = new GenreListAdapter();
        tmdbVideoListView = findViewById(R.id.tmdb_video_list);
        tmdbVideoListAdapter = new VideoListAdapter();
        tmdbVideoListSnapHelper = new PagerSnapHelper();
        tmdbSeasonListView = findViewById(R.id.tmdb_season_list);
        tmdbSeasonListAdapter = new SeasonListAdapter();
        tmdbSeasonListSnapHelper = new PagerSnapHelper();
        captionListView = findViewById(R.id.caption_list_View);
        captionListAdapter = new CaptionListAdapter();
    }

    private void initViews() {
        genreListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        genreListView.setAdapter(genreListViewAdapter);
        tmdbVideoListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        tmdbVideoListView.setAdapter(tmdbVideoListAdapter);
        tmdbVideoListSnapHelper.attachToRecyclerView(tmdbVideoListView);
        tmdbSeasonListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        tmdbSeasonListView.setAdapter(tmdbSeasonListAdapter);
        tmdbSeasonListSnapHelper.attachToRecyclerView(tmdbSeasonListView);
        captionListView.setLayoutManager(new LinearLayoutManager(this));
        captionListView.setAdapter(captionListAdapter);

        loadingContainer.setVisibility(View.VISIBLE);
        loadingIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));
        appBar.setExpanded(false, false);
        animeStatusOffNotice.setVisibility(View.GONE);
        animeStatusLive.setVisibility(View.GONE);
        animeStatusOff.setVisibility(View.GONE);
        tmdbVideoListContainer.setVisibility(View.GONE);
        tmdbDetailContainer.setVisibility(View.GONE);
        tmdbNetworksContainer.setVisibility(View.GONE);
        tmdbSeasonContainer.setVisibility(View.GONE);
        apiLogoContainer.setVisibility(View.GONE);
    }

    private void initObservers() {
        viewModel.anissiaAnime.observe(this, anime -> {
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
            websiteHeader.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getWebsite())));
            });
            genreListViewAdapter.updateList(anime.getGenreList());
            captionListAdapter.updateList(anime.getCaptionList());
        });

        viewModel.tmdbMovie.observe(this, movie -> {
            updateImages(movie.getBackdropURL("original"), movie.getPosterURL("w400"));
            animeTime.setText(movie.getRuntime().concat("분"));
            tmdbTitle.setText(movie.getTitle().concat("\n").concat(movie.getOriginalTitle()));
            tmdbOverview.setText(movie.getOverview());
            tmdbRating.setProgress(movie.getVoteDecimal());
            tmdbRatingCount.setText(String.valueOf(movie.getVoteCount()));
            tmdbRatingDecimal.setText(String.valueOf(movie.getVoteDecimal()));
            tmdbProduction.setText(movie.getStringProductionList());
            viewModel.loadingStatus.postValue(false);
        });

        viewModel.tmdbTV.observe(this, tv -> {
            updateImages(tv.getBackdropURL("original"), tv.getPosterURL("w400"));
            animeTime.setText(animeTime.getText().toString().concat(" - ").concat(String.valueOf(tv.getRuntime()).concat("분")));
            tmdbTitle.setText(tv.getName().concat("\n").concat(tv.getOriginalName()));
            tmdbOverview.setText(tv.getOverview());
            tmdbRating.setProgress(tv.getVoteDecimal());
            tmdbRatingCount.setText(String.valueOf(tv.getVoteCount()));
            tmdbRatingDecimal.setText(String.valueOf(tv.getVoteDecimal()));
            tmdbNetworks.setText(tv.getStringNetworkList());
            tmdbProduction.setText(tv.getStringProductionList());
            tmdbSeasonListAdapter.updateList(tv.getSeasonList());
            viewModel.loadingStatus.postValue(false);
        });

        viewModel.tmdbVideos.observe(this, videos -> {
            tmdbVideoListAdapter.updateList(videos);
            tmdbVideoListContainer.setVisibility(View.VISIBLE);
        });

        viewModel.mediaType.observe(this, s -> {
            tmdbDetailContainer.setVisibility(View.VISIBLE);
            apiLogoContainer.setVisibility(View.VISIBLE);

            if (s.equals("tv")) {
                tmdbNetworksContainer.setVisibility(View.VISIBLE);
                tmdbSeasonContainer.setVisibility(View.VISIBLE);
            }
        });

        viewModel.loadingStatus.observe(this, loading -> {
            if (!loading) {
                loadingContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                loadingContainer.setVisibility(View.GONE);
            } else {
                loadingContainer.setVisibility(View.VISIBLE);
            }
        });

        viewModel.gradientBackground.observe(this, dynamicBackground -> {
            container.setBackground(dynamicBackground.getGradient());
            toolbarLayout.setContentScrimColor(dynamicBackground.getTopColor());
            animeSubject.setBackgroundColor(dynamicBackground.getTopColor());
            if (!dynamicBackground.isDark()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    getWindow().getInsetsController().setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        });
    }

    private void initEvents() {
        captionListAdapter.setOnItemClickListener((v, caption) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(caption.getWebsite())));
        });
    }

    private void updateImages(String backdropURL, String posterURL) {
        RequestListener<Bitmap> backdropListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                appBar.setExpanded(true, true);
                viewModel.dynamicBackground(resource);
                return false;
            }
        };

        GlideApp.with(this)
                .asBitmap()
                .load(backdropURL)
                .listener(backdropListener)
                .into(animeTmdbBackdrop);

        GlideApp.with(this)
                .asBitmap()
                .load(posterURL)
                .override(Target.SIZE_ORIGINAL)
                .into(animeTmdbPoster);
    }
}