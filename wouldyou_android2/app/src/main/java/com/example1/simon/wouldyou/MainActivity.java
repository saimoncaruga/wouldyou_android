package com.example1.simon.wouldyou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public android.support.v4.app.Fragment eventsFragment = new EventsFragment();
    public android.support.v4.app.Fragment searchFragment = new SearchFragment();
    public android.support.v4.app.Fragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

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
