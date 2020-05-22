package com.example.instaclone.profile;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.instaclone.R;
import com.example.instaclone.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView backArrow;
    private ImageView mProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        backArrow = findViewById(R.id.edit_profile_backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProfileImage = findViewById(R.id.profile_photo);
        setProfileImage();

    }


    private void setProfileImage() {

        String imageUrl = "instagram.fpoz2-1.fna.fbcdn.net/v/t51.2885-19/11887042_660706700696553_1149097263_a.jpg?_nc_ht=instagram.fpoz2-1.fna.fbcdn.net&_nc_ohc=oevHfjXIKYEAX8RzPpK&oh=24d925e868be408112edc74f8f33d50b&oe=5EF08EAA";

        UniversalImageLoader.setImage(imageUrl, mProfileImage, null, "https://");

    }
}
