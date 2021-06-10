package com.example.vivu.adapters;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vivu.R;
import com.example.vivu.models.Post;
import com.example.vivu.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPostDiscover extends RecyclerView.Adapter<AdapterPostDiscover.viewHolder> {

    private List<User> mUsers;
    private Context mContext;

    public AdapterPostDiscover(List<User> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_user_post, parent, false);

        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPostDiscover.viewHolder holder, int position) {
        User mUser = mUsers.get(position);
        holder.mTextViewName.setText(mUser.getUserName());
        Post mPost = mUser.getPostUser();
        holder.mTextViewDetail.setText(mPost.getDetailPost());
        Glide.with(mContext).load(mPost.getImages()).into(holder.mImageViewPost);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewAvatar;
        TextView mTextViewName;
        TextView mTextViewDetail;
        ImageView mImageViewPost;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageViewAvatar = itemView.findViewById(R.id.imvAvatarUserPost);
            mTextViewName = itemView.findViewById(R.id.tvNameUserPost);
            mTextViewDetail = itemView.findViewById(R.id.tvDetailUserPost);
            mImageViewPost = itemView.findViewById(R.id.imvUserPost);
        }
    }
}
