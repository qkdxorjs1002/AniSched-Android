package com.novang.anisched.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.start(this, MainActivity.class);
        finish();
    }
}