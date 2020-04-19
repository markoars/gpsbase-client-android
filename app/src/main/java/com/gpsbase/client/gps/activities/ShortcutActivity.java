/*
 * Copyright 2016 - 2017 Anton Tananaev (anton.tananaev@gmail.com)
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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpsbase.client.MainApplication;
import com.gpsbase.client.gps.fragments.SettingsFragment;
import com.gpsbase.client.gps.models.Position;
import com.gpsbase.client.gps.network.PositionProvider;
import com.gpsbase.client.gps.network.ProtocolFormatter;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.network.RequestManager;
import com.gpsbase.client.gps.services.TrackingService;

public class ShortcutActivity extends RootActivity {

    public static final String EXTRA_ACTION = "action";
    public static final String ACTION_START = "start";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_SOS = "sos";

    private static final String ALARM_SOS = "sos";

    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!executeAction(getIntent())) {
            setContentView(R.layout.list);

            final String[] items = new String[] {
                    getString(R.string.shortcut_start),
                    getString(R.string.shortcut_stop),
                    getString(R.string.shortcut_sos)
            };

            auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();

            ListView listView = findViewById(android.R.id.list);

            listView.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, items));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            setShortcutResult(items[position], R.mipmap.ic_start, ACTION_START);
                            break;
                        case 1:
                            setShortcutResult(items[position], R.mipmap.ic_stop, ACTION_STOP);
                            break;
                        case 2:
                            setShortcutResult(items[position], R.mipmap.ic_sos, ACTION_SOS);
                            break;
                    }
                    finish();
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        executeAction(intent);
    }

    private void setShortcutResult(String label, @DrawableRes int iconResId, String action) {
        Intent intent = new Intent(Intent.ACTION_DEFAULT, null, this, ShortcutActivity.class);
        intent.putExtra(EXTRA_ACTION, action);

        ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(this, action)
                .setShortLabel(label)
                .setIcon(IconCompat.createWithResource(this, iconResId))
                .setIntent(intent)
                .build();

        setResult(RESULT_OK, ShortcutManagerCompat.createShortcutResultIntent(this, shortcut));
    }

    @SuppressWarnings("MissingPermission")
    private void sendAlarm() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (location != null) {
            // (String deviceId, int taskId, String taskUID, String _userUID, String _clientUID, Location location, double battery) {
            Position position = new Position(
                    preferences.getString(SettingsFragment.KEY_DEVICE, null),
                    ((MainApplication) this.getApplicationContext()).getCurrentTrackingTaskId(),
                    ((MainApplication) this.getApplicationContext()).getCurrentTrackingTaskUID(),
                    firebaseUser.getUid(),
                    currentUserId,
                    location,
                    PositionProvider.getBatteryLevel(this));

            String request = ProtocolFormatter.formatRequest(
                    preferences.getString(SettingsFragment.KEY_URL, null), position, ALARM_SOS);

            RequestManager.sendRequestAsync(request, new RequestManager.RequestHandler() {
                @Override
                public void onComplete(boolean success) {
                    if (success) {
                        Toast.makeText(ShortcutActivity.this, R.string.status_send_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ShortcutActivity.this, R.string.status_send_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(this, R.string.status_send_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean executeAction(Intent intent) {
        String action;
        if (intent.hasExtra("shortcutAction")) {
            action = intent.getBooleanExtra("shortcutAction", false)
                    ? ACTION_START : ACTION_STOP;
        } else {
            action = intent.getStringExtra(EXTRA_ACTION);
        }
        if (action != null) {
            switch (action) {
                case ACTION_START:
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit().putBoolean(SettingsFragment.KEY_STATUS, true).apply();
                    ContextCompat.startForegroundService(this, new Intent(this, TrackingService.class));
                    Toast.makeText(this, R.string.status_service_create, Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_STOP:
                    PreferenceManager.getDefaultSharedPreferences(this)
                            .edit().putBoolean(SettingsFragment.KEY_STATUS, false).apply();
                    stopService(new Intent(this, TrackingService.class));
                    Toast.makeText(this, R.string.status_service_destroy, Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_SOS:
                    sendAlarm();
                    break;
            }
            finish();
        }
        return action != null;
    }

}
