package com.informatiquemobile.tracking;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double latitudeend =1;
    private double longitudeend = -70;
    private String mod_loc = "GPS";     //mod_loc TEXT
    private double latitude;          //lat_gps REAL
    private double longitude;       //long_gps REAL
    private double alt_gps;             //alt_gps REAL
    private long lm ;                 //lm REAL
    private double dir_dep;             //dir_dep REAL
    private double drp;                 //drp REAL
    private double vm;                  //vm REAL
    private double dt;                  //dt REAL
    private String add_photo;           //add_photo TEST
    private double niv_bat;             //niv_bat REAL
    private String ap_wifi_ssid;        //ap_wifi_ssid TEXT
    private String ap_wifi_bssid;       //ap_wifi_bssid TEXT
    private int ap_wifi_rss;         //ap_wifi_rss INTEGER
    private static final int REQUEST_CAMERA = 1111;
    private int numfrup;
    private LocationManager locationManager;
    private  boolean act_tracking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        numfrup = (getIntent().getIntExtra("numfrup", RESULT_OK));
        latitude = (getIntent().getDoubleExtra("latitude", RESULT_OK));
        longitude = (getIntent().getDoubleExtra("longitude", RESULT_OK));
//        Toast.makeText(getApplicationContext(), "num" + numfrup +latitude +longitude, Toast.LENGTH_LONG).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, numfrup, 10, this);
        timer();
        setUpMapIfNeeded();
        act_tracking = true;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment
                .getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CAMERA);
//        Toast.makeText(getApplicationContext(), "Take a Photo", Toast.LENGTH_LONG).show();
    }

    private void timer() {
        Toast.makeText(getApplicationContext(), "Take a Photo", Toast.LENGTH_LONG).show();
        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
//                Toast.makeText(getApplicationContext(), "End Time", Toast.LENGTH_LONG).show();
                takePhoto();
            }
        }.start();
    }

    private void setBattery() {
        registerReceiver(mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));

    }

    //Create Broadcast Receiver Object along with class definition
        private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

            @Override
            //When Event is published, onReceive method is called
           public void onReceive(Context c, Intent i) {

                //Get Battery %
                niv_bat = i.getIntExtra("level", 0);
//                Toast.makeText(getApplicationContext(), "Battery" + niv_bat, Toast.LENGTH_LONG).show();


            }
       };
       /*
        private void setWifi() {
            WifiManager wifi = null;
            WifiInfo info = wifi.getConnectionInfo();
            if (info.getBSSID()!= null){
                ap_wifi_ssid=info.getSSID();
                ap_wifi_bssid = info.getBSSID();
                ap_wifi_rss = WifiManager.calculateSignalLevel(info.getRssi(), 5);
            }

        }
    */
    private void WriterDB() {
        DataBaseHelper helper = new DataBaseHelper(this);
        Toast.makeText(getApplicationContext(), "created Data Base", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = helper.getWritableDatabase();

        if(db != null)
        {
            ContentValues registro = new ContentValues();  //clase pour garde des information
            registro.put("mod_loc", mod_loc);
            registro.put("lat_gps", latitude);
            registro.put("long_gps", longitude);
            registro.put("alt_gps", alt_gps);
            registro.put("lm", lm);
            registro.put("dir_dep", dir_dep);
            registro.put("drp", drp);
            registro.put("vm", vm);
            registro.put("dt", dt);
            registro.put("add_photo", add_photo);
            registro.put("niv_bat", niv_bat);
            registro.put("ap_wifi_ssid", ap_wifi_ssid);
            registro.put("ap_wifi_bssid", ap_wifi_bssid);
            registro.put("ap_wifi_rss", ap_wifi_rss);
            db.insert("tracking1", null, registro);
            db.close();
            Toast.makeText(this, "Written database", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
          //      timer();

//       setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true); // true to enable

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

            // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(3));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here" + latitude +","+longitude));
  }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        alt_gps =location.getAccuracy();
/*        if (latitude!=latitudeend && longitude!=longitudeend) {
            timer();
        }else{
            Toast.makeText(getApplicationContext(), "Finish Road ", Toast.LENGTH_LONG).show();
        }*/
//        setUpMapIfNeeded();
        //      setWifi();
        setUpMap();
        setBattery();
        timer();
        WriterDB();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to stop your Tracking?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MapsActivity.this, TrackingMode.class);
                        intent.putExtra("vsIA", false);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    public void stopTracking (View View){
        onBackPressed();
    }
}

