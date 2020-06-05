package com.example.instaclone.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instaclone.R;
import com.example.instaclone.home.HomeActivity;
import com.example.instaclone.login.LoginActivity;
import com.example.instaclone.model.User;
import com.example.instaclone.model.UserAccountSettings;
import com.example.instaclone.model.UserAndSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private Context context;
    private String userID;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    public FirebaseMethods(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public UserAndSettings getUserAndAccountSettings(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserAndAccountSettings: getting user info from firebase");

        User user = new User();
        UserAccountSettings settings = new UserAccountSettings();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            if (ds.getKey().equals(context.getString(R.string.dbname_user_account_settings))) {
                try {

                    settings.setDescription(ds.child(userID).getValue(UserAccountSettings.class).getDescription());
                    settings.setDisplay_name(ds.child(userID).getValue(UserAccountSettings.class).getDisplay_name());
                    settings.setFollowers(ds.child(userID).getValue(UserAccountSettings.class).getFollowers());
                    settings.setFollowing(ds.child(userID).getValue(UserAccountSettings.class).getFollowing());
                    settings.setPosts(ds.child(userID).getValue(UserAccountSettings.class).getPosts());
                    settings.setProfile_photo(ds.child(userID).getValue(UserAccountSettings.class).getProfile_photo());
                    settings.setUsername(ds.child(userID).getValue(UserAccountSettings.class).getUsername());
                    settings.setWebsite(ds.child(userID).getValue(UserAccountSettings.class).getWebsite());

                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAndAccountSettings: Null pointer Exception" + e.getMessage() );
                }
            }

            if (ds.getKey().equals(context.getString(R.string.dbname_users))) {
                try {
                    user.setUser_id(ds.child(userID).getValue(User.class).getUser_id());
                    user.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    user.setPhone_number(ds.child(userID).getValue(User.class).getPhone_number());
                    user.setUsername(ds.child(userID).getValue(User.class).getUsername());


                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAndAccountSettings: Null pointer Exception" + e.getMessage() );
                }
            }
        }

        return new UserAndSettings(user, settings);

    }

    public void addNewUser(String email, String username, String description, String website, String profile_photo) {

        User user = new User(userID, email, 0, StringManipulation.condenseUsername(username));
        myRef.child(context.getString(R.string.dbname_users)).child(userID).setValue(user);

        UserAccountSettings settings = new UserAccountSettings(description, username, 0, 0, 0, profile_photo, StringManipulation.condenseUsername(username), website);
        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID).setValue(settings);

    }


    public void registerNewEmail(String email, String password, String userName, final ProgressBar progressBar) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            userID = user.getUid();
                            sendVerificationEmail();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });

    }

    public void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(context, "Couldn't send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void loginUser(String email, String password, final ProgressBar progressBar) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success" + mAuth.getCurrentUser());
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            try {
                                if (user.isEmailVerified()) {
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();
                                } else {
                                    Log.e(TAG, "onComplete: Email is not verified");
                                    Toast.makeText(context, "Please verify your email first", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    mAuth.signOut();
                                }
                            } catch (NullPointerException e) {
                                Log.e(TAG, "onComplete: Null pointer exception" + e.getMessage());
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: updating username to " + username);

        myRef.child(context.getString(R.string.dbname_users)).child(userID)
                .child(context.getString(R.string.field_username)).setValue(username);

        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID)
                .child(context.getString(R.string.field_username)).setValue(username);

    }

    public void updateDisplayName(String dispayName) {
        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID)
                .child(context.getString(R.string.field_display_name)).setValue(dispayName);
    }

    public void updateWebsite(String website) {
        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID)
                .child(context.getString(R.string.field_website)).setValue(website);
    }

    public void updateDescription(String bio) {
        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID)
                .child(context.getString(R.string.field_description)).setValue(bio);
    }

    public void updatePhoneNumber(long phoneNumber) {
        myRef.child(context.getString(R.string.dbname_users)).child(userID)
                .child(context.getString(R.string.field_phone_number)).setValue(phoneNumber);
    }

    public void updateEmail(String email) {
        myRef.child(context.getString(R.string.dbname_users)).child(userID)
                .child(context.getString(R.string.field_email)).setValue(email);
    }
}
