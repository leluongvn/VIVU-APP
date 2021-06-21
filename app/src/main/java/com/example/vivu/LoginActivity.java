package com.example.vivu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.vivu.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton mButtonLogin;
    TextInputLayout mLayoutNumberPhone, mLayoutPassword;
    TextInputEditText mEditTextNumberPhone, mEditTextPassWord;
    List<User> userList = new ArrayList<>();
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButtonLogin = findViewById(R.id.btnLoginHome);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mapping();


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        String email = mEditTextNumberPhone.getText().toString();
        String passWord = mEditTextPassWord.getText().toString();
        if (email.isEmpty()) {
            mLayoutNumberPhone.setError("Nhập email của bạn ");
            return;
        }
        mLayoutNumberPhone.setErrorEnabled(false);
        if (passWord.isEmpty()) {
            mLayoutPassword.setError("Nhập mật khẩu của bạn");
            return;
        }
        mLayoutPassword.setErrorEnabled(false);
        mFirebaseAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Kiểm tra lại email và mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void mapping() {
        mLayoutNumberPhone = findViewById(R.id.tipNumberPhoneLogin);
        mLayoutPassword = findViewById(R.id.tipPasswordLogin);
        mEditTextNumberPhone = findViewById(R.id.edtNumberPhoneLogin);
        mEditTextPassWord = findViewById(R.id.edtPasswordLogin);


    }
}