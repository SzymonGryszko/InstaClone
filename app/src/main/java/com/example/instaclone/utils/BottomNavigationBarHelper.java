package com.example.instaclone.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.instaclone.R;
import com.example.instaclone.alert.AlertActivity;
import com.example.instaclone.home.HomeActivity;
import com.example.instaclone.plus.PlusActivity;
import com.example.instaclone.profile.ProfileActivity;
import com.example.instaclone.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationBarHelper {

    public static void setBottomNavigationBar(final Context context, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_house:
                        Intent intentHomeActivity = new Intent(context, HomeActivity.class);
                        context.startActivity(intentHomeActivity);
                        break;
                    case R.id.ic_search:
                        Intent intentSearchActivity = new Intent(context, SearchActivity.class);
                        item.setChecked(true);
                        context.startActivity(intentSearchActivity);
                        break;
                    case R.id.ic_plus:
                        Intent intentPlusActivity = new Intent(context, PlusActivity.class);
                        context.startActivity(intentPlusActivity);
                        break;
                    case R.id.ic_aler:
                        Intent intentAlertActivity = new Intent(context, AlertActivity.class);
                        context.startActivity(intentAlertActivity);
                        break;
                    case R.id.ic_person:
                        Intent intentProfileActivity = new Intent(context, ProfileActivity.class);
                        context.startActivity(intentProfileActivity);
                        break;
                }
                return false;
            }
        });
    }

}
