package com.example.airsick;

import androidx.fragment.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The Search fragment creates our city search page which allows users to look up any city
 * and get it's air quality information.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener{


    private SearchView editsearch;
    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/";
    private static final String API_TOKEN = "/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    private TextView currentCity;
    private TextView aqiText;
    private TextView sourceText;
    private TextView timeText;
    private LineChart lineChart;
    private TextView severityMessage;
    private TextView timeTextDesc;
    private TextView aqiTextDesc;
    private TextView sourceTextDesc;
    private TextView graphTextDesc;
    private XAxis xAxis;
    /**
     * The M view.
     */
    protected View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_watchlist, container, false);
        _requestQueue = Volley.newRequestQueue(this.requireContext());
        this.mView = view;

        editsearch = (SearchView) view.findViewById(R.id.search_bar);
        editsearch.setOnQueryTextListener(this);

        currentCity = (TextView) view.findViewById(R.id.searchedCityText);
        timeText = (TextView) view.findViewById(R.id.searchDateText);
        aqiText = (TextView) view.findViewById(R.id.searchRatingDisplayText);
        sourceText = (TextView) view.findViewById(R.id.searchCompanySourceText);
        severityMessage = (TextView) view.findViewById(R.id.searchRatingSeverityText);
        lineChart = (LineChart) view.findViewById(R.id.searchLineChart);

        timeTextDesc = view.findViewById(R.id.searchCollectionText);
        aqiTextDesc = view.findViewById(R.id.searchRatingTagText);
        sourceTextDesc = view.findViewById(R.id.searchSourceText);
        graphTextDesc = view.findViewById(R.id.searchGraphTitle);

        setTextViewsInvisible();

        aqiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (severityMessage.getVisibility() == View.INVISIBLE) {
                    severityMessage.setVisibility(View.VISIBLE);
                } else {
                    severityMessage.setVisibility(View.INVISIBLE);
                }
            }
        });

        configureLineChart(lineChart);

        return view;
    }

    private void setTextViewVisible() {
        timeTextDesc.setVisibility(View.VISIBLE);
        aqiTextDesc.setVisibility(View.VISIBLE);
        sourceTextDesc.setVisibility(View.VISIBLE);
        graphTextDesc.setVisibility(View.VISIBLE);
        currentCity.setVisibility(View.VISIBLE);
        timeText.setVisibility(View.VISIBLE);
        aqiText.setVisibility(View.VISIBLE);
        sourceText.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.VISIBLE);
    }

    private void setTextViewsInvisible() {
        timeTextDesc.setVisibility(View.INVISIBLE);
        aqiTextDesc.setVisibility(View.INVISIBLE);
        sourceTextDesc.setVisibility(View.INVISIBLE);
        graphTextDesc.setVisibility(View.INVISIBLE);
        currentCity.setVisibility(View.INVISIBLE);
        timeText.setVisibility(View.INVISIBLE);
        aqiText.setVisibility(View.INVISIBLE);
        sourceText.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);
    }

    private void configureLineChart(LineChart lineChart) {
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
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String cityURL = API_URL + s + API_TOKEN;

        queueParseJSON(new SearchFragment.CallBack() {
            @Override
            public void onSuccess(ApiInformation apiData) {
                setTextViewInfo(apiData.getData().getCity().getCityName(),
                        apiData.getData().getTime().getS(),
                        apiData.getData().getAqi().toString());

                configureSourceLine(apiData.getData().getCity().getUrl());

                setWidgetColour(apiData.getData().getAqi());

                setGraphData(apiData.getData().getForecast().getDaily().getPm25());

                setTextViewVisible();

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
        return false;
    }

    private void queueParseJSON(final SearchFragment.CallBack onCallBack, String url) {

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
                                    onCallBack.onSuccess(apiData);
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

    /**
     * The interface Call back.
     */
    public interface CallBack {

        /**
         * On success.
         *
         * @param apiData the api data
         */
        void onSuccess(ApiInformation apiData);

        /**
         * On fail.
         *
         * @param errorMessage the error message
         */
        void onFail(String errorMessage);
    }

    private void setTextViewInfo(String city, String time, String aqi) {
        currentCity.setText(city);
        timeText.setText(time);
        aqiText.setText(aqi);
    }

    private void configureSourceLine(String url) {
        String sourceString = "<a href=\"" + url + "\">Link To Source</a>";
        sourceText.setMovementMethod(LinkMovementMethod.getInstance());
        sourceText.setClickable(true);
        sourceText.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_COMPACT));
        sourceText.setTextColor(Color.BLUE);
    }

    private void setWidgetColour(Integer aqi) {
        GradientDrawable drawable = (GradientDrawable) aqiText.getBackground();
        LayerDrawable tooltipDrawable = (LayerDrawable) severityMessage.getBackground();
        GradientDrawable bigRectangle = (GradientDrawable) tooltipDrawable.findDrawableByLayerId(R.id.ttRectangle);
        RotateDrawable rotateTriangle = (RotateDrawable) tooltipDrawable.findDrawableByLayerId(R.id.ttTriangle);
        GradientDrawable triangle = (GradientDrawable) rotateTriangle.getDrawable();
        String lowMessage = "Low";
        String moderateMessage = "Moderate";
        String sensitiveMessage = "Unhealthy For Sensitive Groups";
        String unhealthyMessage = "Unhealthy";
        String vUnhealthyMessage = "Very Unhealthy";
        String hazMessage = "Hazardous";
        if (aqi < 50) {
            drawable.setColor(Color.rgb(0, 166, 110));
            aqiText.setTextColor(Color.WHITE);
            bigRectangle.setColor(Color.rgb(0, 166, 110));
            triangle.setColor(Color.rgb(0, 166, 110));
            severityMessage.setText(lowMessage);
        } else if (aqi < 100) {
            drawable.setColor(Color.rgb(79, 240, 10));
            aqiText.setTextColor(Color.BLACK);
            bigRectangle.setColor(Color.rgb(79, 240, 10));
            triangle.setColor(Color.rgb(79, 240, 10));
            severityMessage.setText(moderateMessage);
        } else if (aqi < 150) {
            drawable.setColor(Color.rgb(240, 178, 10));
            aqiText.setTextColor(Color.WHITE);
            bigRectangle.setColor(Color.rgb(240, 178, 10));
            triangle.setColor(Color.rgb(240, 178, 10));
            severityMessage.setText(sensitiveMessage);
        } else if (aqi < 200) {
            drawable.setColor(Color.rgb(242, 76, 70));
            aqiText.setTextColor(Color.WHITE);
            bigRectangle.setColor(Color.rgb(242, 76, 70));
            triangle.setColor(Color.rgb(242, 76, 70));
            severityMessage.setText(unhealthyMessage);
        } else if (aqi < 300) {
            drawable.setColor(Color.rgb(219, 7, 187));
            aqiText.setTextColor(Color.WHITE);
            bigRectangle.setColor(Color.rgb(219, 7, 187));
            triangle.setColor(Color.rgb(219, 7, 187));
            severityMessage.setText(vUnhealthyMessage);
        } else {
            drawable.setColor(Color.rgb(112, 6, 66));
            aqiText.setTextColor(Color.WHITE);
            bigRectangle.setColor(Color.rgb(112, 6, 66));
            triangle.setColor(Color.rgb(112, 6, 66));
            severityMessage.setText(hazMessage);
        }
    }

    private void setGraphData(ArrayList<ParticleData> apiForcastList) {
        ZoneId zoneId = ZoneId.systemDefault();
        ArrayList<ParticleData> pm25List = apiForcastList;
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


}