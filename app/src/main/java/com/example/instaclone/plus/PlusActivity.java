package com.example.instaclone.plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instaclone.utils.BottomNavigationBarHelper;
import com.example.instaclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlusActivity extends AppCompatActivity {

    private Context mContext = PlusActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        setBottomNavigationMenu();
    }

    private void setBottomNavigationMenu() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        BottomNavigationBarHelper.setBottomNavigationBar(mContext, bottomNavigationView);
    }
}
