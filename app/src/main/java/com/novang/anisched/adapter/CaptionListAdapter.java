package com.novang.anisched.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.Caption;

import java.util.List;

public class CaptionListAdapter extends RecyclerView.Adapter<CaptionListAdapter.ViewHolder> {

    private List<Caption> captionList;
    private OnItemClickListener onItemClickListener;

    public CaptionListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_caption_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caption caption = captionList.get(position);

        TextView captionEpisode = holder.view.findViewById(R.id.episode);
        TextView captionAuthor = holder.view.findViewById(R.id.author);
        TextView captionUpdate = holder.view.findViewById(R.id.update);

        captionEpisode.setText(caption.getEpisode());
        captionAuthor.setText(caption.getAuthor());
        captionUpdate.setText(caption.getTimeElapsed());
        if(caption.getWebsite().equals("")) {
            holder.view.setEnabled(false);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, caption);
            }
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

    public void updateList(List<Caption> list) {
        captionList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Caption caption);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
