package com.novang.anisched.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.novang.anisched.R;

public class LoadingView extends ConstraintLayout {

    private ImageView loadingIcon;

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.loadingIcon = new ImageView(context);
        initViews(context);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            loadingIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotation));
        }
    }

    private void initViews(Context context) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.topToTop = getTop();
        layoutParams.leftToLeft = getLeft();
        layoutParams.rightToRight = getRight();
        layoutParams.bottomToBottom = getBottom();

        loadingIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground));
        addView(loadingIcon, layoutParams);
    }
}
