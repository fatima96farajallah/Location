package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewLocations extends AppCompatActivity {
    private LocationsDB db;
    private LocationsAdapter locationsAdapter ;
    ArrayList<Location_> locations;
    ListView listView;
    private int SelectedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviwe);
        listView = (ListView)findViewById(R.id.list);
        locations = new ArrayList<Location_>();
        db = new LocationsDB(listView.getContext());
        locations = getLocations();
        SelectedID=locations.indexOf(0);
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
                        db.deleteLocation(locations.get(position).getId());
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
