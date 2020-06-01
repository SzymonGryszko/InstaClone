package com.example.instaclone.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instaclone.R;
import com.example.instaclone.home.HomeActivity;
import com.example.instaclone.utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = "LoginScreen";

    private EditText mEmial, mPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        context = LoginScreen.this;

        mEmial = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mProgressBar = findViewById(R.id.login_progressbar);

        mProgressBar.setVisibility(View.GONE);
        initFirebaseLogin();

        intiRegisterNewUser();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void intiRegisterNewUser() {
        TextView linkSignup = findViewById(R.id.link_signup);
        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initFirebaseLogin() {
        Button mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmial.getText().toString().toLowerCase().trim();
                String password = mPassword.getText().toString().trim();
                mProgressBar.setVisibility(View.VISIBLE);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginScreen.this, "Please populate all fields", Toast.LENGTH_SHORT).show();
                } else {
                    new FirebaseMethods(context).loginUser(email, password, mProgressBar);
                }
            }
        });
    }

}
