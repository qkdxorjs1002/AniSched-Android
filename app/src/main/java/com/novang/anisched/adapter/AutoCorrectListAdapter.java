package com.novang.anisched.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.novang.anisched.R;
import com.novang.anisched.model.anissia.AutoCorrect;

import java.util.List;

public class AutoCorrectListAdapter extends RecyclerView.Adapter<AutoCorrectListAdapter.ViewHolder> {

    private List<AutoCorrect> autoCorrectList;
    private OnItemClickListener onItemClickListener;

    public AutoCorrectListAdapter() {
        onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_autocorrect_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AutoCorrect autoCorrect = autoCorrectList.get(position);

        TextView subject = holder.view.findViewById(R.id.subject);
        View stroke = holder.view.findViewById(R.id.stroke);

        subject.setText(autoCorrect.getSubject());

        if (position >= getItemCount() - 1) {
            stroke.setVisibility(View.GONE);
        } else {
            stroke.setVisibility(View.VISIBLE);
        }

        holder.view.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, autoCorrect);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (autoCorrectList == null) {

            return 0;
        }

        return autoCorrectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public void updateList(List<AutoCorrect> list) {
        autoCorrectList = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, AutoCorrect autoCorrect);
    }

    public void setOnItemClickListener(OnItemClickListener i) {
        this.onItemClickListener = i;
    }

}
