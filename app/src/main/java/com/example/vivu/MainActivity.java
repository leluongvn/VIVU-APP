package com.example.vivu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonLoginEmail, mButtonLoginFacebook, mButtonLogin, mButtonRegister;


//    CallbackManager callbackManager = CallbackManager.Factory.create();
//    private static final String EMAIL = "email";
//    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        loginButton = findViewById(R.id.login_buttonFacebook);
//        loginButton.setReadPermissions(Arrays.asList(EMAIL));


        mapping();
        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btnRegisterMain) {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }

    }

//    private void loginWithFacebook() {
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCancel() {
//                // App code
//                Toast.makeText(MainActivity.this, "Login cancel", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
//                Log.e("Error login facebook ",""+exception);
//            }
//        });
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void loginWithEmail() {
    }

    private void mapping() {


        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonRegister = findViewById(R.id.btnRegisterMain);
    }
}