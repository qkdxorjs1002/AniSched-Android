package com.novang.anisched.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.novang.anisched.R;
import com.novang.anisched.adapter.CaptionListAdapter;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel viewModel;
    private int id;

    private RecyclerView captionListView;
    private CaptionListAdapter captionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        id = getIntent().getIntExtra("id", -1);

        initReferences();
        initObservers();
        initEvents();

        viewModel.callAnimeInfo(id);
    }

    public void initReferences() {
        captionListView = findViewById(R.id.caption_list_View);
        captionListAdapter = new CaptionListAdapter();
        captionListView.setLayoutManager(new LinearLayoutManager(this));
        captionListView.setAdapter(captionListAdapter);
    }

    public void initObservers() {
        viewModel.animeInfo.observe(this, anime -> {
            captionListAdapter.updateList(anime.getCaptionList());
        });
    }

    public void initEvents() {

    }
}