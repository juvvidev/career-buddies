package com.juvvi.careerbuddies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            startLogin(true);
        } else {
            startLogin(false);
        }
    }

    private void startLogin(Boolean b) {
        Intent intent;
        if (b) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
        finish();
    }
}