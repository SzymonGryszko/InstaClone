package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.instaclone.utils.BottomNavigationBarHelper;
import com.example.instaclone.R;
import com.example.instaclone.utils.UniversalImageLoader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileActivity extends AppCompatActivity {

    private Context mContext = ProfileActivity.this;
    private DrawerLayout drawer;
    private ProgressBar mProgressBar;
    private TextView editProfileTextView;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setActivityWidgets();
        setBottomNavigationMenu();
        setupToolbar(drawer);
        setViewPager();
        setProfileImage();

    }

    private void setProfileImage() {

        String imageUrl = "instagram.fpoz2-1.fna.fbcdn.net/v/t51.2885-19/11887042_660706700696553_1149097263_a.jpg?_nc_ht=instagram.fpoz2-1.fna.fbcdn.net&_nc_ohc=oevHfjXIKYEAX8RzPpK&oh=24d925e868be408112edc74f8f33d50b&oe=5EF08EAA";
        UniversalImageLoader.setImage(imageUrl, profileImage, mProgressBar, "https://");

    }

    private void setActivityWidgets() {
        drawer = findViewById(R.id.drawer_layout);
        mProgressBar = findViewById(R.id.profile_progressbar);
        mProgressBar.setVisibility(View.GONE);
        profileImage = findViewById(R.id.profile_image);
        editProfileTextView = findViewById(R.id.textEditProfile);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setViewPager() {
        ViewPager2 viewPager2 = findViewById(R.id.profileViewPager);
        viewPager2.setAdapter(new ProfileSectionPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.profileTabs);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_person);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_message);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void setupToolbar(final DrawerLayout drawer) {
        Toolbar toolbar = findViewById(R.id.profile_settings_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private void setBottomNavigationMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationBarHelper.setBottomNavigationBar(mContext, bottomNavigationView);
    }
}
