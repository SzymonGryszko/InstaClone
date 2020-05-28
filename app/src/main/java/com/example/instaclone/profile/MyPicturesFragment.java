package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.instaclone.R;
import com.example.instaclone.utils.PicassoAdapter;

import java.util.ArrayList;

public class MyPicturesFragment extends Fragment {

    private static final int GRID_COLUMN_NUMBER = 3;
    private static final String ADAPTER_STATE = "ADAPTER_STATE";
    private static final String TAG = "MyApp";
    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_pictures, container, false);
        gridView = view.findViewById(R.id.mypictures_grid_view);
        ArrayList<String> imgURLs = getImageURLs();
        int width = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = width / GRID_COLUMN_NUMBER;

        gridView.setColumnWidth(imageWidth);
        gridView.setAdapter(new PicassoAdapter(view.getContext(),imgURLs));

        return view;
    }

    private ArrayList<String> getImageURLs() {
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("https://i.redd.it/q3g5motlbe151.jpg");
        imgURLs.add("https://i.redd.it/7j1q4oeczd151.jpg");
        imgURLs.add("https://i.redd.it/nenm7ud6fe151.jpg");
        imgURLs.add("https://i.redd.it/efji6i28fa151.jpg");
        imgURLs.add("https://i.redd.it/xq8forhj4e151.jpg");
        imgURLs.add("https://i.redd.it/4589j03uqe151.jpg");
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("https://i.redd.it/q3g5motlbe151.jpg");
        imgURLs.add("https://i.redd.it/7j1q4oeczd151.jpg");
        imgURLs.add("https://i.redd.it/nenm7ud6fe151.jpg");
        imgURLs.add("https://i.redd.it/efji6i28fa151.jpg");
        imgURLs.add("https://i.redd.it/xq8forhj4e151.jpg");
        imgURLs.add("https://i.redd.it/4589j03uqe151.jpg");
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("https://i.redd.it/q3g5motlbe151.jpg");
        imgURLs.add("https://i.redd.it/7j1q4oeczd151.jpg");
        imgURLs.add("https://i.redd.it/nenm7ud6fe151.jpg");
        imgURLs.add("https://i.redd.it/efji6i28fa151.jpg");
        imgURLs.add("https://i.redd.it/xq8forhj4e151.jpg");
        imgURLs.add("https://i.redd.it/4589j03uqe151.jpg");
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("https://i.redd.it/q3g5motlbe151.jpg");
        imgURLs.add("https://i.redd.it/7j1q4oeczd151.jpg");
        imgURLs.add("https://i.redd.it/nenm7ud6fe151.jpg");
        imgURLs.add("https://i.redd.it/efji6i28fa151.jpg");
        imgURLs.add("https://i.redd.it/xq8forhj4e151.jpg");
        imgURLs.add("https://i.redd.it/4589j03uqe151.jpg");
        imgURLs.add("https://i.redd.it/4589j03uqe151.jpg");


        return imgURLs;
    }


}
