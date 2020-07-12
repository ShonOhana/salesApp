package com.example.postapp.ui.main.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.postapp.R;
import com.example.postapp.ui.main.view.MainFragment;
import com.example.postapp.ui.main.view.SliderFragment;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {


            //Put the explanation viewpager for the first time only.
            mPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

            boolean firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SliderFragment()).commitNow();
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commitNow();
            }


        }
    }



}