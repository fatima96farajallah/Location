package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements LocationListener {
    public static double Longi;
    public static double Lati;
    private LocationsDB db;
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
        Longi=location.getLongitude();
        Lati=location.getLatitude();
        savelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertData(Longi,Lati);
                Toast.makeText(MainActivity.this, "Location is saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<com.example.mylocation.Location> getLocations() {
        com.example.mylocation.Location loc;
        ArrayList<com.example.mylocation.Location> locationsList = new ArrayList<com.example.mylocation.Location>();
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            locationsList.add(new com.example.mylocation.Location(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                    , cursor.getDouble(3)
                    , cursor.getDouble(4)
            ));
        }
        return locationsList;
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
    private void showNotification(String title,String content,String discrption) {

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendinggIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(discrption,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("nothing");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(notificationChannel);


        }
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this,discrption);
        noBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.userlocation)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendinggIntent);
        notificationManager.notify(new Random().nextInt(),noBuilder.build());

    }
}
