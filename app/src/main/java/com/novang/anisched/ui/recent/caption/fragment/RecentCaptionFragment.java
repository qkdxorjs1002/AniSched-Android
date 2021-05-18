package com.novang.anisched.ui.recent.caption.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.adapter.RecentCaptionListAdapter;
import com.novang.anisched.base.BaseFragment;
import com.novang.anisched.ui.detail.DetailActivity;

public class RecentCaptionFragment extends BaseFragment {

    private RecentCaptionViewModel viewModel;

    private RecyclerView captionListView;
    private RecentCaptionListAdapter captionListAdapter;

    private int count;

    public static RecentCaptionFragment newInstance(int count) {
        RecentCaptionFragment fragment = new RecentCaptionFragment();

        Bundle bundle = new Bundle();

        bundle.putInt("count", count);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(RecentCaptionViewModel.class);

        count = getArguments().getInt("count");
        super.init(savedInstanceState);

        viewModel.callRecentCaption();
    }

    @Override
    protected void initReferences() {
        captionListView = getView().findViewById(R.id.fragment_list_View);
        captionListAdapter = new RecentCaptionListAdapter();
    }

    @Override
    protected void initViews() {
        captionListView.setLayoutManager(new LinearLayoutManager(getContext()));
        captionListView.setAdapter(captionListAdapter);
    }

    @Override
    protected void initObservers() {
        viewModel.getCaptionList().observe(this, recentCaptions -> {
            if (recentCaptions == null) {
                Toast.makeText(getActivity(), "애니시아 서버에서 정보를 가져오는데 실패했습니다.", Toast.LENGTH_LONG).show();

                return;
            }

            if(count > 0) {
                captionListAdapter.updateList(recentCaptions.subList(0, getArguments().getInt("count")));
            } else {
                captionListAdapter.updateList(recentCaptions);
            }
        });
    }

    @Override
    protected void initEvents() {
        captionListAdapter.setOnItemClickListener((v, caption) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(caption.getWebsite())));
        });

        captionListAdapter.setOnItemLongClickListener((v, caption) -> {
            DetailActivity.start(getActivity(), DetailActivity.class, "id", caption.getId());

            return true;
        });
    }
}