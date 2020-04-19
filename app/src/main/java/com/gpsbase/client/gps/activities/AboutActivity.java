/*
 * Copyright 2012 - 2017 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gpsbase.client.gps.activities;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.gpsbase.client.R;

public class AboutActivity extends RootActivity {

    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView title = findViewById(R.id.title);
        signOut = findViewById(R.id.sign_out);

        try {
            title.setText(title.getText() + " " + getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            Log.w(AboutActivity.class.getSimpleName(), e);
        }


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        // Go back to prev activity
        finish();
        return true;
    }

    //sign out method
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
       // startActivity(new Intent(AboutActivity.this, LoginActivity.class));
       // finish();
    }
}
