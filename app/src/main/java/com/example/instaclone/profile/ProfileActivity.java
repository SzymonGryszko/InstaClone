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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.instaclone.model.User;
import com.example.instaclone.model.UserAccountSettings;
import com.example.instaclone.model.UserAndSettings;
import com.example.instaclone.utils.BottomNavigationBarHelper;
import com.example.instaclone.R;
import com.example.instaclone.utils.ExitDialog;
import com.example.instaclone.utils.FirebaseMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String TAG = "MyApp";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private Context mContext = ProfileActivity.this;
    private DrawerLayout drawer;
    private ProgressBar mProgressBar;
    private TextView editProfileTextView;
    private ViewPager viewPager;
    private TextView signout;
    private TextView toolbarUsername;
    private TextView posts, followers, following;
    private TextView profileName, description, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initActivityWidgets();
        setBottomNavigationMenu();
        setupToolbar(drawer);
        initFirebase();
        int viewPagerPosition = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_POSITION);
        setViewPager(viewPagerPosition);

    }

    private void setViewPager(int viewPagerPosition) {
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

    private void openExitDialog() {
        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(), "exit_dialog");
    }

    private void initActivityWidgets() {
        drawer = findViewById(R.id.drawer_layout);
        mProgressBar = findViewById(R.id.profile_progressbar);
        editProfileTextView = findViewById(R.id.textEditProfile);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExitDialog();
            }
        });

        toolbarUsername = findViewById(R.id.toolbar_profile_name);
        posts = findViewById(R.id.tvPosts);
        followers = findViewById(R.id.tvFollowers);
        following = findViewById(R.id.tvFollowing);
        profileName = findViewById(R.id.profile_name);
        description = findViewById(R.id.description);
        website = findViewById(R.id.profile_website);
    }

    private void loadWidgetsWithDatabase(UserAndSettings userAndSettings) {

        UserAccountSettings settings = userAndSettings.getSettings();

        toolbarUsername.setText(settings.getUsername());
        posts.setText(String.valueOf(settings.getPosts()));
        followers.setText(String.valueOf(settings.getFollowers()));
        following.setText(String.valueOf(settings.getFollowing()));
        profileName.setText(settings.getDisplay_name());
        description.setText(settings.getDescription());
        website.setText(settings.getWebsite());

        CircleImageView profileImage = findViewById(R.id.profile_image);
        Picasso.get().load(settings.getProfile_photo().isEmpty() ? null : settings.getProfile_photo())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(profileImage);
        
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


    private void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        final FirebaseMethods firebaseMethods = new FirebaseMethods(this);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadWidgetsWithDatabase(firebaseMethods.getUserAndAccountSettings(dataSnapshot));
                mProgressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
