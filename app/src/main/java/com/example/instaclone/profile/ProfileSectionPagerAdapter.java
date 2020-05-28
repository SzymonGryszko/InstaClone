package com.example.instaclone.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileSectionPagerAdapter extends FragmentStateAdapter {
    private static final String TAG = "MyApp";
    Fragment myPicturesFragment;
    Fragment taggedPicturesFragemnt;

    public ProfileSectionPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        myPicturesFragment = new MyPicturesFragment();
        taggedPicturesFragemnt = new TaggedPicturesFragment();
        Log.d(TAG, "ProfileSectionPagerAdapter: New Profile Section Pager Adapter");
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return myPicturesFragment;
            default:
                return taggedPicturesFragemnt;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
