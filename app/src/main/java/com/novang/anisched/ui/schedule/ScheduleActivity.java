package com.novang.anisched.ui.schedule;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.novang.anisched.R;
import com.novang.anisched.base.BaseActivity;
import com.novang.anisched.ui.schedule.fragment.ListFragment;

public class ScheduleActivity extends BaseActivity {

    private ScheduleViewModel viewModel;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        super.init(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, ListFragment.newInstance(
                            getIntent().getIntExtra("week", 0)))
                    .commitNow();
        }
    }

    @Override
    protected void initReferences() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initObservers() {

    }

    @Override
    protected void initEvents() {

    }

}