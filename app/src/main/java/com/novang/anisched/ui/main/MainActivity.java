package com.novang.anisched.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.novang.anisched.BuildConfig;
import com.novang.anisched.R;
import com.novang.anisched.ui.list.ListActivity;
import com.novang.anisched.ui.list.fragment.ListFragment;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private ConstraintLayout menuNew;
    private ImageButton menuSun, menuMon, menuTue, menuWed, menuThu, menuFri, menuSat, menuOva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initReferences();
        initViews();
        initObservers();
        initEvents();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.today_schedule_container, ListFragment.newInstance(
                            Calendar.getInstance(Locale.KOREA).get(Calendar.DAY_OF_WEEK) - 1))
                    .commitNow();
        }
    }

    private void initReferences() {
        menuNew = findViewById(R.id.menu_new);
        menuSun = findViewById(R.id.menu_sunday);
        menuMon = findViewById(R.id.menu_monday);
        menuTue = findViewById(R.id.menu_tuesday);
        menuWed = findViewById(R.id.menu_wednesday);
        menuThu = findViewById(R.id.menu_thursday);
        menuFri = findViewById(R.id.menu_friday);
        menuSat = findViewById(R.id.menu_saturday);
        menuOva = findViewById(R.id.menu_ova);
    }
    private void initViews() {
        viewModel.requestRelease(BuildConfig.VERSION_NAME);
    }

    private void initObservers() {
        viewModel.release.observe(this, release -> {
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
    }

    private void initEvents() {
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

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("week", week);
            startActivity(intent);
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
    }

}