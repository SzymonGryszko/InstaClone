package com.example.instaclone.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instaclone.home.HomeActivity;
import com.example.instaclone.login.LoginScreen;
import com.example.instaclone.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private Context context;
    private String userID;

    public FirebaseMethods(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot) {

        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            user.setUsername(ds.getValue(User.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: " + user.getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerNewUser(String email, String password, String userName, final ProgressBar progressBar) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
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
                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
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
