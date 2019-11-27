package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class editlocation extends AppCompatActivity {

    private LocationsDB db;
    Button update;
    EditText Longitude ,Latitude;
    private LocationsAdapter locationsAdapter ;
    ArrayList<Location> locations;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        locations = new ArrayList<Location>();
        setContentView(R.layout.activity_editlocation);
        db = new LocationsDB(this);
        locations = getLocations();
        locationsAdapter = new LocationsAdapter(this,locations);
        selectedId=locations.indexOf(0);
        update = findViewById(R.id.update);
        Longitude = findViewById(R.id.edit1);
        Latitude = findViewById(R.id.edit2);
        UpdateData();
    }
public void UpdateData(){
    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             db.updateData(String.valueOf(selectedId),Longitude.getText().toString(),Latitude.getText().toString());
                locationsAdapter.notifyDataSetChanged();
                Toast.makeText(editlocation.this, "Data is update", Toast.LENGTH_SHORT).show();
        }
    });
}
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locationsList = new ArrayList<Location>();
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            locationsList.add(new Location(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                    , cursor.getDouble(3)
                    , cursor.getDouble(4)
            ));
        }
        return locationsList;
    }
}