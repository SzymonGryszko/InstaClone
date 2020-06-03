package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instaclone.R;
import com.example.instaclone.model.User;
import com.example.instaclone.model.UserAccountSettings;
import com.example.instaclone.model.UserAndSettings;
import com.example.instaclone.utils.FirebaseMethods;
import com.example.instaclone.utils.SquareImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ImageView backArrow;
    private EditText fullName, username, website, bio, email, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initWidgets();
        initFirebase();

    }

    private void initWidgets() {
        backArrow = findViewById(R.id.edit_profile_backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fullName = findViewById(R.id.edit_profile_name);
        username = findViewById(R.id.edit_profile_username);
        website = findViewById(R.id.edit_profile_website);
        bio = findViewById(R.id.edit_profile_bio);
        email = findViewById(R.id.edit_profile_email);
        phoneNumber = findViewById(R.id.edit_profile_phonenumber);

    }

    public void loadWidgetsWithDatabse(UserAndSettings userAndSettings) {
        User user = userAndSettings.getUser();
        UserAccountSettings settings = userAndSettings.getSettings();

        fullName.setText(settings.getDisplay_name());
        username.setText(settings.getUsername());
        website.setText(settings.getWebsite());
        bio.setText(settings.getDescription());
        email.setText(user.getEmail());
        phoneNumber.setText(String.valueOf(user.getPhone_number()));

        CircleImageView profileImage = findViewById(R.id.profile_photo);
        Picasso.get().load(settings.getProfile_photo().isEmpty() ? null : settings.getProfile_photo())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(profileImage);

    }


    public void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        final FirebaseMethods firebaseMethods = new FirebaseMethods(this);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadWidgetsWithDatabse(firebaseMethods.getUserAndAccountSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
