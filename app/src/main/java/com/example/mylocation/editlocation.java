package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class editlocation extends AppCompatActivity {

    private LocationsDB db;
    Button update;
    EditText Longitude ,Latitude;
    private LocationsAdapter locationsAdapter ;
    ArrayList<Location_> locations;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        locations = new ArrayList<Location_>();
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
            int id = locations.get(0).getId();
            db.updateData(id,Longitude.getText().toString(),Latitude.getText().toString());
            locationsAdapter.notifyDataSetChanged();
            Toast.makeText(editlocation.this, "Data is update", Toast.LENGTH_SHORT).show();
        }
    });
}
    public ArrayList<Location_> getLocations() {
        ArrayList<Location_> locationsList = new ArrayList<Location_>();
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            locationsList.add(new Location_(
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