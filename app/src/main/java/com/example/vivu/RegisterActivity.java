package com.example.vivu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private TextInputEditText mTextUserName, mTextNumberPhone, mTextEmail,
            mTextPassWord, mTextPassWordConfirm;
    private TextInputLayout mLayoutUser, mLayoutEmail, mLayoutPhone,
            mLayoutPassword, mLayoutPasswordConfirm;
    private AppCompatButton mButtonRegister;
    private DatabaseReference mDatabase;
    private List<User> mUserList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiester);
        mapping();

        mFirebaseAuth = FirebaseAuth.getInstance();
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

        mFirebaseAuth.createUserWithEmailAndPassword(emailUser, passwordConfirm)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            String idUser = user.getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users").child(idUser);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", idUser);
                            hashMap.put("username", userName);
                            hashMap.put("phone", numberPhone);
                            hashMap.put("image", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("email", emailUser);
                            hashMap.put("password", passwordConfirm);
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }

                    }
                });


    }

    private void insertUser(User user) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");
            myRef.push().setValue(user);
            Toast.makeText(this, "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Register-Faill", "" + e);
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

//    private boolean validateEmail(String email) {
//
//        Log.e("TEST", "" + mUserList.size());
//
//        if (email.isEmpty()) {
//            mLayoutEmail.setError("Nhập email ! ");
//            return false;
//        }
//        mLayoutEmail.setErrorEnabled(false);
//        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
//
//        if (!isValid) {
//            mLayoutEmail.setError("Nhập đúng định dạng . ex : abc@gmail.com");
//            return false;
//        }
//        for (User user : mUserList) {
//            if (email.trim().equals(user.getEmailUser())) {
//                Log.e("EMAIL", "" + user.getEmailUser());
//                mLayoutEmail.setError("Email đã đăng kí , hãy sử dụng email khác");
//                return false;
//            }
//        }
//
//        mLayoutEmail.setErrorEnabled(false);
//        return true;
//    }

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

//    private List<User> getData() {
//
//        List<User> arrayList = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    User user = snapshot1.getValue(User.class);
//                    Log.e("USERNAME", "" + user.getUserName());
//                    arrayList.add(user);
//                }
//                Log.e("DATA", "" + arrayList.size());
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//                Log.e("GetUser-Error", "" + error);
//            }
//        });
//        return arrayList;
//
//    }
}