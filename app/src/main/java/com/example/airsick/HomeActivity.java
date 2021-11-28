package com.example.airsick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends Fragment {

    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/here/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    private TextView currentCity;
    private TextView aqiText;
    private TextView sourceText;
    private TextView timeText;
    private LineChart lineChart;
    private XAxis xAxis;

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
        lineChart = (LineChart) view.findViewById(R.id.linechart);
        Description desc = new Description();
        desc.setText(" ");
        lineChart.setDescription(desc);
        lineChart.getAxisRight().setEnabled(false);
        xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            @Override
            public String getFormattedValue(float value) {
                long millis = (long) value * 1000L;
                return format.format(new Date(millis));
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        xAxis.setTextSize(12);
        lineChart.getAxisLeft().setTextSize(12);
        _requestQueue = Volley.newRequestQueue(this.requireContext());
        queueParseJSON(API_URL, view);
        return view;
    }

    private void queueParseJSON(String url, View view) {
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
                        GradientDrawable drawable = (GradientDrawable) aqiText.getBackground();
                        if (apiData.getData().getAqi() < 50) {
                            drawable.setColor(Color.rgb(0, 166, 110));
                            aqiText.setTextColor(Color.WHITE);
                        } else if (apiData.getData().getAqi() >= 50 && apiData.getData().getAqi() < 100) {
                            drawable.setColor(Color.rgb(79, 240, 10));
                            aqiText.setTextColor(Color.BLACK);
                        } else if (apiData.getData().getAqi() >= 100 && apiData.getData().getAqi() < 150) {
                            drawable.setColor(Color.rgb(240, 178, 10));
                            aqiText.setTextColor(Color.WHITE);
                        } else if (apiData.getData().getAqi() >= 150 && apiData.getData().getAqi() < 200) {
                            drawable.setColor(Color.rgb(242, 76, 70));
                            aqiText.setTextColor(Color.WHITE);
                        } else if (apiData.getData().getAqi() >= 200 && apiData.getData().getAqi() < 300) {
                            drawable.setColor(Color.rgb(219, 7, 187));
                            aqiText.setTextColor(Color.WHITE);
                        } else {
                            drawable.setColor(Color.rgb(112, 6, 66));
                            aqiText.setTextColor(Color.WHITE);
                        }

                        ZoneId zoneId = ZoneId.systemDefault();
                        ArrayList<ParticleData> pm25List = apiData.getData().getForecast().getDaily().getPm25();
                        List<Entry> entries = new ArrayList<Entry>();
                        xAxis.setLabelCount(pm25List.size(), true);
                        for (int i = 0; i < pm25List.size(); i++) {
                            int average = pm25List.get(i).getAverage();
                            String date = pm25List.get(i).getForecastDay();
                            LocalDate newDate = LocalDate.parse(date);
                            float floatDate = newDate.atStartOfDay(zoneId).toEpochSecond();
                            entries.add(new Entry(floatDate, average));
                        }
                        LineDataSet forcastPoints = new LineDataSet(entries, "Particle 2.5 Data");
                        forcastPoints.setDrawCircles(true);
                        forcastPoints.setCircleRadius(4);
                        forcastPoints.setDrawValues(false);
                        forcastPoints.setLineWidth(3);
                        forcastPoints.setColor(Color.GREEN);
                        forcastPoints.setCircleColor(Color.GREEN);
                        LineData lineData = new LineData(forcastPoints);
                        lineChart.setData(lineData);
                        lineChart.invalidate();

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