package com.example.vivu.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vivu.LoginActivity;
import com.example.vivu.R;
import com.example.vivu.UpdateActivity;
import com.example.vivu.adapters.AdapterPostDiscover;
import com.example.vivu.models.Post;
import com.example.vivu.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    private List<Post> mPostList = new ArrayList<>();
    private AdapterPostDiscover adapterPostDiscover;
    private RecyclerView mRecyclerView;
    private ImageView mImageViewBackground;
    private CircleImageView mImageViewAvatar, mImageViewUploadBGR, mImageViewUploadAvatar;
    private TextView mTextViewName;
    private FirebaseUser mFirebaseUser;
    private StorageReference mStorageReference;
    private DatabaseReference mReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.
            Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable
                                     Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
//        mRecyclerView = view.findViewById(R.id.rcPostAccount);
        mImageViewAvatar = view.findViewById(R.id.imvAvatar);
        mImageViewBackground = view.findViewById(R.id.imvBackground);
        mImageViewUploadAvatar = view.findViewById(R.id.imvUploadAvatar);
        mImageViewUploadBGR = view.findViewById(R.id.imvUploadBackground);
        mTextViewName = view.findViewById(R.id.tvNameAccount);
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mReference = FirebaseDatabase.getInstance().getReference("users").
                child(mFirebaseUser.getUid());
        Log.e("NULL", mFirebaseUser.getUid());


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mTextViewName.setText(user.getUsername());
                if (user.getImage().equals("default")) {
                    mImageViewAvatar.setImageResource(R.drawable.account);
                } else {
                    Glide.with(getContext()).load(user.getImage()).into(mImageViewAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
//        setData();
        mImageViewUploadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadAvatar();
            }
        });

        return view;
    }

    private void UploadAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    private void setData() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mTextViewName.setText(user.getUsername());
                if (user.getImage().equals("default")) {
                    mImageViewAvatar.setImageResource(R.drawable.account);
                } else {
                    Glide.with(getContext()).load(user.getImage()).into(mImageViewAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getAndSetData(RecyclerView recyclerView) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post post = snapshot1.getValue(Post.class);
//                    if (post.getEmailOwnerPost().trim().equals()) {
//                        mPostList.add(post);
//                    }
                }
                adapterPostDiscover = new AdapterPostDiscover(mPostList, getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerView.setAdapter(adapterPostDiscover);
                recyclerView.setLayoutManager(manager);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("ERROR", "" + error);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
        if (uploadTask != null && uploadTask.isInProgress()) {
            Toast.makeText(getContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
        } else {
            uploadImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        mReference = FirebaseDatabase.getInstance().getReference("users")
                                .child(mFirebaseUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image", "" + mUri);
                        mReference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
