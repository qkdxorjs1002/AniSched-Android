package com.novang.anisched.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.widget.Toast;

import com.novang.anisched.repository.anissia.AnissiaRepository;
import com.novang.anisched.repository.tmdb.TMDBRepository;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AnissiaRepository anissiaRepository = new AnissiaRepository();

        anissiaRepository.ping().observe(this, aBoolean -> {
            if (aBoolean) {
                MainActivity.start(this, MainActivity.class);
            } else {
                Toast.makeText(this, "애니시아 서버가 점검 중입니다.\n나중에 다시 시도해주세요.", Toast.LENGTH_LONG).show();
            }

            finish();
        });
    }
}