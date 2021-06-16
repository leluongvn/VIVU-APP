package com.example.vivu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vivu.fragment.AccountFragment;
import com.example.vivu.models.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private TextInputEditText mTextUserName, mTextNumberPhone, mTextEmail,
            mTextPassWord, mTextPassWordConfirm;
    private TextInputLayout mLayoutUser, mLayoutEmail, mLayoutPhone,
            mLayoutPassword, mLayoutPasswordConfirm;
    private AppCompatButton mButtonUpdate;
    private ShapeableImageView mImageViewAvatar, mImageViewUpload;
    private String email;
    private User mUserAccount;
    private Bitmap mBitmap;
    private String key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mapping();
        getData();
        mImageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadImage();
            }
        });

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();

            }
        });


    }

    private void checkData() {
        String name = mTextUserName.getText().toString();
        String phone = mTextNumberPhone.getText().toString();
        String email = mTextEmail.getText().toString();
        String password = mTextPassWord.getText().toString();
        String passwordConfirm = mTextPassWordConfirm.getText().toString();
        if (name.isEmpty()) {
            mLayoutUser.setError("Nhập tên người dùng");
            return ;
        }
        mLayoutUser.setErrorEnabled(false);
        if (phone.isEmpty()) {
            mLayoutPhone.setError("Nhập số điện thoại");
            return ;
        }
        mLayoutPhone.setErrorEnabled(false);
        if (password.isEmpty()) {
            mLayoutPassword.setError("Nhập mật khẩu");
            return ;
        }
        mLayoutEmail.setErrorEnabled(false);
        if (passwordConfirm.isEmpty()) {
            mLayoutPasswordConfirm.setError("Nhập mật khẩu");
            return ;
        }
        mLayoutPasswordConfirm.setErrorEnabled(false);
        if (!passwordConfirm.equals(mUserAccount.getPasswordUser())) {
            mLayoutPasswordConfirm.setError("Mật khẩu của bạn không đúng");
            return ;
        }
        String image = null;
        if (mBitmap != null){
            image = BitMapToString(mBitmap);
        }
        mLayoutPasswordConfirm.setErrorEnabled(false);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(key).child("numberPhoneUser").setValue(phone);
        reference.child(key).child("passwordUser").setValue(password);
        reference.child(key).child("userName").setValue(name);
        reference.child(key).child("imageUser").setValue(image);
        Intent intent = new Intent(UpdateActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    private void upLoadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 111);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations
            .Nullable Intent data) {
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mBitmap = bitmap;
                mImageViewAvatar.setImageBitmap(mBitmap);
                Toast.makeText(getApplicationContext(), "Thêm ảnh thành công", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        email = preferences.getString("email", null);
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference().child("users");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User mUser = snapshot1.getValue(User.class);
                    if (mUser.getEmailUser().equals(email)) {
                        mUserAccount = mUser;
                        key = snapshot1.getKey();
                        setData();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("ERROR", "" + error);
            }
        });
    }

    private void setData() {
        mTextUserName.setText(mUserAccount.getUserName());
        mTextNumberPhone.setText(mUserAccount.getNumberPhoneUser());
        mTextEmail.setText(mUserAccount.getEmailUser());
        if (mUserAccount.getImageUser().isEmpty()) {
            mImageViewAvatar.setImageDrawable(getDrawable(R.drawable.account));
        } else {
            mImageViewAvatar.setImageBitmap(StringToBitMap(mUserAccount.getImageUser()));
        }

    }

    private void mapping() {
        mTextUserName = findViewById(R.id.edtUserNameUpdate);
        mTextNumberPhone = findViewById(R.id.edtNumberPhoneUpdate);
        mTextEmail = findViewById(R.id.edtEmailUpdate);
        mTextPassWord = findViewById(R.id.edtPassWordUpdate);
        mTextPassWordConfirm = findViewById(R.id.edtPassWordUpdateConfirm);
        mLayoutEmail = findViewById(R.id.tipEmailUpdate);
        mLayoutPhone = findViewById(R.id.tipNumberPhoneUpdate);
        mLayoutUser = findViewById(R.id.tipUserNameUpdate);
        mLayoutPassword = findViewById(R.id.tipPassWordUpdate);
        mLayoutPasswordConfirm = findViewById(R.id.tipPassWordUpdateConfirm);
        mButtonUpdate = findViewById(R.id.btnUpdateAccount);
        mImageViewAvatar = findViewById(R.id.imvAvatarUpdate);
        mImageViewUpload = findViewById(R.id.imvUploadAvatar);
    }

    private Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            Log.e("ERROR-CONVERT", "" + e);
            return null;
        }
    }

    private String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}