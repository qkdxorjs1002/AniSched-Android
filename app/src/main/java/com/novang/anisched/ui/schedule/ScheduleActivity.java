package com.novang.anisched.ui.schedule;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.novang.anisched.R;
import com.novang.anisched.base.BaseActivity;
import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.ui.schedule.fragment.ScheduleFragment;

public class ScheduleActivity extends BaseActivity {

    private ScheduleViewModel viewModel;
    private int week;

    private TextView scheduleHeaderTitle;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_schedule);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        week = getIntent().getIntExtra("week", 0);

        super.init(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, ScheduleFragment.newInstance(week))
                    .commitNow();
        }
    }

    @Override
    protected void initReferences() {
        scheduleHeaderTitle = findViewById(R.id.schedule_header_title);
    }

    @Override
    protected void initViews() {
        scheduleHeaderTitle.setText(AnissiaRepository.DAY_OF_WEEK.get(week).concat(" 편성표"));
    }

    @Override
    protected void initObservers() {

    }

    @Override
    protected void initEvents() {

    }

}