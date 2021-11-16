package com.example.airsick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class HomeActivity extends Fragment {

    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/here/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    private TextView currentCity;
    private TextView aqiText;
    private TextView sourceText;
    private TextView timeText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        currentCity = (TextView) view.findViewById(R.id.currentCityText);
        timeText = (TextView) view.findViewById(R.id.dateText);
        aqiText = (TextView) view.findViewById(R.id.ratingDisplayText);
        sourceText = (TextView) view.findViewById(R.id.companySourceText);
        _requestQueue = Volley.newRequestQueue(this.requireContext());
        queueParseJSON(API_URL);
        return view;
    }

    private void queueParseJSON(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

            Gson gson = new Gson();

                    @Override
                    public void onResponse(JSONObject response) {
                        ApiInformation apiData = gson.fromJson(response.toString(), ApiInformation.class);
                        currentCity.setText(apiData.getData().getCity().getCityName());
                        timeText.setText(apiData.getData().getTime().getS());
                        aqiText.setText(apiData.getData().getAqi().toString());
                        String sourceString = "<a href=\"" + apiData.getData().getCity().getUrl() + "\">Link To Source</a>";
                        sourceText.setMovementMethod(LinkMovementMethod.getInstance());
                        sourceText.setClickable(true);
                        sourceText.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_COMPACT));
                        sourceText.setTextColor(Color.BLUE);
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