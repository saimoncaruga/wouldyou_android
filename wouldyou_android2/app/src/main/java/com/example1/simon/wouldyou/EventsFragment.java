package com.example1.simon.wouldyou;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example1.simon.wouldyou.Models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventsFragment extends android.support.v4.app.ListFragment {

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, null);
    }*/

    private static final String TAG = "EventsFragment";

    private ListView listView;
    private EventsAdapter mAdapter;
    private Button createdBtn;
    private Button participateBtn;

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Cane", "Gatto", "Maiale", "Uccello", "Polipo",
                "Sardina", "Aragosta", "Cervo","Aquila" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        listView
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        Log.d(TAG, "onCreateView");

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onViewCreated");

        createdBtn = getView().findViewById(R.id.events_created);
        participateBtn = getView().findViewById(R.id.events_participated);
        listView = getView().findViewById(R.id.events_list);

        createdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fillListView("user-events-active");
                final ArrayList<Event> eventsList = new ArrayList<>();

                Event event = new Event("Cane", "Sono un cane pazzo", 45.673208, 9.042247, "2018-06-25 00:29:49", "2018-06-25 00:29:49", 8, 1, 1);
                eventsList.add(event);

                mAdapter = new EventsAdapter(getContext(), eventsList);
                listView.setAdapter(mAdapter);

            }
        });

        participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillListView("");
            }
        });

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
        Log.d("ListFragment", id + "");
    }

    private void fillListView(String eventsRequestURL) {

        /*Log.d(TAG, eventsRequestURL);
        final ArrayList<Event> eventsList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "http://lalalaravel.test/api/" + eventsRequestURL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "fillListView: onResponse: " + response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String name = response.getJSONObject(i).getString("name");
                                String description = response.getJSONObject(i).getString("description");

                                Log.d(TAG, "onResponse: name: " + name);
                                Log.d(TAG, "onResponse: name: " + description);

                                Event event = new Event(name, description, 45.673208, 9.042247, "2018-06-25 00:29:49", "2018-06-25 00:29:49", 8, 1, 1);
                                eventsList.add(event);
                            }

                            mAdapter = new EventsAdapter(getContext(), eventsList);
                            listView.setAdapter(mAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                                    Log.d(TAG, "onItemClick: I clicked!");
                                }
                            });
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonArrayRequest);*/

    }
}
