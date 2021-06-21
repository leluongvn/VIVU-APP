package com.example.vivu.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivu.R;
import com.example.vivu.adapters.AdapterUser;
import com.example.vivu.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {
    private RecyclerView mRecyclerViewListUser;
    private RecyclerView mRecyclerViewListMessage;
    private List<User> mUserList = new ArrayList<>();
    private AdapterUser mAdapterUser;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle
                                     savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mRecyclerViewListUser = view.findViewById(R.id.rcUser);
        mRecyclerViewListMessage = view.findViewById(R.id.rcMessage);

        getData();


        return view;
    }

    private void setAdapterUser(RecyclerView recyclerView, AdapterUser adapterUser) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false);
        recyclerView.setAdapter(adapterUser);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    mUserList.add(user);
                }
                Log.e("size", "" + mUserList.size());
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                        RecyclerView.HORIZONTAL, false);
                mAdapterUser = new AdapterUser(getContext(), mUserList);
                mRecyclerViewListUser.setAdapter(mAdapterUser);
                mRecyclerViewListUser.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.e("ERROR-GET DATA", " " + error);
            }
        });


    }


}
