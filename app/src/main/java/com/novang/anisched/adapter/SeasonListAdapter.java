package com.novang.anisched.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.novang.anisched.R;
import com.novang.anisched.model.tmdb.tv.Season;
import com.novang.anisched.tool.GlideApp;

import java.util.Collections;
import java.util.List;

public class SeasonListAdapter extends RecyclerView.Adapter<SeasonListAdapter.ViewHolder> {

    private List<Season> seasonList;
    private OnItemClickListener onItemClickListener;

    public SeasonListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_season_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Season season = seasonList.get(position);

        ImageView seasonPoster = holder.view.findViewById(R.id.season_poster);
        TextView seasonName = holder.view.findViewById(R.id.season_name);
        TextView seasonAirDate = holder.view.findViewById(R.id.season_air_date);
        TextView seasonEpisodeCount = holder.view.findViewById(R.id.season_episode_count);
        TextView seasonOverview = holder.view.findViewById(R.id.season_overview);

        GlideApp.with(holder.view)
                .load(season.getPosterURL("w400"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(seasonPoster);

        seasonName.setText(season.getName());
        seasonAirDate.setText(season.getAirDate());
        seasonEpisodeCount.setText(String.valueOf(season.getEpisodeCount()));
        seasonOverview.setText(season.getOverview());

        if (position >= getItemCount() - 1) {
            holder.view.setPadding(0, 0, 0, 0);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, season);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (seasonList == null) {

            return 0;
        }

        return seasonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<Season> list) {
        seasonList = list;
        Collections.reverse(seasonList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Season season);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
