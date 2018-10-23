package com.gpsbase.client.gps.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gpsbase.client.MainApplication;
import com.gpsbase.client.R;
import com.gpsbase.client.gps.models.Position;
import com.gpsbase.client.gps.utils.DatabaseHelper;
import com.gpsbase.client.gps.utils.TrackingServiceUtil;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

import static com.gpsbase.client.gps.utils.DatabaseHelper.POSITIONS_TABLE;


/**
 * Created by Marko on 11/5/2017.
 */

public class TaskActivity extends AppCompatActivity {

    private TextView taskIdTxt;
    private TextView taskDescriptionTxt;
    private TextView txtSatelliteCount;
    private TextView txtAccuracy;
    private TextView txtDistance;
    private TextView txtPoints;
    private TextView txtDuration;
    private TextView txtSpeed;


    private MapView map;
    private ArrayList<GeoPoint> points;
    private DatabaseHelper databaseHelper;
    private BroadcastReceiver brLocationUpdates;

    private ToggleButton tbtnStartTrackingToggle;
    private Button btnDeleteRecords;

    private String taskIdString;
    private String taskDescr;
    private int taskId;
    private int currentTrackingTaskId;
    private int clientId;

    private static final int PERMISSIONS_REQUEST_LOCATION = 2;
    public static final String KEY_STATUS = "status";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_task);

        taskIdString = Integer.toString(getIntent().getIntExtra("taskId", 0));
        taskDescr = getIntent().getStringExtra("taskDescription");
        taskId = getIntent().getIntExtra("taskId", 0);
        currentTrackingTaskId = ((MainApplication) TaskActivity.this.getApplication()).getCurrentTrackingTaskId();
        clientId = getIntent().getIntExtra("clientId", 0);

        points = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        taskIdTxt = findViewById(R.id.activityTask_task_id);
        taskDescriptionTxt = findViewById(R.id.activityTask_task_description);
        taskIdTxt.setText(taskIdString);
        taskDescriptionTxt.setText(taskDescr);
        this.setTitle(taskIdString + " - " + taskDescr); // Set action bar title

        txtSatelliteCount = findViewById(R.id.activity_task_txtSatelliteCount);
        txtAccuracy = findViewById(R.id.activity_task_txtAccuracy);
        txtDistance = findViewById(R.id.activity_task_txtDistance);
        txtPoints = findViewById(R.id.activity_task_txtPoints);
        txtDuration = findViewById(R.id.activity_task_txtDuration);
        txtSpeed = findViewById(R.id.activity_task_txtSpeed);



        map = findViewById(R.id.map_osm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        /// Set initial map point
        IMapController mapController = map.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(41.98568496632937, 21.4279956); // Skopje
        mapController.setCenter(startPoint);



        tbtnStartTrackingToggle = findViewById(R.id.buttonOnOff);
        btnDeleteRecords = findViewById(R.id.buttonDeleteRecords);


        if(currentTrackingTaskId == taskId) {
            tbtnStartTrackingToggle.setChecked(true);
            tbtnStartTrackingToggle.setBackgroundColor(getResources().getColor(R.color.primary));
        }
        else {
            tbtnStartTrackingToggle.setBackgroundColor(getResources().getColor(R.color.blue));
        }

        tbtnStartTrackingToggle.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {

                TrackingServiceUtil trackingUtil = new TrackingServiceUtil(TaskActivity.this);

                if(isChecked) {
                    // start tracking service
                    if(currentTrackingTaskId == 0) {

                        ((MainApplication) TaskActivity.this.getApplication()).setCurrentTrackingTaskId(taskId);
                        boolean trackingStatus = trackingUtil.startTrackingService(true, true);
                        toggleButton.setChecked(trackingStatus);
                        toggleButton.setBackgroundColor(getResources().getColor(R.color.primary));
                    }
                    else {
                        // Tracking is ON, so just change the current selected task Id
                        ((MainApplication) TaskActivity.this.getApplication()).setCurrentTrackingTaskId(taskId);
                    }
                }
                else {
                    // stop tracking service
                    ((MainApplication) TaskActivity.this.getApplication()).setCurrentTrackingTaskId(0); // set none is selected
                    trackingUtil.stopTrackingService();
                    toggleButton.setBackgroundColor(getResources().getColor(R.color.blue));
                }
            }
        }) ;


        btnDeleteRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*databaseHelper.deletePositionAsync(POSITIONS_TABLE, taskId, new DatabaseHelper.DatabaseHandler<Void>() {
                    @Override
                    public void onComplete(boolean success, Void result) {
                        if (success) {
                        } else {
                        }
                    }
                });
                map.getOverlayManager().clear();*/
            }
        });




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

        List<Position> positions = databaseHelper.getLocationsByTaskId(POSITIONS_TABLE, taskId);

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
