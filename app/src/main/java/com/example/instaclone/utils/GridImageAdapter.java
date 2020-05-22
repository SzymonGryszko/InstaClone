package com.example.instaclone.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instaclone.R;
import com.example.instaclone.profile.MyPicturesFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context mContext, int layoutResource, String mAppend, ArrayList<String> imgURLs) {
        super(mContext, layoutResource, imgURLs);
        this.mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
        this.layoutResource = layoutResource;
        this.mAppend = mAppend;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder {
        SquareImageView image;
        ProgressBar mProgressbar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressbar = convertView.findViewById(R.id.gridImageProgressbar);
            viewHolder.image = convertView.findViewById(R.id.gridImageView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String imgURL = getItem(position);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imgURL, viewHolder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (viewHolder.mProgressbar != null) {
                    viewHolder.mProgressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (viewHolder.mProgressbar != null) {
                    viewHolder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (viewHolder.mProgressbar != null) {
                    viewHolder.mProgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (viewHolder.mProgressbar != null) {
                    viewHolder.mProgressbar.setVisibility(View.GONE);
                }
            }
        });

        return convertView;
    }
}
