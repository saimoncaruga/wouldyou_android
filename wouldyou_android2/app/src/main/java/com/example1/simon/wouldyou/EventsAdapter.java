package com.example1.simon.wouldyou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example1.simon.wouldyou.Models.Event;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventsAdapter";

    private Context mContext;
    private List<Event> eventsList= new ArrayList<>();

    public EventsAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Event> list) {
        super(context, 0, list);
        mContext = context;
        eventsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_events, parent, false);
        }

        Event currentEvent = eventsList.get(position);

        TextView eventName = listItem.findViewById(R.id.event_name);
        eventName.setText(currentEvent.getName());

        TextView eventDesc = listItem.findViewById(R.id.event_description);
        eventDesc.setText(currentEvent.getDescription());

        TextView eventAddress = listItem.findViewById(R.id.event_address);
        //LatLng address = new LatLng(currentEvent.getLat(), currentEvent.getLon());
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());;
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(currentEvent.getLat(), currentEvent.getLon(), 1);
        } catch (IOException e) {
            e.printStackTrace();

            Log.d(TAG, "getView: there's a problem in getting the addresses:" + e);
        }

        String address = addresses.get(0).getAddressLine(0);

        eventAddress.setText(address);

        TextView event_start = listItem.findViewById(R.id.event_time_start);
        event_start.setText(currentEvent.getTime_start_event());

        TextView event_end = listItem.findViewById(R.id.event_time_end);
        event_end.setText(currentEvent.getTime_end_event());

        return listItem;
    }
}
