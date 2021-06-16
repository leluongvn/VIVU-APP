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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        getData();
        mButtonLogin = findViewById(R.id.btnLoginHome);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin()) {
                    Intent intent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkLogin() {

        String numberPhone = mEditTextNumberPhone.getText().toString();
        String passWord = mEditTextPassWord.getText().toString();

        User mUser = null;

        if (numberPhone.isEmpty()) {
            mLayoutNumberPhone.setError("Số điện thoại của bạn là gì ?");
            return false;
        }
        mLayoutNumberPhone.setErrorEnabled(false);

        int temp = 0;
        for (User user : userList) {
            if (numberPhone.trim().equals(user.getNumberPhoneUser().trim())) {
                mUser = user;
                temp++;
                break;

            }
        }
        if (temp == 0) {
            mLayoutNumberPhone.setError("Số điện thoại bạn chưa đăng kí");
            return false;
        } else {
            mLayoutNumberPhone.setEnabled(false);
        }

        mLayoutNumberPhone.setErrorEnabled(false);
        if (passWord.isEmpty()) {
            mLayoutPassword.setError("Bạn phải nhật mật khẩu");
            return false;
        }
        mLayoutPassword.setErrorEnabled(false);
        Log.e("PASS", "" + mUser.getPasswordUser());
        if (!passWord.trim().equals(mUser.getPasswordUser())) {
            mLayoutPassword.setError("Bạn nhập sai mật khẩu");
            return false;
        }
        SharedPreferences preferences = LoginActivity.this.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", mUser.getUserName());
        editor.putString("email", mUser.getEmailUser());
        editor.putString("image",mUser.getImageUser());
        editor.commit();


        return true;
    }


    private void getData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("ERROR-GET DATA", " " + error);
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