package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileSectionPagerAdapter extends FragmentStateAdapter {

    public ProfileSectionPagerAdapter(@NonNull ProfileActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyPicturesFragment();
            default:
                return new TaggedPicturesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
