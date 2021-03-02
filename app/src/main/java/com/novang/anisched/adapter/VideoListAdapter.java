package com.novang.anisched.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.tmdb.child.common.Video;
import com.novang.anisched.view.YoutubePlayer;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private List<Video> videoList;
    private OnItemClickListener onItemClickListener;

    public VideoListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_video_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videoList.get(position);

        YoutubePlayer youtubePlayer = holder.view.findViewById(R.id.youtube_player);

        youtubePlayer.load(video.getKey());

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, video);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList == null) {

            return 0;
        }

        return videoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<Video> list) {
        videoList = list;

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Video video);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
