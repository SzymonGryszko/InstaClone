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
import com.example.instaclone.utils.PicassoAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class TaggedPicturesFragment extends Fragment {

    private static final int GRID_COLUMN_NUMBER = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tagged_pictures, container, false);

        GridView gridView = view.findViewById(R.id.taggedpictures_grid_view);
        ArrayList<String> imgURLs = getImageURLs();
        int width = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = width / GRID_COLUMN_NUMBER;
        gridView.setColumnWidth(imageWidth);

        gridView.setAdapter(new PicassoAdapter(view.getContext(), imgURLs));

        return view;
    }

    private ArrayList<String> getImageURLs() {
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://i.redd.it/9bf67ygj710z.jpg");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");
        imgURLs.add("https://i.redd.it/j2glm9bbeg051.png");



        return imgURLs;
    }
}
