package com.novang.anisched.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.novang.anisched.R;
import com.novang.anisched.tool.GlideApp;

public class YoutubePlayer extends FrameLayout implements LifecycleObserver {

    private final String THUMBNAIL_BASE_URL = "https://i.ytimg.com/vi/";
    private final String THUMBNAIL_ORIGINAL = "/original.jpg";
    private final String THUMBNAIL_MAXRES = "/maxresdefault.jpg";
    private final String THUMBNAIL_HQ = "/hqdefault.jpg";

    private final ImageView thumbnail;
    private final WebView webView;

    private MutableLiveData<String> key;

    public YoutubePlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.thumbnail = new ImageView(context);
        this.webView = new WebView(this.getContext()) {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        event.setAction(MotionEvent.ACTION_DOWN);
                        super.onTouchEvent(event);
                        event.setAction(MotionEvent.ACTION_UP);
                        return super.onTouchEvent(event);
                    case MotionEvent.ACTION_DOWN:
                        return true;
                }
                return super.onTouchEvent(event);
            }
        };

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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resume() {
        webView.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stop() {
        webView.onPause();
    }

    private void initView(Context context) {
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setForeground(ContextCompat.getDrawable(context, R.drawable.player_foreground));
        addView(thumbnail, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initObservers() {
        key.observeForever(s -> {

            GlideApp.with(this)
                    .load(THUMBNAIL_BASE_URL.concat(s).concat(THUMBNAIL_ORIGINAL))
                    .error(
                            GlideApp.with(this)
                                    .load(THUMBNAIL_BASE_URL.concat(s).concat(THUMBNAIL_MAXRES))
                                    .error(GlideApp.with(this)
                                            .load(THUMBNAIL_BASE_URL.concat(s).concat(THUMBNAIL_HQ))
                                    )
                    )
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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadUrl("https://www.youtube.com/embed/".concat(key).concat("?controls=0"));
        addView(webView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
