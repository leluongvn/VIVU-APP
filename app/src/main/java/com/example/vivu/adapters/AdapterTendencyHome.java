package com.example.vivu.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vivu.R;
import com.example.vivu.models.Tendency;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterTendencyHome extends RecyclerView.Adapter<AdapterTendencyHome.ViewHolderTendency> {

    private Context mContext;
    private List<Tendency> mTendencies;

    public AdapterTendencyHome(Context mContext, List<Tendency> mTendencies) {
        this.mContext = mContext;
        this.mTendencies = mTendencies;
    }

    @NonNull
    @Override
    public ViewHolderTendency onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        Context mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //tao view hien thi
        View view = inflater.inflate(R.layout.item_tendency, parent, false);
        ViewHolderTendency viewHolderTendency = new ViewHolderTendency(view);
        return viewHolderTendency;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterTendencyHome.ViewHolderTendency holder, int position) {
        Tendency tendency = mTendencies.get(position);
        holder.mImageView.setImageURI(Uri.parse(tendency.getImagesCity()));

        Glide.with(mContext).load(tendency.getImagesCity()).into(holder.mImageView);
        holder.mButton.setText(tendency.getCityName());
    }

    @Override
    public int getItemCount() {
        return mTendencies.size();
    }

    public class ViewHolderTendency extends RecyclerView.ViewHolder {
        ImageView mImageView;
        AppCompatButton mButton;
        View iView;

        public ViewHolderTendency(@NonNull @NotNull View itemView) {
            super(itemView);
            iView = itemView;
            mButton = itemView.findViewById(R.id.btnTendency);
            mImageView = itemView.findViewById(R.id.imvTendency);
        }
    }
}
