package com.novang.anisched.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.RecentCaption;

import java.util.List;

public class RecentCaptionListAdapter extends RecyclerView.Adapter<RecentCaptionListAdapter.ViewHolder> {

    private List<RecentCaption> captionList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public RecentCaptionListAdapter() {
        onItemClickListener = null;
        onItemLongClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recent_caption_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentCaption caption = captionList.get(position);

        TextView captionAuthor = holder.view.findViewById(R.id.author);
        TextView captionEpisode = holder.view.findViewById(R.id.episode);
        TextView captionSubject = holder.view.findViewById(R.id.subject);
        TextView captionUpdate = holder.view.findViewById(R.id.update);
        View stroke = holder.view.findViewById(R.id.stroke);

        captionAuthor.setText(caption.getAuthor());
        captionEpisode.setText(caption.getEpisode());
        captionSubject.setText(caption.getSubject());
        captionUpdate.setText(caption.getTimeElapsed());

        if(caption.getWebsite().equals("")) {
            holder.view.setEnabled(false);
        }

        if (position >= getItemCount() - 1) {
            stroke.setVisibility(View.GONE);
        } else {
            stroke.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, caption);
            }
        });

        holder.view.setOnLongClickListener(v -> {
            if (onItemClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, caption);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if (captionList == null) {

            return 0;
        }

        return captionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<RecentCaption> list) {
        captionList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, RecentCaption caption);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, RecentCaption caption);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener i) {
        this.onItemLongClickListener = i;
    }

}
