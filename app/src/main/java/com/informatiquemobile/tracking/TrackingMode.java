package com.informatiquemobile.tracking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

    public void googleMapsGPS(View View) {

        EditText numberfrecucy = (EditText) findViewById(R.id.freUpdNumber);
        if (numberfrecucy.getText().length() == 0) {
            //mesaage for selection frecuency update
            Toast.makeText(getApplicationContext(), "Enter a frequency of updating of position, Please", Toast.LENGTH_LONG).show();

        } else {
            int numfrup = Integer.parseInt(numberfrecucy.getText().toString());

            boolean gpsAct;
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gpsAct = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //if GPS is activite
            if (gpsAct) {
                Intent intent = new Intent(TrackingMode.this, ModeGps.class);
                intent.putExtra("vsIA", false);
                intent.putExtra("frecuencyupdate",numfrup);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Active your GPS, Please", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void googleMapsGSM(View View) {

        EditText numberfrecucy = (EditText) findViewById(R.id.freUpdNumber);
        if (numberfrecucy.getText().length() == 0) {
            //mesaage for selection frecuency update
            Toast.makeText(getApplicationContext(), "Enter a frequency of updating of position, Please", Toast.LENGTH_LONG).show();

        } else {
            int numfrup = Integer.parseInt(numberfrecucy.getText().toString());
            double latitudewifi = 2;
            double longitudewifi = -72;
            Intent intent = new Intent(TrackingMode.this, MapsActivityCellular.class);
            intent.putExtra("vsIA", false);
            intent.putExtra("latitudemap", latitudewifi);
            intent.putExtra("longitudemap", longitudewifi);
            startActivity(intent);
        }

    }
    public  void exitClick(){
        new AlertDialog.Builder(this)
                .setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TrackingMode.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
