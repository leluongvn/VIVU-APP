package com.example.vivu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vivu.adapters.AdapterMessage;
import com.example.vivu.models.Chat;
import com.example.vivu.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private Intent mIntent;
    private String mEmailReceiver;
    private String mImage;
    private String mName;
    private CircleImageView mImageViewAvatar;
    private TextView mTextViewName;
    private ImageButton mButtonSend;
    private EditText mEditTextContent;
    private String mEmailCurrent;
    private RecyclerView mRecyclerViewMessage;
    private List<Chat> chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mapping();


        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextContent.getText().toString().isEmpty()) {
                    Toast.makeText(MessageActivity.this, "Nội dung trống",
                            Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(mEmailCurrent, mEmailReceiver, mEditTextContent.getText().toString());
                    mEditTextContent.setText("");
                }
            }
        });
        setUser(mEmailReceiver);

        readMessage(mEmailCurrent, mEmailReceiver, "");

    }

    private void readMessage(String sender, String receiver, String image) {
        chatList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chat chat = snapshot1.getValue(Chat.class);
                    if (chat.getSender().equals(sender) && chat.getReceiver().equals(receiver)
                            || chat.getSender().equals(receiver) && chat.getReceiver().equals(sender)) {
                        chatList.add(chat);
                    }
                }
                mRecyclerViewMessage.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setStackFromEnd(true);
                mRecyclerViewMessage.setLayoutManager(linearLayoutManager);
                AdapterMessage message = new AdapterMessage(getApplicationContext(), chatList, image);
                mRecyclerViewMessage.setAdapter(message);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void addToUserHaveMessage(String emailSend, String emailReceiver) {
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(emailSend)
                .child(emailReceiver);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(emailReceiver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(emailReceiver)
                .child(emailSend);
        chatRefReceiver.child("id").setValue(emailSend);



    }

    private void setUser(String mEmailCurrent) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if (user.getEmail().equals(mEmailCurrent)) {
                        mTextViewName.setText(user.getUsername());
                        String image = user.getImage();
                        if (image.isEmpty()) {
                            mImageViewAvatar.setImageResource(R.drawable.account);
                        } else {
                            mImageViewAvatar.setImageBitmap(StringToBitMap(image));
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chat");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        reference.push().setValue(hashMap);

        addToUserHaveMessage(sender,receiver);

    }

    private void mapping() {
        mImageViewAvatar = findViewById(R.id.profile_image);
        mTextViewName = findViewById(R.id.username);
        mButtonSend = findViewById(R.id.btn_send);
        mEditTextContent = findViewById(R.id.text_send);
        mRecyclerViewMessage = findViewById(R.id.recycler_view);
        mIntent = getIntent();
        mEmailReceiver = mIntent.getStringExtra("email");

        SharedPreferences preferences = getApplicationContext().
                getSharedPreferences("login", Context.MODE_PRIVATE);

        mEmailCurrent = preferences.getString("email", null);
        Toast.makeText(this, "" + mEmailCurrent, Toast.LENGTH_SHORT).show();

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