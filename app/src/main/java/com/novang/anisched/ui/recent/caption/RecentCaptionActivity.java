package com.novang.anisched.ui.recent.caption;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.novang.anisched.R;
import com.novang.anisched.base.BaseActivity;
import com.novang.anisched.ui.recent.caption.fragment.RecentCaptionFragment;

public class RecentCaptionActivity extends BaseActivity {

    private RecentCaptionViewModel viewModel;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recent_caption);
        viewModel = new ViewModelProvider(this).get(RecentCaptionViewModel.class);

        super.init(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, RecentCaptionFragment.newInstance(0))
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