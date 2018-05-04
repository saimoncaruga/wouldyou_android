package com.example1.simon.wouldyou;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);
        /*SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Log.d("mappa", mapFragment + "");
        mapFragment.getMapAsync(this);*/

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        mapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit();

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng manera = new LatLng(45.6739885, 9.0361521);
        googleMap.addMarker(new MarkerOptions().position(manera)
                .title("Marker in Manera"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(manera));
    }
}


