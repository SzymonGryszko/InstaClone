package com.example.instaclone.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instaclone.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PicassoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> imageURLs;

    public PicassoAdapter(Context context, ArrayList<String> imageURLs) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.imageURLs = imageURLs;
    }

    @Override
    public int getCount() {
        return imageURLs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_grid_imageview_tagged, parent, false);
            holder = new ViewHolder();
            assert view != null;

            holder.imageView = view.findViewById(R.id.gridImageView);
            holder.progressBar = view.findViewById(R.id.gridImageProgressbar);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso mPicasso = Picasso.get();
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(imageURLs.get(position)).placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground).fit().into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    static class  ViewHolder {
        SquareImageView imageView;
        ProgressBar progressBar;
    }
}
