package com.gpsbase.client.gps.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.TwoStatePreference;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gpsbase.client.MainApplication;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.Position;
import com.gpsbase.client.gps.services.TrackingService;
import com.gpsbase.client.gps.utils.DatabaseHelper;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_ANGLE;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_DEVICE;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_DISTANCE;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_INTERVAL;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_PROVIDER;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_STATUS;
import static com.gpsbase.client.gps.fragments.SettingsFragment.KEY_URL;

/**
 * Created by Marko on 11/5/2017.
 */

public class SessionActivity extends AppCompatActivity {

    private TextView sessionIdTxt;
    private TextView sessionDescriptionTxt;
    private MapView map;
    private ArrayList<GeoPoint> points;
    private DatabaseHelper databaseHelper;
    private BroadcastReceiver brLocationUpdates;

    ToggleButton startTrackingToggle;

    private String sessionIdString;
    private String sessionDescr;
    private int sessionId;


    private static final int PERMISSIONS_REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_session);

        sessionIdString = Integer.toString(getIntent().getIntExtra("sessionId", 0));
        sessionDescr = getIntent().getStringExtra("sessionDescription");
        sessionId = getIntent().getIntExtra("sessionId", 0);

        points = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        sessionIdTxt = findViewById(R.id.activitySession_session_id);
        sessionDescriptionTxt = findViewById(R.id.activitySession_session_description);
        sessionIdTxt.setText(sessionIdString);
        sessionDescriptionTxt.setText(sessionDescr);
        this.setTitle(sessionIdString + " - " + sessionDescr); // Set action bar title

        map = findViewById(R.id.map_osm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        /// Set initial map point
        IMapController mapController = map.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(41.98568496632937, 21.4279956); // Skopje
        mapController.setCenter(startPoint);




        startTrackingToggle = findViewById(R.id.buttonOnOff);
        startTrackingToggle.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {

                if(isChecked) {
                    // start tracking service
                    ((MainApplication) SessionActivity.this.getApplication()).setSelectedSession(sessionId);
                }
                else {
                    // stop tracking service
                }
            }
        }) ;



        redrawPolyLines();

        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        // Go back to prev activity
        finish();
        return true;
    }


    @Override
    protected void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        if(brLocationUpdates == null){
            brLocationUpdates = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    double longitude =  intent.getDoubleExtra("longitude", 0);
                    double latitude =  intent.getDoubleExtra("latitude", 0);

                    GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                    points.add(geoPoint);

                    redrawPolyLines();
                }
            };
        }
        registerReceiver(brLocationUpdates,new IntentFilter("com.gpsbase.client_location_update"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(brLocationUpdates != null){
            unregisterReceiver(brLocationUpdates);
        }
    }


    private void redrawPolyLines(){

        List<Position> positions = databaseHelper.getLocationsForSession(sessionId);

        if(positions != null) {
            points = new ArrayList<>(); // clear list

            List<GeoPoint> geoPoints = new ArrayList<>();
            map.getOverlayManager().clear(); // remove previous polyLines
            GeoPoint currentPoint = new GeoPoint(0, 0);

            for (int i = 0; i < positions.size(); i++) {
                // TODO: Create a helper class/method to convert Position to LatLon object
                Position position = positions.get(i);
                GeoPoint geoPoint = position.getGeoPoint();

                points.add(geoPoint);
                geoPoints.add(geoPoint);

                currentPoint = geoPoint;
            }

            //add your points here
            Polyline line = new Polyline();
            line.setPoints(geoPoints);
            map.getOverlayManager().add(line);
            map.getController().animateTo(currentPoint);

            addSimpleMarker(currentPoint);
        }
    }



    private void addSimpleMarker(GeoPoint point) {
        //OverlayItem myLocationOverlayItem = new OverlayItem("Here", "Current Position", point);
        //Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.person);
        //myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
    }
}
