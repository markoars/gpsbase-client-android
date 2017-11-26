package com.gpsbase.client.gps.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.gpsbase.client.R;

/**
 * Created by Marko on 11/5/2017.
 */

public class SessionActivity extends AppCompatActivity {


    TextView clientNameTxt;
    TextView clientDescriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        clientNameTxt = findViewById(R.id.session_client_name);
        clientDescriptionTxt = findViewById(R.id.session_client_description);

        String clientName = getIntent().getStringExtra("clientName");
        String sessionDescr = getIntent().getStringExtra("sessionDescription");

        clientNameTxt.setText(clientName);
        clientDescriptionTxt.setText(sessionDescr);

        // Set action bar title
        this.setTitle(clientName + " - " + sessionDescr);

        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        // Go back to prev activity
        finish();
        return true;
    }
}
