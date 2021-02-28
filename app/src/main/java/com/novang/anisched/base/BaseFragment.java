package com.novang.anisched.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    protected void init(@Nullable Bundle savedInstanceState) {
        initReferences();
        initViews();
        initObservers();
        initEvents();
    }

    protected abstract void initReferences();

    protected abstract void initViews();

    protected abstract void initObservers();

    protected abstract void initEvents();
}
