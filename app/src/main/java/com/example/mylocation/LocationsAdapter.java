package com.example.mylocation;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Calendar;

public class LocationsAdapter extends ArrayAdapter<Location_> {
    ArrayList<Location_> locations;
    public LocationsAdapter(Context context, ArrayList<Location_> locations) {
        super(context, 0, locations);
        this.locations = locations;
    }

    @NonNull
    public View getView(final int position, View viewContect, final ViewGroup parent ) {
        Location_ location = getItem(position);
        if(viewContect == null) {
            viewContect = LayoutInflater.from(getContext()).inflate(R.layout.mylist, parent, false);
        }
        TextView titleText = (TextView) viewContect.findViewById(R.id.title);
        TextView subtitleText = (TextView) viewContect.findViewById(R.id.subtitle);
        titleText.setText(" Latitude = "+location.getLatitude() +" , " +"Longitude = "+ location.getLongitude()+ ","+"Date:"+ Calendar.getInstance().getTime());
        subtitleText.setText("location visited" + location.getCreateAt());
        return viewContect;
    }
}
