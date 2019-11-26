package com.example.mylocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class LocationsAdapter extends ArrayAdapter<Location> {
    ArrayList<Location> Locations;
    public LocationsAdapter(Context context, ArrayList<Location> Locations) {
        super(context, 0, Locations);
        this.Locations = Locations;
    }

    @NonNull
    public View getView(final int position, View viewContect, final ViewGroup parent ) {

        Location location = getItem(position);
        if(viewContect == null) {
            viewContect = LayoutInflater.from(getContext()).inflate(R.layout.mylist, parent, false);
        }
        TextView titleText = (TextView) viewContect.findViewById(R.id.title);
        TextView subtitleText = (TextView) viewContect.findViewById(R.id.subtitle);
        titleText.setText(" Latitude = "+location.getLatitude() +" , " +"Longitude = "+ location.getLongitude());
        subtitleText.setText("location visited" + location.getCreateAt());
        return viewContect;
    }
}
