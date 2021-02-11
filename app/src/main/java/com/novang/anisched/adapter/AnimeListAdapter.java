package com.novang.anisched.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.Anime;

import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.ViewHolder> {

    private List<Anime> animeList;
    private OnItemClickListener onItemClickListener;

    public AnimeListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_anime_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = animeList.get(position);

        TextView subject = holder.view.findViewById(R.id.subject);
        TextView time = holder.view.findViewById(R.id.time);
        TextView genres = holder.view.findViewById(R.id.genres);

        subject.setText(anime.getSubject());
        time.setText(anime.getTime());
        genres.setText(anime.getGenres());

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, anime);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (animeList == null) {

            return 0;
        }

        return animeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<Anime> list) {
        animeList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Anime anime);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
