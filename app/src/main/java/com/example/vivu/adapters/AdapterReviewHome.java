package com.example.vivu.adapters;

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
import com.example.vivu.models.Review;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterReviewHome extends RecyclerView.Adapter<AdapterReviewHome.ViewHolderReview> {

    private Context mContext;
    private List<Review> mReviewList;

    public AdapterReviewHome(Context mContext, List<Review> mReviewList) {
        this.mContext = mContext;
        this.mReviewList = mReviewList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderReview onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_review, parent, false);
        ViewHolderReview review = new ViewHolderReview(view);
        return review;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterReviewHome.ViewHolderReview holder, int position) {
        Review review = mReviewList.get(position);
        Glide.with(mContext).load(review.getImageReview()).into(holder.mImageView);
//    holder.mImageView.setImageURI();
        holder.mTextView.setText(review.getTitleReview());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ViewHolderReview extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        public ViewHolderReview(@NonNull @NotNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imvReviewHome);
            mTextView = itemView.findViewById(R.id.tvReviewHome);
        }
    }

}
