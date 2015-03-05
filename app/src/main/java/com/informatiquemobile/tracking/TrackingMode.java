package com.informatiquemobile.tracking;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;


public class TrackingMode extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracking_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void googleMapsGPS (View View) {

        //location GPS
        final double latitudegps;
        double longitudegps;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Location gpsstart = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (gpsstart != null) {
            latitudegps = gpsstart.getLatitude();
            longitudegps = gpsstart.getLongitude();
        } else {
            latitudegps = 0;
            longitudegps = 0;
        }
        LocationListener loclistener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double latitudegps = gpsstart.getLatitude();
                double longitudegps = gpsstart.getLongitude();
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
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 10, loclistener);
/*
        // lancer map

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude of the current location
        double latitudegps = myLocation.getLatitude();

        // Get longitude of the current location
        double longitudegps = myLocation.getLongitude();
*/

    Intent intent = new Intent(TrackingMode.this, MapsActivity.class);
        intent.putExtra("vsIA", false);
        intent.putExtra("latitudemap",latitudegps);
        intent.putExtra("longitudemap",longitudegps);
        startActivity(intent);
    }
    public void googleMapsWIFI (View View) {
        double latitudewifi = 0;
        double longitudewifi = 0;
        Intent intent = new Intent(TrackingMode.this, MapsActivity.class);
        intent.putExtra("vsIA", false);
        intent.putExtra("latitudemap",latitudewifi);
        intent.putExtra("longitudemap",longitudewifi);

        startActivity(intent);
    }
    public void googleMapsGSM (View View) {
        double latitudewifi = 2;
        double longitudewifi = -72;
        Intent intent = new Intent(TrackingMode.this, MapsActivity.class);
        intent.putExtra("vsIA", false);
        intent.putExtra("latitudemap",latitudewifi);
        intent.putExtra("longitudemap",longitudewifi);
        startActivity(intent);
    }

}
