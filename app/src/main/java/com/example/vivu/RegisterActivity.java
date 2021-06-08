package com.example.vivu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import com.example.vivu.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mTextUserName, mTextNumberPhone, mTextEmail,
            mTextPassWord, mTextPassWordConfirm;
    private TextInputLayout mLayoutUser, mLayoutEmail, mLayoutPhone,
            mLayoutPassword, mLayoutPasswordConfirm;

    private AppCompatButton mButtonRegister;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiester);
        mapping();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String userName = mTextUserName.getText().toString();
        String emailUser = mTextEmail.getText().toString();
        String numberPhone = mTextNumberPhone.getText().toString();
        String password = mTextPassWord.getText().toString();
        String passwordConfirm = mTextPassWordConfirm.getText().toString();
        if (checkUserName(userName) && validatePhone(numberPhone) && validateEmail(emailUser) && checkPassword(password, passwordConfirm)) {
            try {
                User user = new User(userName, emailUser, numberPhone, passwordConfirm);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.push().setValue(user);
                Toast.makeText(this, "Login donee !!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("RegisterError", " " + e);
            }
        }
    }

    private boolean checkPassword(String password, String passwordConfirm) {
        if (password.isEmpty()) {
            mLayoutPassword.setError("Nhập mật khẩu của bạn");
            return false;
        }
        mLayoutPassword.setErrorEnabled(false);
        if (password.length() < 6) {
            mLayoutPassword.setError("Password phải >=6 kí tự");
            return false;
        }
        mLayoutPassword.setErrorEnabled(false);
        if (passwordConfirm.isEmpty()) {
            mLayoutPasswordConfirm.setError("Nhập mật khẩu xác nhận ");
            return false;
        }
        mLayoutPasswordConfirm.setErrorEnabled(false);
        if (!password.trim().equals(passwordConfirm.trim())) {
            mLayoutPasswordConfirm.setError("Mật khẩu không khớp ");
            return false;
        }
        mLayoutPasswordConfirm.setErrorEnabled(false);
        return true;
    }

    private boolean checkUserName(String name) {
        if (name.isEmpty()) {
            mLayoutUser.setError("Tên đăng nhập không thể trống");
            return false;
        }
        mLayoutUser.setErrorEnabled(false);
        if (name.length() < 6) {
            mLayoutUser.setError("Tên đăng nhập >= 6 kí tự");
            return false;
        }
        mLayoutUser.setErrorEnabled(false);

        return true;
    }

    private boolean validatePhone(String phone) {
        if (phone.trim().isEmpty()) {
            mLayoutPhone.setError("Nhập số điện thoại ! ");
            return false;
        }
        mLayoutPhone.setErrorEnabled(false);

        if (phone.length() != 10) {
            mLayoutPhone.setError("Nhập sai định dạng !");
            return false;
        }
        mLayoutPhone.setErrorEnabled(false);

        return true;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            mLayoutEmail.setError("Nhập email ! ");
            return false;
        }
        mLayoutEmail.setErrorEnabled(false);
        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if (!isValid) {
            mLayoutEmail.setError("Nhập đúng định dạng . ex : abc@gmail.com");
            return false;
        }

        mLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private void mapping() {
        mTextUserName = findViewById(R.id.tipUserNameRegister);
        mTextNumberPhone = findViewById(R.id.tipNumberPhoneRegister);
        mTextEmail = findViewById(R.id.tipEmailRegister);
        mTextPassWord = findViewById(R.id.tipPassWordRegister);
        mTextPassWordConfirm = findViewById(R.id.tipPassWordRegisterConfirm);
        mLayoutEmail = findViewById(R.id.tipEmailRegisterLO);
        mLayoutPhone = findViewById(R.id.tipNumberPhoneRegisterLO);
        mLayoutUser = findViewById(R.id.tipUserNameRegisterLO);
        mLayoutPassword = findViewById(R.id.tipPassWordRegisterLO);
        mLayoutPasswordConfirm = findViewById(R.id.tipPassWordRegisterConfirmLO);
        mButtonRegister = findViewById(R.id.btRegister);
    }
}