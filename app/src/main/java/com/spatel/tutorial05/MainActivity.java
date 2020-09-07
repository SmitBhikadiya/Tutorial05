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
    private CheckBox remeberMe;
    private String em,pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("LogState",MODE_PRIVATE);
        editor = preferences.edit();

        // check user is login or not
        if(!checkKeyExists(preferences,"isLogin")){
            em = preferences.getString("Email","");
            pa = preferences.getString("Password","");
            setContentView(R.layout.activity_main);
        }
        else{
            Intent intent =new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        // get reference to control it
        btn_login = findViewById(R.id.btnLogin);
        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        remeberMe = findViewById(R.id.rememberMe);

        // set content in editbox if user check remember me or not
        et_email.setText(em);
        et_password.setText(pa);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString().trim().toLowerCase();
                String password = et_password.getText().toString().trim();

                if(isEmailValid(email) && isPasswordValid(password)) {
                    if(remeberMe.isChecked()){
                        editor.putString("Email",email);
                        editor.putString("Password",password);
                    }
                    else{
                        editor.remove("Email");
                        editor.remove("Password");
                    }
                    editor.putString("isLogin","true");
                    editor.commit();
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
        else if(password.length() <= 8){
            Log.d("pass",password);
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