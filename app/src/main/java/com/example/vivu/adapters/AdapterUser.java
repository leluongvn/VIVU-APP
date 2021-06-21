package com.example.vivu.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivu.MessageActivity;
import com.example.vivu.R;
import com.example.vivu.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.viewHolderUser> {
    private Context mContext;
    private List<User> userList = new ArrayList<>();

    public AdapterUser(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolderUser onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new viewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterUser.viewHolderUser holder, int position) {
        User user = userList.get(position);
        holder.mTextViewName.setText(user.getUsername());
        if (user.getImage() == "" || user.getImage().isEmpty()) {
            holder.mImageViewAvatar.setImageResource(R.drawable.account);
        } else {
            holder.mImageViewAvatar.setImageBitmap(StringToBitMap(user.getImage()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("email",user.getEmail());
//                intent.putExtra("image",user.getImageUser());
//                intent.putExtra("name",user.getUserName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class viewHolderUser extends RecyclerView.ViewHolder {
        ImageView mImageViewAvatar;
        TextView mTextViewName;

        public viewHolderUser(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageViewAvatar = itemView.findViewById(R.id.imvAvatarItemUser);
            mTextViewName = itemView.findViewById(R.id.tvItemUser);
        }
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
