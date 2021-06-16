package com.example.vivu.adapters;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vivu.R;
import com.example.vivu.convert_data.ConvertImage;
import com.example.vivu.models.Post;
import com.example.vivu.models.User;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

import java.util.List;

public class AdapterPostDiscover extends RecyclerView.Adapter<AdapterPostDiscover.viewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public AdapterPostDiscover(List<Post> mPosts, Context mContext) {
        this.mPosts = mPosts;
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

    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterPostDiscover.viewHolder holder, int position) {
        Post mPost = mPosts.get(position);
        holder.mTextViewName.setText(mPost.getOwnerPost());
        holder.mTextViewDetail.setText(mPost.getDetailPost());
        holder.mImageViewAvatar.setImageBitmap(StringToBitMap(mPost.getImageUserPost()));
        holder.mImageViewPost.setImageBitmap(StringToBitMap(mPost.getImagePost()));

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
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

    public Bitmap StringToBitMap(String encodedString) {
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
