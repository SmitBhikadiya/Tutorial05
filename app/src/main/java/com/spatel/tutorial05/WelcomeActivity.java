package com.spatel.tutorial05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        preferences = getSharedPreferences("LogState",MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void LogoutMe(View view) {
        editor.remove("isLogin");
        editor.commit();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}