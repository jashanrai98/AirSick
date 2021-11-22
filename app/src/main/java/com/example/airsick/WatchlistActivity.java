package com.example.airsick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WatchlistActivity extends Fragment {

    SharedPreferences sp;
    String cityName;
    int cityAQI;
    CityRankObject item;
    private WatchlistAdapter adapter;
    private ArrayList<CityRankObject> watchlistModalArrayList;
    private RecyclerView favListRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sp = this.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);
        loadWatchlist();
        buildRecyclerView();
        favListRV = this.getActivity().findViewById(R.id.favList);
        return inflater.inflate(R.layout.activity_watchlist, container, false);
    }

    public void saveCity(View view) {
        sp = this.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(watchlistModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("fav", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        //Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    public void loadWatchlist() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("fav", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<CityRankObject>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        watchlistModalArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (watchlistModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            watchlistModalArrayList = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        adapter = new WatchlistAdapter(watchlistModalArrayList, this.getContext());

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        favListRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        favListRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        favListRV.setAdapter(adapter);
    }
}