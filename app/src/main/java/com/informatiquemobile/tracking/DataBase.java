package com.informatiquemobile.tracking;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class DataBase extends ActionBarActivity {
    private String [] tracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        tracking = new String[]{"Tracking #1", "Tracking #2", "Tracking #3"};
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tracking);
        ListView lv = (ListView) findViewById(R.id.lastTracking);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (tracking[position]){
                    case "Tracking #1":
                        Intent intent= new Intent(DataBase.this, ReadDb.class);
                        intent.putExtra("vsIA", false);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"tracking numero: " + tracking[position], Toast.LENGTH_SHORT).show();
                    case "Tracking #2":
                        Toast.makeText(getApplicationContext(),"tracking numero: " + tracking[position], Toast.LENGTH_SHORT).show();
                    case "Tracking #3":
                        Toast.makeText(getApplicationContext(),"tracking numero: " + tracking[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
 /*       //create data base
        DataBaseHelper helper = new DataBaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_base, menu);
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
}
