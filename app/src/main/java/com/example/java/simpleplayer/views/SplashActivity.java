package com.example.java.simpleplayer.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.java.simpleplayer.R;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MainActivity.newIntent(this));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
