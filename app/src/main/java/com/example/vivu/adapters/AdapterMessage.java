package com.example.vivu.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.vivu.R;
import com.example.vivu.models.Chat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.viewHolder> {
    private Context mContext;
    private List<Chat> chatList;
    private String image;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    public AdapterMessage(Context mContext, List<Chat> chatList, String image) {
        this.mContext = mContext;
        this.chatList = chatList;
        this.image = image;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new AdapterMessage.viewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new AdapterMessage.viewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMessage.viewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.show_message.setText(chat.getMessage());
        if (image == "") {
            holder.profile_image.setImageResource(R.drawable.account);
        } else {
            holder.profile_image.setImageBitmap(StringToBitMap(image));
        }
        if (position == chatList.size() - 1) {
            if (chat.isSeen()) {
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
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

    @Override
    public int getItemViewType(int position) {
        SharedPreferences preferences = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        String emailCurrent = preferences.getString("email", null);
        if (chatList.get(position).getSender().equals(emailCurrent)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
