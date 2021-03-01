package com.novang.anisched.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    public static void start(Activity activity, Class<?> tClass) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, Class<?> tClass, String extraName, int extra) {
        Intent intent = new Intent(activity, tClass);
        intent.putExtra(extraName, extra);
        activity.startActivity(intent);
    }

    protected void init(@Nullable Bundle savedInstanceState) {
        initReferences();
        initViews();
        initObservers();
        initEvents();
    }

    protected abstract void initReferences();

    protected abstract void initViews();

    protected abstract void initObservers();

    protected abstract void initEvents();
}
