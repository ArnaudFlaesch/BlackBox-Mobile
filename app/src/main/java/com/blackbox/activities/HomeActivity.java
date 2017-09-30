package com.blackbox.activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.blackbox.R;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Log.d("Test", getIntent().getStringExtra("userId"));
    }

}
