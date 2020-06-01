package com.example.instaclone.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.instaclone.login.LoginScreen;
import com.example.instaclone.utils.BottomNavigationBarHelper;
import com.example.instaclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity {

    private Context mContext = HomeActivity.this;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
 //       mAuth.signOut();

        setContentView(R.layout.activity_home);
        setBottomNavigationMenu();
        setViewPager();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
        }
    }


    private void setViewPager() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new SectionPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabs);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_camera);
                        break;
                    case 1:
                        tab.setIcon(getDrawable(R.drawable.insta_watercolor));
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_message);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

    }

    private void setBottomNavigationMenu() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        BottomNavigationBarHelper.setBottomNavigationBar(mContext, bottomNavigationView);
    }

}
