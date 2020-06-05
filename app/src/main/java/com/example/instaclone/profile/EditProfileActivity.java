package com.example.instaclone.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instaclone.R;
import com.example.instaclone.dialogs.ConfirmPasswordDialog;
import com.example.instaclone.model.User;
import com.example.instaclone.model.UserAccountSettings;
import com.example.instaclone.model.UserAndSettings;
import com.example.instaclone.utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements ConfirmPasswordDialog.OnPasswordConfirmListener {

    private static final String TAG = "EditProfileActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseMethods firebaseMethods;

    private UserAndSettings mUserAndSettings;

    private ImageView backArrow, saveChanges;
    private EditText mDisplayName, mUsername, mWebsite, mBio, mEmail, mPhoneNumber;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setupFirebaseAuth();
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

        saveChanges = findViewById(R.id.edit_profile_save);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving changes");
                saveChanges.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                saveProfileSettings();
                progressBar.setVisibility(View.GONE);
                saveChanges.setVisibility(View.VISIBLE);
            }
        });

        progressBar = findViewById(R.id.edit_profile_progressbar);
        progressBar.setVisibility(View.GONE);
        mDisplayName = findViewById(R.id.edit_profile_name);
        mUsername = findViewById(R.id.edit_profile_username);
        mWebsite = findViewById(R.id.edit_profile_website);
        mBio = findViewById(R.id.edit_profile_bio);
        mEmail = findViewById(R.id.edit_profile_email);
        mPhoneNumber = findViewById(R.id.edit_profile_phonenumber);

    }

    public void loadWidgetsWithDatabse(UserAndSettings userAndSettings) {
        User user = userAndSettings.getUser();
        UserAccountSettings settings = userAndSettings.getSettings();

        mUserAndSettings = userAndSettings;

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mBio.setText(settings.getDescription());
        mEmail.setText(user.getEmail());
        mPhoneNumber.setText(String.valueOf(user.getPhone_number()));

        CircleImageView profileImage = findViewById(R.id.profile_photo);
        Picasso.get().load(settings.getProfile_photo().isEmpty() ? null : settings.getProfile_photo())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(profileImage);

    }

    public void saveProfileSettings() {
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String bio = mBio.getText().toString();
        final String email = mEmail.getText().toString();
        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());

        if (!mUserAndSettings.getUser().getUsername().equals(username)) {
            checkIfUsernameExists(username);
        }
        if (!mUserAndSettings.getUser().getEmail().equals(email)) {
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getSupportFragmentManager(), getString(R.string.confirm_dialog_password));
        }

        if (!mUserAndSettings.getSettings().getDisplay_name().equals(displayName)) {
            firebaseMethods.updateDisplayName(displayName);
        }

        if (!mUserAndSettings.getSettings().getDescription().equals(bio)) {
            firebaseMethods.updateDescription(bio);
        }

        if (!mUserAndSettings.getSettings().getWebsite().equals(website)) {
            firebaseMethods.updateWebsite(website);
        }

        if (mUserAndSettings.getUser().getPhone_number() != phoneNumber) {
            firebaseMethods.updatePhoneNumber(phoneNumber);
        }

        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();

    }

    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: " + username);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {

                    firebaseMethods.updateUsername(username);

                } else {
                    Toast.makeText(EditProfileActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onPasswordConfirm(String password) {
        Log.d(TAG, "onPasswordConfirm: password: " + password);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");

                        mAuth.fetchSignInMethodsForEmail(mEmail.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                                        try {
                                            if (isNewUser) {
                                                Log.e("TAG", "emial is not in use");

                                                mAuth.getInstance().getCurrentUser().updateEmail(mEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(EditProfileActivity.this, "Email updated", Toast.LENGTH_SHORT).show();
                                                                    firebaseMethods.updateEmail(mEmail.getText().toString());
                                                                }
                                                            }
                                                        });


                                            } else {
                                                Log.e("TAG", "emial alredy used");
                                                Toast.makeText(EditProfileActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (NullPointerException e) {
                                            e.getMessage();
                                        }


                                    }
                                });

                    }
                });
    }


    public void initFirebase() {

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

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        firebaseMethods = new FirebaseMethods(this);
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user information from the database
                loadWidgetsWithDatabse(firebaseMethods.getUserAndAccountSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
