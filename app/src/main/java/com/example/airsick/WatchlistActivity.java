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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class WatchlistActivity extends Fragment implements SearchView.OnQueryTextListener{

    SharedPreferences sp;
    String cityName;
    int cityAQI;
    CityRankObject item;
    WatchlistAdapter adapter;
    private ArrayList<CityRankObject> watchlistModalArrayList;
    private RecyclerView favListRV;
    ArrayList<CityRankObject> arraylist = new ArrayList<CityRankObject>();
    String[] cList;
    ListView list;

    SearchView editsearch;
    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/";
    private static final String API_TOKEN = "/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter2;
    RecyclerView recyclerView2;
    protected View mView;
    ToggleButton btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_watchlist, container, false);
        _requestQueue = Volley.newRequestQueue(this.requireContext());
        this.mView = view;
        sp = view.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);

        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(lm);

        recyclerView2 = (RecyclerView) mView.findViewById(R.id.favList2);

        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager lm2 = new LinearLayoutManager(view.getContext());
        recyclerView2.setLayoutManager(lm2);

        //RecyclerView favListRV = this.requireActivity().findViewById(R.id.favList);
        cList = CityList.getListOfCities();
        ArrayList<CityRankObject> cities = new ArrayList<>();




        // Locate the EditText in listview_main.xml


        editsearch = (SearchView) view.findViewById(R.id.search_bar);
        editsearch.setOnQueryTextListener(this);
        return view;
    }



    public void saveCity(View view) {
        sp = view.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(arraylist);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("fav", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        //Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    public void loadWatchlist(View view) {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("savedWatchlist", Context.MODE_PRIVATE);

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

    private void buildRecyclerView(View view) {
        // initializing our adapter class.
        adapter = new WatchlistAdapter(watchlistModalArrayList, view.getContext());

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        favListRV.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        favListRV.setLayoutManager(manager);

        // setting adapter to our recycler view.
        favListRV.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String cityURL = API_URL + s + API_TOKEN;
        ArrayList<CityRankObject> cities = new ArrayList<>();
        queueParseJSON(new WatchlistActivity.CallBack() {
            @Override
            public void onSuccess(CityRankObject currentCity) {

                cities.add(currentCity);
                //arraylist.add(currentCity);
                recyclerAdapter = new RecyclerAdapter(getActivity(), cities);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFail(String errorMessage) {
                System.out.println(errorMessage);
            }
        }, cityURL);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        //adapter.filter(text);
        return false;
    }

    private void queueParseJSON(final WatchlistActivity.CallBack onCallBack, String url) {



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    Gson gson = new Gson();

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("status").equals("ok")) {
                                JSONObject data = response.getJSONObject("data");
                                if (!data.get("aqi").toString().equals("-")) {
                                    ApiInformation apiData = gson.fromJson(response.toString(), ApiInformation.class);
                                    CityRankObject currentCity = new CityRankObject(apiData.getData().getCity().getCityName(), apiData.getData().getAqi());
                                    onCallBack.onSuccess(currentCity);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onCallBack.onFail("Unable to get city data");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        _requestQueue.add(request);
    }

    public interface CallBack {
        void onSuccess(CityRankObject currentCity);
        void onFail(String errorMessage);
    }

    public void findCity(String s){
        String cityURL = API_URL + s + API_TOKEN;
        ArrayList<CityRankObject> cities = new ArrayList<>();
        queueParseJSON(new WatchlistActivity.CallBack() {
            @Override
            public void onSuccess(CityRankObject currentCity) {
                cities.add(currentCity);
                recyclerAdapter = new RecyclerAdapter(getActivity(), cities);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFail(String errorMessage) {
                System.out.println(errorMessage);
            }
        }, cityURL);
    }


}