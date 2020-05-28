package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.instaclone.utils.BottomNavigationBarHelper;
import com.example.instaclone.R;
import com.example.instaclone.utils.SquareImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String TAG = "MyApp";

    private Context mContext = ProfileActivity.this;
    private DrawerLayout drawer;
    private ProgressBar mProgressBar;
    private TextView editProfileTextView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setActivityWidgets();
        setBottomNavigationMenu();
        setupToolbar(drawer);
        setProfileImage();

        int viewPagerPosition = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_POSITION);


        viewPager = findViewById(R.id.profileViewPager);
        viewPager.setAdapter(new ProfileSectionPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        viewPager.setCurrentItem(viewPagerPosition, true);
        Log.d(TAG, "onCreate: Profile Activity onCreate");


        TabLayout tabLayout = findViewById(R.id.profileTabs);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_message);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_POSITION, viewPager.getCurrentItem());
    }

    private void setProfileImage() {
        CircleImageView profileImage = findViewById(R.id.profile_image);
        String imageUrl = "https://instagram.fpoz2-1.fna.fbcdn.net/v/t51.2885-19/11887042_660706700696553_1149097263_a.jpg?_nc_ht=instagram.fpoz2-1.fna.fbcdn.net&_nc_ohc=oevHfjXIKYEAX8RzPpK&oh=24d925e868be408112edc74f8f33d50b&oe=5EF08EAA";
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(profileImage);

    }

    private void setActivityWidgets() {
        drawer = findViewById(R.id.drawer_layout);
        mProgressBar = findViewById(R.id.profile_progressbar);
        mProgressBar.setVisibility(View.GONE);
        editProfileTextView = findViewById(R.id.textEditProfile);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
            }
        });

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
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
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
