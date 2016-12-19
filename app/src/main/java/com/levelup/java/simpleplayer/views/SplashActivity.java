package com.levelup.java.simpleplayer.views;

import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(MenuActivity.newIntent(this));
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
