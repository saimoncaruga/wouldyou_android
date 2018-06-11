package com.example1.simon.wouldyou;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    // Error if the user doesn't have the correct Google Play Services version
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public ListFragment eventsFragment = new EventsFragment();
    public android.support.v4.app.Fragment searchFragment = new SearchFragment();
    public android.support.v4.app.Fragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        FrameLayout target = findViewById(R.id.fragment_container);

        //loadFragment(searchFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
    }

    /*private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }

        return false;
    }*/

    // Check if the user has the right Google Play services version
    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {

            // Everything is fine and user can make requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            // An error occurred but it can be fixed
            Log.d(TAG, "isServicesOK: an error occurred but it can be fixed");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Youn can't make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch(item.getItemId()) {

            case R.id.navigation_events:  //nascondo o mostro i fragment che mi servono
                //fragment = new EventsFragment();
                //fragment = eventsFragment;
                //break;
                transaction.replace(R.id.fragment_container, eventsFragment);
                transaction.commit();
                //<nomeVista>.setVisibility(GONE);
                return true;

            case R.id.navigation_search:
                //fragment = new SearchFragment();
                //fragment = searchFragment;
                //break;
                transaction.replace(R.id.fragment_container, searchFragment);
                transaction.commit();
                return true;

            case R.id.navigation_profile:
                //fragment = new ProfileFragment();
                //fragment = profileFragment;
                //break;
                transaction.replace(R.id.fragment_container, profileFragment);
                transaction.commit();
                return true;
        }

        //return loadFragment(fragment);
        return false;
    }

}
