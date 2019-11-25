package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewLocations extends AppCompatActivity {

    private LocationsDB db;
    private LocationsAdapter locationsAdapter ;
    ArrayList<Location> locations;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviwe);
        listView = (ListView)findViewById(R.id.list);
        locations = new ArrayList<Location>();

        db = new LocationsDB(this);
        locations = getLocations();
         locationsAdapter = new LocationsAdapter(this,locations);
        listView.setAdapter(locationsAdapter);
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
