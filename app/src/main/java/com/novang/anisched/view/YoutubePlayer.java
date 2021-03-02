package com.novang.anisched.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.novang.anisched.R;
import com.novang.anisched.tool.GlideApp;

public class YoutubePlayer extends FrameLayout {

    private final WebView webView;
    private final ImageView thumbnail;

    private MutableLiveData<String> key;

    public YoutubePlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.webView = new WebView(context);
        this.thumbnail = new ImageView(context);
        key = new MutableLiveData<>();

        initView(context);
        initObservers();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();

        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(
                    (View.MeasureSpec.getSize(widthMeasureSpec) / 16) * 9, View.MeasureSpec.EXACTLY));
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(Context context) {
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setForeground(context.getDrawable(R.drawable.player_foreground));
        addView(thumbnail, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initObservers() {
        key.observeForever(s -> {
            GlideApp.with(this)
                    .load("https://i.ytimg.com/vi/".concat(s).concat("/original.jpg"))
                    .into(thumbnail);

            thumbnail.setOnClickListener(v -> {
                loadPlayer(s);
                v.setVisibility(GONE);
            });
        });
    }

    public void load(String key) {
        this.key.postValue(key);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadPlayer(String key) {
        addView(webView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("https://www.youtube.com/embed/".concat(key).concat("?controls=0&autoplay=1"));
    }
}
