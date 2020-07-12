package com.example.postapp.ui.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.postapp.R;
import com.example.postapp.ui.main.view.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, new DetailFragment()).commitNow();
    }
}