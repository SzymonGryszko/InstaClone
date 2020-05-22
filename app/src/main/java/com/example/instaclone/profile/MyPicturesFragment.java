package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.instaclone.R;
import com.example.instaclone.utils.GridImageAdapter;

import java.util.ArrayList;

public class MyPicturesFragment extends Fragment {

    private static final int GRID_COLUMN_NUMBER = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_pictures, container, false);

        GridView gridView = view.findViewById(R.id.mypictures_grid_view);
        initGridViewAdapter(gridView);

        return view;
    }



    private void initGridViewAdapter(GridView gridView) {

        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg");
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://c1.staticflickr.com/5/4276/34102458063_7be616b993_o.jpg");
        imgURLs.add("http://i.imgur.com/EwZRpvQ.jpg");
        imgURLs.add("http://i.imgur.com/JTb2pXP.jpg");
        imgURLs.add("https://i.redd.it/59kjlxxf720z.jpg");
        imgURLs.add("https://i.redd.it/pwduhknig00z.jpg");
        imgURLs.add("https://i.redd.it/clusqsm4oxzy.jpg");
        imgURLs.add("https://i.redd.it/svqvn7xs420z.jpg");
        imgURLs.add("http://i.imgur.com/j4AfH6P.jpg");
        imgURLs.add("https://i.redd.it/89cjkojkl10z.jpg");
        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");
        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");
        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");
        imgURLs.add("https://i.redd.it/aw7pv8jq4zzy.jpg");

        int width = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = width/GRID_COLUMN_NUMBER;

        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(getContext(), R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);
    }

}
