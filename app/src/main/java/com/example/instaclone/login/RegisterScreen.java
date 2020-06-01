package com.example.instaclone.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instaclone.R;
import com.example.instaclone.utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterScreen extends AppCompatActivity {

    private static final String TAG = "RegisterScreen";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText mUserName, mEmail, mPassword;
    private ProgressBar mProgressBar;
    private Context context;

    private String email, username, password;
    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        context = RegisterScreen.this;
        final FirebaseMethods firebaseMethods = new FirebaseMethods(context);

        mUserName = findViewById(R.id.input_fullname);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mProgressBar = findViewById(R.id.register_progressbar);
        mProgressBar.setVisibility(View.GONE);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged: user is signed in" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (firebaseMethods.checkIfUsernameExists(username, dataSnapshot)) {
                                append = myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username exists, appending random string " + append);
                                username = username + append;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else {
                    Log.d(TAG, "onAuthStateChanged: user is signed out");
                }
            }
        };

        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterScreen.this, "Please populate all fields", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewUser(email, password, username, mProgressBar);
                }

            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
