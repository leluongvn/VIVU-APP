package com.example.vivu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivu.R;
import com.example.vivu.models.Chat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUserMessage extends RecyclerView.Adapter<AdapterUserMessage.viewHolder> {

    private Context mContext;
    private List<Chat> chatList;
    private String image;

    public AdapterUserMessage(Context mContext, List<Chat> chatList, String image) {
        this.mContext = mContext;
        this.chatList = chatList;
        this.image = image;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterUserMessage.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CircleImageView mImageViewAvatar;
        TextView mTextViewDetail;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageViewAvatar = itemView.findViewById(R.id.imvAvatarMessage);
            mTextViewDetail = itemView.findViewById(R.id.tvDetailMessage);
        }
    }
}
