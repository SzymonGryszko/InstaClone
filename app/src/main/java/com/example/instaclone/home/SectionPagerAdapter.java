package com.example.instaclone.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerAdapter extends FragmentStateAdapter {

    public SectionPagerAdapter(@NonNull HomeActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CameraFragment();
            case 1:
                return new HomeFragment();
            default:
                return new MessagesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
