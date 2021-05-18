package com.novang.anisched.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.novang.anisched.BuildConfig;
import com.novang.anisched.R;
import com.novang.anisched.adapter.RankBannerListAdapter;
import com.novang.anisched.base.BaseActivity;
import com.novang.anisched.ui.detail.DetailActivity;
import com.novang.anisched.ui.recent.caption.RecentCaptionActivity;
import com.novang.anisched.ui.recent.caption.fragment.RecentCaptionFragment;
import com.novang.anisched.ui.schedule.ScheduleActivity;
import com.novang.anisched.ui.schedule.fragment.ScheduleFragment;
import com.novang.anisched.ui.search.SearchActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;

    private Toolbar toolbar;
    private ImageButton menuSun, menuMon, menuTue, menuWed, menuThu, menuFri, menuSat, menuOva;

    private ConstraintLayout recentCaptionHeader, menuNew;

    private RecyclerView rankBannerListView;
    private RankBannerListAdapter rankBannerListAdapter;
    private SnapHelper rankBannerSnapHelper;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        super.init(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recent_caption_container, RecentCaptionFragment.newInstance(3))
                    .commitNow();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.today_schedule_container, ScheduleFragment.newInstance(
                            Calendar.getInstance(Locale.KOREA).get(Calendar.DAY_OF_WEEK) - 1))
                    .commitNow();
        }
        viewModel.requestRelease(BuildConfig.VERSION_NAME);
        viewModel.requestRanking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rankBannerListAdapter.getItemCount() != 0) {
            viewModel.startTimer(10000,10000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.stopTimer();
    }

    @Override
    protected void initReferences() {
        toolbar = findViewById(R.id.toolbar);
        recentCaptionHeader = findViewById(R.id.recent_caption_header);
        menuNew = findViewById(R.id.menu_new);
        menuSun = findViewById(R.id.menu_sunday);
        menuMon = findViewById(R.id.menu_monday);
        menuTue = findViewById(R.id.menu_tuesday);
        menuWed = findViewById(R.id.menu_wednesday);
        menuThu = findViewById(R.id.menu_thursday);
        menuFri = findViewById(R.id.menu_friday);
        menuSat = findViewById(R.id.menu_saturday);
        menuOva = findViewById(R.id.menu_ova);

        menuNew = findViewById(R.id.menu_new);

        rankBannerListView = findViewById(R.id.rank_list_view);
        rankBannerListAdapter = new RankBannerListAdapter(this);
        rankBannerSnapHelper = new PagerSnapHelper();
    }

    @Override
    protected void initViews() {
        rankBannerListView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rankBannerListView.setHasFixedSize(true);
        rankBannerListView.setAdapter(rankBannerListAdapter);
        rankBannerSnapHelper.attachToRecyclerView(rankBannerListView);
    }

    @Override
    protected void initObservers() {
        viewModel.getRelease().observe(this, release -> {
            new AlertDialog.Builder(this)
                    .setTitle("새 버전이 있습니다.")
                    .setMessage(release.getTagName().concat("\n").concat(release.getBody()))
                    .setPositiveButton("다운로드", (dialog, which) -> {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(release.getAssetList().get(0).getDownloadUrl())));
                    })
                    .setNegativeButton("취소", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
        });

        viewModel.getRankList().observe(this, ranks -> {
            if (ranks == null) {
                Toast.makeText(this, "애니시아 서버에서 정보를 가져오는데 실패했습니다.", Toast.LENGTH_LONG).show();
                finish();

                return;
            }
            rankBannerListAdapter.updateList(ranks);
            viewModel.startTimer(10000,10000);
        });

        viewModel.getRankPage().observe(this, integer -> {
            rankBannerListView.smoothScrollToPosition(integer);
        });
    }

    @Override
    protected void initEvents() {
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_search) {
                start(this, SearchActivity.class);
            }
            return false;
        });

        recentCaptionHeader.setOnClickListener(v -> {
            start(this, RecentCaptionActivity.class);
        });

        View.OnClickListener menuClickListener = v -> {
            int week = 8;

            switch(getResources().getResourceEntryName(v.getId())) {
                case "menu_sunday":
                    week = 0;
                    break;
                case "menu_monday":
                    week = 1;
                    break;
                case "menu_tuesday":
                    week = 2;
                    break;
                case "menu_wednesday":
                    week = 3;
                    break;
                case "menu_thursday":
                    week = 4;
                    break;
                case "menu_friday":
                    week = 5;
                    break;
                case "menu_saturday":
                    week = 6;
                    break;
                case "menu_ova":
                    week = 7;
                    break;
            }

            start(this, ScheduleActivity.class, "week", week);
        };

        menuNew.setOnClickListener(menuClickListener);
        menuSun.setOnClickListener(menuClickListener);
        menuMon.setOnClickListener(menuClickListener);
        menuTue.setOnClickListener(menuClickListener);
        menuWed.setOnClickListener(menuClickListener);
        menuThu.setOnClickListener(menuClickListener);
        menuFri.setOnClickListener(menuClickListener);
        menuSat.setOnClickListener(menuClickListener);
        menuOva.setOnClickListener(menuClickListener);

        rankBannerListView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rankBannerSnapHelper.onFling(velocityX, velocityY);
                viewModel.restartTimer(10000,10000);
                return false;
            }
        });

        rankBannerListAdapter.setOnItemClickListener((v, anime) -> {
            start(this, DetailActivity.class, "id", anime.getId());
        });
    }
}