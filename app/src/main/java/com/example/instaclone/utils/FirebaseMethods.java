package com.example.instaclone.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instaclone.R;
import com.example.instaclone.home.HomeActivity;
import com.example.instaclone.model.User;
import com.example.instaclone.model.UserAccountSettings;
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

    public void addNewUser(String email, String username, String description, String website, String profile_photo) {

        User user = new User(userID, email, 0, StringManipulation.condenseUsername(username));
        myRef.child(context.getString(R.string.dbname_users)).child(userID).setValue(user);

        UserAccountSettings settings = new UserAccountSettings(description, username, 0, 0, 0, profile_photo, username, website);
        myRef.child(context.getString(R.string.dbname_user_account_settings)).child(userID).setValue(settings);

    }


    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot) {

        User user = new User();

        for (DataSnapshot ds : dataSnapshot.child(userID).getChildren()) {
            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: " + user.getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                return true;
            }
        }
        return false;
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

}
