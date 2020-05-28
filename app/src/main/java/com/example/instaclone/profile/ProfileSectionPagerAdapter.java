package com.example.instaclone.profile;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileSectionPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MyApp";
    Fragment myPicturesFragment;
    Fragment taggedPicturesFragemnt;

    public ProfileSectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        myPicturesFragment = new MyPicturesFragment();
        taggedPicturesFragemnt = new TaggedPicturesFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return myPicturesFragment;
            default:
                return taggedPicturesFragemnt;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


//    public ProfileSectionPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//        myPicturesFragment = new MyPicturesFragment();
//        taggedPicturesFragemnt = new TaggedPicturesFragment();
//        Log.d(TAG, "ProfileSectionPagerAdapter: New Profile Section Pager Adapter");
//    }
//
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return myPicturesFragment;
//            default:
//                return taggedPicturesFragemnt;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }
}
