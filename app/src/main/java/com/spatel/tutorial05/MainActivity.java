package com.spatel.tutorial05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button btn_login;
    private EditText et_email,et_password;
    private String key;
    private CheckBox remeberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("LogState",MODE_PRIVATE);
        editor = preferences.edit();

        key = "isLogin";
        Boolean state = checkKeyExists(preferences,key);
        Log.d("tag :", String.valueOf(state));

        if(!state){
            setContentView(R.layout.activity_main);
        }
        else{
            Intent intent =new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
            finish();
        }




        btn_login = findViewById(R.id.btnLogin);
        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        remeberMe = findViewById(R.id.rememberMe);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim().toLowerCase();
                String password = et_password.getText().toString().trim();
                if(isEmailValid(email) && isPasswordValid(password)) {
                    String x = email+" "+password;
                    editor.putString("isLogin","true");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),x, LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Somthing went wrong", LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isPasswordValid(String password) {
        if(password.isEmpty()){
            et_password.setError("Password can not be empty");
            return false;
        }
        else if(password.length() > 8){
            et_password.setError("Password must be 8 char long");
            return false;
        }
        else{
            et_password.setError(null);
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        if(email.isEmpty()){
            et_email.setError("Email can not be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Invalid Email !");
            return false;
        }
        else{
            et_email.setError(null);
            return true;
        }

    }

    private Boolean checkKeyExists(SharedPreferences preferences,String key) {
        String kValue = preferences.getString(key,"false");
        Log.d("tag ::",kValue);
        if(kValue.equals("true")) return true;
        else return false;
    }
}