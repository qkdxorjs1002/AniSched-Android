package com.novang.anisched.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.Rank;

import java.util.List;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ViewHolder> {

    private List<Rank> rankList;
    private OnItemClickListener onItemClickListener;

    public RankListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rank_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rank rank = rankList.get(position);

        TextView ranking = holder.view.findViewById(R.id.ranking);
        TextView subject = holder.view.findViewById(R.id.subject);
        TextView diff = holder.view.findViewById(R.id.diff);

        if (rank.getDiff() > 0) {
            diff.setTextColor(Color.RED);
        } else if (rank.getDiff() < 0) {
            diff.setTextColor(Color.BLUE);
        } else {
            diff.setVisibility(View.INVISIBLE);
        }

        ranking.setText(String.valueOf(rank.getRank()));
        subject.setText(rank.getSubject());
        diff.setText(String.valueOf(rank.getDiff()));

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
