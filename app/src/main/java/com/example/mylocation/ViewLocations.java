package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
        db = new LocationsDB(listView.getContext());
        locations = getLocations();
        locationsAdapter = new LocationsAdapter(this,locations);
        listView.setAdapter(locationsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(ViewLocations.this);
                adb.setTitle("Edit location ");
                adb.setMessage("If you want to delete location click delete , or if you want to update location click update");
                final int positionToRemove = position;
                adb.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locations.remove(positionToRemove);
                        locationsAdapter.notifyDataSetChanged();
                    }
                });

                adb.setNegativeButton("Update", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent editlocation =new Intent(getApplicationContext(),editlocation.class);
                        startActivity(editlocation);
                    }
                });

                adb.show();

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
