package com.example1.simon.wouldyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements InfoWindowAdapter {

    private final View mWindow;
    private final Context mContext;

    public CustomInfoWindowAdapter(Context context) {

        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.map_event_info_window, null);
    }

    private void renderWindowText (Marker marker, View view) {

        String title = marker.getTitle();
        TextView tvTitle = view.findViewById(R.id.title);

        if (!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = view.findViewById(R.id.snippet);

        if (!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {

        renderWindowText(marker, mWindow);

        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {

        renderWindowText(marker, mWindow);

        return mWindow;
    }
}
