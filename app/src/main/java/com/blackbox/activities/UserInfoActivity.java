package com.blackbox.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.blackbox.R;

import org.json.JSONObject;

public class UserInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

    }


    public static void getChangePasswordTaskResult(JSONObject foldersJson) {

    }

    public static void getChangeUserInfoTaskResult(JSONObject foldersJson) {

    }

    public static void getDeletedAccountTaskResult(JSONObject foldersJson) {

    }

}
