package com.example1.simon.wouldyou;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example1.simon.wouldyou.Models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.util.WorkSourceUtil.TAG;

public class SearchFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SearchFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float cameraDefaultZoom = 16f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    // Widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    private ImageView mInfo;

    // Variables
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //PARTE CORRETTA
        /*android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        mapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit();
        mapFragment.getMapAsync(this);

        return rootView;*/

        getLocationPermissions();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSearchText = getView().findViewById(R.id.input_search);
        mGps = getView().findViewById(R.id.icon_gps);
        mInfo = getView().findViewById(R.id.place_info);
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");

        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        mapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");

        mMap = googleMap;

        LatLng manera = new LatLng(45.6739885, 9.0361521);
        mMap.addMarker(new MarkerOptions().position(manera)
                .title("Marker in Manera"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(manera));

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);   // disable or not the current location button

            init();
        }
    }

    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    Log.d(TAG, "init: onEditorAction clicked");

                        // Execute the method for searching
                        geoLocate();

                        hideKeyboard();
                    }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked GPS icon");

                getDeviceLocation();
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked place info");

                try {
                    if (mMarker.isInfoWindowShown()) {
                        mMarker.hideInfoWindow();
                    } else {
                        Log.d(TAG, "onClick: place info: " + mPlace.toString());

                        mMarker.showInfoWindow();
                    }

                } catch (NullPointerException e) {
                    Log.d(TAG, "onClick: NullPointerException: " + e.getMessage());
                }
            }
        });
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(getActivity());

        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.d(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), cameraDefaultZoom, address.getAddressLine(0));
        }
    }


    private void getLocationPermissions() {
        Log.d(TAG, "getLocationPermissions: getting location permissions");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                Log.d(TAG, "getLocationPermissions: first else statement");
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            Log.d(TAG, "getLocationPermissions: second else statement");
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");

        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permissions failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permissions granted");
                    mLocationPermissionsGranted = true;

                    //initialize map
                    initMap();
                }
            }
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the current device location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");

                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), cameraDefaultZoom, "My Location");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(), "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security Exception: " + e.getMessage());
        }
    }

    private void moveCamera (LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to lat: " + latLng.latitude + ", lng: " + latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        // Clears all the markers on the map
        mMap.clear();

        /*if (!title.equals("MyLocation")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);

            mMap.addMarker(options);
        }*/

        //hideKeyboard();
    }

    private void moveCamera (LatLng latLng, float zoom, PlaceInfo placeInfo) {
        Log.d(TAG, "moveCamera: moving the camera to lat: " + latLng.latitude + ", lng: " + latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        // Clears all the markers on the map
        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));

        if (placeInfo != null) {
            try {
                String snippet = "Address: " + placeInfo.getAddress() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);

                mMarker = mMap.addMarker(options);

            } catch (NullPointerException e) {
                Log.d(TAG, "moveCamera: NullPointerException: " + e.getMessage());
            }
        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideKeyboard();
    }

    private void hideKeyboard () {
        Log.d(TAG, "hideKeyboard: keyboard hidden");

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    /**
     * -----------------------   Google Places API autocomplete suggestions   -----------------------
     */


    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);

            // Submits the request for the place
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    // Callback that gets all the information about the place
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "onResult: place query did not complete successfully: " + places.getStatus().toString());

                // Release in order to prevent a memory leak
                places.release();
                return;
            }

            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setId(place.getId());
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setLatLng(place.getLatLng());

                Log.d(TAG, "onResult: place info: " + mPlace.toString());

            } catch (NullPointerException e) {
                Log.d(TAG, "NullPointer Exception: " + e.getMessage());
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude, place.getViewport().getCenter().longitude), cameraDefaultZoom, mPlace);

            places.release();
        }
    };

}


