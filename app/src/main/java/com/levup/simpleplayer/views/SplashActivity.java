package com.levup.simpleplayer.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.levup.simpleplayer.R;

public class SplashActivity extends Activity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MenuActivity.newIntent(SplashActivity.this));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 1000);
    }
}