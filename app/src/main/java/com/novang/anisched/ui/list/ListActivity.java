package com.novang.anisched.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.novang.anisched.R;
import com.novang.anisched.ui.list.fragment.ListFragment;

public class ListActivity extends AppCompatActivity {

    private ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        initReferences();
        initObservers();
        initEvents();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_container, ListFragment.newInstance(
                            getIntent().getIntExtra("week", 0)))
                    .commitNow();
        }
    }

    public void initReferences() {

    }

    public void initObservers() {

    }

    public void initEvents() {

    }
}