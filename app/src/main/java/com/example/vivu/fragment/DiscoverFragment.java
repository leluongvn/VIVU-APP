package com.example.vivu.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivu.R;
import com.example.vivu.adapters.AdapterPostDiscover;
import com.example.vivu.convert_data.ConvertImage;
import com.example.vivu.models.Post;
import com.example.vivu.models.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    ImageView mImageViewAvatar;
    AppCompatButton mEditTextPost;
    RecyclerView mRecyclerViewPost;
    AdapterPostDiscover mAdapterPostDiscover;


    static final int CODE_FOLDER = 999;
    Bitmap bitmapImages = null;
    String name = null;
    String email = null;
    String imvAvatar;
    List<Post> mPostList = new ArrayList<>();
    String imageAvatar;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        mImageViewAvatar = view.findViewById(R.id.imvAvatarDiscover);
        mEditTextPost = view.findViewById(R.id.edtPost);
        mRecyclerViewPost = view.findViewById(R.id.rcPostDiscover);
        mapping();
        mImageViewAvatar.setImageBitmap(StringToBitMap(imageAvatar));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post mPost = snapshot1.getValue(Post.class);
                    mPostList.add(mPost);
                }
                mAdapterPostDiscover = new AdapterPostDiscover(mPostList, getContext());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerViewPost.setAdapter(mAdapterPostDiscover);
                mRecyclerViewPost.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("ERROR-GET DATA", " " + error);
            }
        });


        mEditTextPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });

        return view;

    }

    private void mapping() {
        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        name = preferences.getString("name", null);
        email = preferences.getString("email", null);
        imageAvatar = preferences.getString("image",null);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    User mUser = snapshot.getValue(User.class);
//                    if (mUser.getEmailUser().equals(email)) {
//                        name = mUser.getUserName();
//                        imageAvatar = mUser.getImageUser();
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


    }


    private void addPost() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(getContext());
        sheetDialog.setContentView(R.layout.item_post);
        AppCompatButton mButtonAddImage = sheetDialog.findViewById(R.id.btnUploadImage);
        TextView mTextViewName = sheetDialog.findViewById(R.id.tvNameAccountPost);
        ImageView mImageViewAvatar = sheetDialog.findViewById(R.id.imvAvatarPost);
        EditText mEditTextDetail = sheetDialog.findViewById(R.id.edtDetailPost);
        ImageView mImageViewImagePost = sheetDialog.findViewById(R.id.imvPost);
        TextView mTextViewAddPost = sheetDialog.findViewById(R.id.tvPost);
        sheetDialog.show();
        mTextViewName.setText(name);
        mImageViewAvatar.setImageBitmap(StringToBitMap(imageAvatar));
        mButtonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CODE_FOLDER);
            }
        });
        mTextViewAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detail = mEditTextDetail.getText().toString();
                if (detail.isEmpty()) {
                    mEditTextDetail.setHint("Nội dung bài viết của bạn là gì ?");
                    mEditTextDetail.setHintTextColor(getResources().getColor(R.color.red));
                    return;
                }
                if (bitmapImages == null) {
                    Toast.makeText(getContext(), "Bạn nên thêm ảnh vào bài biết ",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    Post post = new Post(detail, name, email, BitMapToString(bitmapImages), imageAvatar);
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference reference = mDatabase.getReference("post");
                    reference.push().setValue(post);
                    Toast.makeText(getContext(), "Đã đăng", Toast.LENGTH_SHORT).show();
                    mPostList.clear();
                    sheetDialog.dismiss();
                } catch (Exception e) {
                    Log.e("ERROR-INSERT_POSRT", " " + e);
                }
            }
        });

    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (requestCode == CODE_FOLDER && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapImages = bitmap;
                Toast.makeText(getContext(), "Thêm ảnh thành công", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "Thêm ảnh thất bại", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
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

}
