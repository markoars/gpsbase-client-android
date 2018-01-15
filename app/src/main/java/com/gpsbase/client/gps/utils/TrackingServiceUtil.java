package com.gpsbase.client.gps.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.support.v4.content.ContextCompat;

import com.gpsbase.client.gps.fragments.SettingsFragment;
import com.gpsbase.client.gps.receivers.AutostartReceiver;
import com.gpsbase.client.gps.services.TrackingService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MARS on 13-Jan-18.
 */
/// Used for global access to the starting/stopping the tracking service
public class TrackingServiceUtil {

    private static final int ALARM_MANAGER_INTERVAL = 15000;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private SharedPreferences sharedPreferences;

    Activity senderActivity;

    public TrackingServiceUtil(Activity _senderActivity)
    {
        senderActivity = _senderActivity;
        alarmManager = (AlarmManager) senderActivity.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = PendingIntent.getBroadcast(senderActivity, 0, new Intent(senderActivity, AutostartReceiver.class), 0);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(senderActivity);
    }


    public boolean startTrackingService(boolean checkPermission, boolean permission) {
        if (checkPermission) {
            Set<String> missingPermissions = new HashSet<>();
            if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                missingPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                missingPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (missingPermissions.isEmpty()) {
                permission = true;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    senderActivity.requestPermissions(missingPermissions.toArray(new String[missingPermissions.size()]),
                            SettingsFragment.PERMISSIONS_REQUEST_LOCATION);
                }
                //return;
            }
        }

        if (permission) {
            ContextCompat.startForegroundService(senderActivity, new Intent(senderActivity, TrackingService.class));
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                            ALARM_MANAGER_INTERVAL,
                                            ALARM_MANAGER_INTERVAL,
                                            alarmIntent);

            this.setKeyStatusValue(true);

            return true;
        } else {
            this.setKeyStatusValue(false);

            return false;
        }
    }



    public void stopTrackingService() {
        alarmManager.cancel(alarmIntent);
        senderActivity.stopService(new Intent(senderActivity, TrackingService.class));

        this.setKeyStatusValue(false);
    }


    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return true;
        }

        return ContextCompat.checkSelfPermission(senderActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void setKeyStatusValue(Boolean _keyStatus)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SettingsFragment.KEY_STATUS, _keyStatus);
        editor.apply();
    }
}
