package com.gpsbase.client.gps.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/// Root of all AppCompatActivities where all general stuff goes
public class RootActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
        {
            currentUserId = user.getUid();
        }




        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {


                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(RootActivity.this, LoginActivity.class));
                    finish();
                }
                else
                {
                }
            }
        };
    }




    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
