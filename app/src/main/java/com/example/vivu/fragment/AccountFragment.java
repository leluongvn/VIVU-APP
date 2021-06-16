package com.example.vivu.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivu.LoginActivity;
import com.example.vivu.R;
import com.example.vivu.UpdateActivity;
import com.example.vivu.adapters.AdapterPostDiscover;
import com.example.vivu.models.Post;
import com.example.vivu.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private List<Post> mPostList = new ArrayList<>();
    private AdapterPostDiscover adapterPostDiscover;
    private RecyclerView mRecyclerView;
    private String email;
    private ImageView mImageViewSetting;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.
            Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable
                                     Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mapping();
        mRecyclerView = view.findViewById(R.id.rcPostAccount);
        mImageViewSetting = view.findViewById(R.id.imvSetting);
        getAndSetData(mRecyclerView);

        mImageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(getContext(), v);
                menu.inflate(R.menu.toolbar_account);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.update: {
                                Intent intent = new Intent(getContext(), UpdateActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.logout: {
                                clearAccount();
                                break;
                            }
                        }
                        return true;
                    }
                });
            }
        });

        return view;
    }

    private void getAndSetData(RecyclerView recyclerView) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Post post = snapshot1.getValue(Post.class);
                    if (post.getEmailOwnerPost().trim().equals(email)) {
                        mPostList.add(post);
                    }
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

    private void clearAccount() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    private void mapping() {
        SharedPreferences preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        email = preferences.getString("email", null);

    }
}
