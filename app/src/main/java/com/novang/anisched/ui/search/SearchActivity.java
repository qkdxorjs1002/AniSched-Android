package com.novang.anisched.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.adapter.AutoCorrectListAdapter;
import com.novang.anisched.base.BaseActivity;
import com.novang.anisched.ui.detail.DetailActivity;

public class SearchActivity extends BaseActivity {

    private SearchViewModel viewModel;

    private SearchView searchView;

    private RecyclerView autoCorrectListView;
    private AutoCorrectListAdapter autoCorrectListAdapter;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        super.init(savedInstanceState);
    }

    @Override
    protected void initReferences() {
        searchView = findViewById(R.id.search_view);
        autoCorrectListView = findViewById(R.id.list_View);
        autoCorrectListAdapter = new AutoCorrectListAdapter();
    }

    @Override
    protected void initViews() {
        autoCorrectListView.setLayoutManager(new LinearLayoutManager(this));
        autoCorrectListView.setAdapter(autoCorrectListAdapter);
    }

    @Override
    protected void initObservers() {
        viewModel.getAutoCorrectList().observe(this, autoCorrects -> {
            autoCorrectListAdapter.updateList(autoCorrects);
        });
    }

    @Override
    protected void initEvents() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    viewModel.requestAutoCorrect(newText);
                }
                return false;
            }
        });

        autoCorrectListAdapter.setOnItemClickListener((v, autoCorrect) -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", autoCorrect.getId());
            startActivity(intent);
        });
    }

}