package com.example.airsick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapActivity extends Fragment {

    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/here/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    private Float lat = null;
    private Float lng = null;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (lat == null || lng == null) {
                lat = 49.2827f;
                lng = -123.1207f;
            }

            LatLng location = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                @Nullable
                @Override
                public URL getTileUrl(int x, int y, int zoom) {
                    String s = "https://tiles.aqicn.org/tiles/usepa-aqi/" + zoom + "/" + x + "/" + y + ".png?token=0ec2dee04055ae8588569571ef88a352ab1a5992";

                    try {
                        return new URL(s);
                    } catch (MalformedURLException e) {
                        throw new AssertionError(e);
                    }
                }
            };

            googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);
        _requestQueue = Volley.newRequestQueue(this.requireContext());
        queueParseJSON(API_URL);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void queueParseJSON(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    Gson gson = new Gson();

                    @Override
                    public void onResponse(JSONObject response) {
                        ApiInformation apiData = gson.fromJson(response.toString(), ApiInformation.class);

                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        _requestQueue.add(request);
    }

}

