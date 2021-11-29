package com.example.airsick;

import static android.location.LocationManager.*;
import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.*;
import android.os.Build;
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
import com.google.android.gms.location.LocationServices;
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

/**
 * The Google Map fragment uses a tile overlay from the AQICN API to display
 * air quality data all over the world.
 */
public class MapFragment extends Fragment {

    private Float lat = null;
    private Float lng = null;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {

            if (lat == null || lng == null) {
                lat = 49.2462f;
                lng = -123.0046f;
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

}

