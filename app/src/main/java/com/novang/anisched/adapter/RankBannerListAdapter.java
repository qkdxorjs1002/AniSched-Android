package com.novang.anisched.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.Rank;
import com.novang.anisched.model.tmdb.child.search.Result;
import com.novang.anisched.repository.tmdb.TMDBHelper;
import com.novang.anisched.repository.tmdb.TMDBRepository;
import com.novang.anisched.tool.GlideApp;

import java.util.List;

public class RankBannerListAdapter extends RecyclerView.Adapter<RankBannerListAdapter.ViewHolder> {

    private List<Rank> rankList;
    private OnItemClickListener onItemClickListener;
    private final Context context;

    public RankBannerListAdapter(Context context) {
        onItemClickListener = null;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rank_banner_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rank rank = rankList.get(position);

        ImageView rankBackdrop = holder.view.findViewById(R.id.rank_backdrop);
        TextView rankTitle = holder.view.findViewById(R.id.rank_title);
        TextView rankNum = holder.view.findViewById(R.id.rank_num);
        TextView rankDiff = holder.view.findViewById(R.id.rank_diff);

        TMDBHelper tmdbHelper = new TMDBHelper(new TMDBRepository(), new TMDBHelper.OnResultListener() {
            @Override
            public void onFind(String apiKey, Result result) {
                GlideApp.with(holder.view)
                        .asBitmap()
                        .load(result.getBackdropURL("original"))
                        .into(rankBackdrop);
            }

            @Override
            public void onFailed() {

            }
        });

        tmdbHelper.searchWithFilter(context.getString(R.string.tmdb_api_key), rank.getSubject());
        rankTitle.setText(rank.getSubject());
        rankNum.setText(String.valueOf(rank.getRank()).concat("위"));

        if (rank.getDiff() > 0) {
            rankDiff.setText("▲".concat(String.valueOf(rank.getDiff())));
            rankDiff.setTextColor(context.getColor(R.color.rankUp));
            rankDiff.setVisibility(View.VISIBLE);
        } else if (rank.getDiff() < 0) {
            rankDiff.setText("▼".concat(String.valueOf(rank.getDiff() * -1)));
            rankDiff.setTextColor(context.getColor(R.color.rankDown));
            rankDiff.setVisibility(View.VISIBLE);
        } else {
            rankDiff.setVisibility(View.INVISIBLE);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, rank);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (rankList == null) {

            return 0;
        }

        return rankList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<Rank> list) {
        rankList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Rank anime);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
