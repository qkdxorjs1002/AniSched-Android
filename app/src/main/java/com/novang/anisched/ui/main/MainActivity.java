package com.novang.anisched.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.novang.anisched.R;
import com.novang.anisched.ui.list.ListActivity;
import com.novang.anisched.ui.list.fragment.ListFragment;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private ConstraintLayout menuAll;
    private RadioGroup menuGroup1;
    private RadioGroup menuGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.today_schedule_container, ListFragment.newInstance(
                            Calendar.getInstance(Locale.KOREA).get(Calendar.DAY_OF_WEEK) - 1))
                    .commitNow();
        }
    }

    public void initReferences() {
        menuAll = findViewById(R.id.menu_all);
        menuGroup1 = findViewById(R.id.menu_group1);
        menuGroup2 = findViewById(R.id.menu_group2);
    }

    public void initObservers() {

    }

    public void initEvents() {
        menuAll.setOnClickListener(v -> {

        });

        menuGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("week", group.indexOfChild(radioButton));
            startActivity(intent);
            radioButton.setChecked(false);
        });

        menuGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("week", group.indexOfChild(radioButton) + 4);
            startActivity(intent);
            radioButton.setChecked(false);
        });
    }

}