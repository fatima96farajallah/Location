package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements LocationListener {
    public static double Longi;
    public static double Lati;
    private LocationsDB db;
    TextView Latitude;
    TextView Longitude;
    ListView listView;

    private   Button savelocation,showlocation;
    LocationManager locationManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db =new LocationsDB(this);
        setContentView(R.layout.activity_main);
        savelocation =findViewById(R.id.savelocation);
        showlocation =findViewById(R.id.showlocation);
        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viwelocation = new Intent(getApplicationContext(),ViewLocations.class);
                startActivity(viwelocation);
            }
        });
        CheckPermission();
    }
    public void onResume() {
        super.onResume();
        getLocation();
    }
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION} , 101);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Longitude =(TextView) findViewById(R.id.Longitude);
        Latitude = (TextView) findViewById(R.id.Latitude);
        Longi=location.getLongitude();
        Lati=location.getLatitude();
        Longitude.setText("Longitude : " + Longi);
        Latitude.setText("Latitude : " + Lati);
        savelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertData(Longi,Lati);
                Toast.makeText(MainActivity.this, "Save Done", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }
}
