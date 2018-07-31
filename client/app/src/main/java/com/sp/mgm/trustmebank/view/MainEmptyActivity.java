package com.sp.mgm.trustmebank.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class MainEmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // go straight to main if a token is stored
//        if (Util.getToken() != null) {
//            activityIntent = new Intent(this, MainActivity.class);
//        } else {
//            activityIntent = new Intent(this, LoginActivity.class);
//        }
        activityIntent = new Intent(this, LoginActivity.class);
        startActivity(activityIntent);
        finish();
    }
}