package com.example.airsick;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;

public class RankingsFragment extends Fragment {

    private RequestQueue _requestQueue;
    private static final String API_URL = "https://api.waqi.info/feed/";
    private static final String API_TOKEN = "/?token=0ec2dee04055ae8588569571ef88a352ab1a5992";
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ArrayList<CityRankObject> mExample;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rankings, container, false);

        _requestQueue = Volley.newRequestQueue(this.requireContext());
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(lm);

        String[] cityList = CityList.getListOfCities();
        ArrayList<CityRankObject> cities = new ArrayList<>();

        view.findViewById(R.id.recycler_view).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        for (String cityName : cityList) {
            String cityURL = API_URL + cityName + API_TOKEN;
            queueParseJSON(new CallBack() {
                @Override
                public void onSuccess(CityRankObject currentCity) {
                    cities.add(currentCity);
                    Collections.sort(cities);
                    recyclerAdapter = new RecyclerAdapter(getActivity(), cities);
                    recyclerView.setAdapter(recyclerAdapter);
                    if (cities.size() > 50 && view.findViewById(R.id.progressBar).getVisibility() == View.VISIBLE) {
                        view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFail(String errorMessage) {
                    System.out.println(errorMessage);
                }
            }, cityURL);
        }
        
        return view;
    }

    private void queueParseJSON(final CallBack onCallBack, String url) {


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

}

