package com.example.airsick;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context _context;
    private ArrayList<CityRankObject> _cityList;


    public RecyclerAdapter(Context context, ArrayList<CityRankObject> cityArrayList) {
        _context = context;
        _cityList = cityArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView _cardView;

        public ViewHolder(CardView v) {
            super(v);
            _cardView = v;
        }
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(_context)
                .inflate(R.layout.card_item, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        final CardView cardView = holder._cardView;
        CityRankObject currentItem = _cityList.get(position);
        TextView cityNameDisplay = cardView.findViewById(R.id.cardCityName);
        TextView cityAQIDisplay = cardView.findViewById(R.id.cardCityAQI);
        TextView cardSeverity = cardView.findViewById(R.id.cardSeverity);
        cityNameDisplay.setText(String.valueOf(currentItem.getName()));
        String aqiText = "Current AQI: " + currentItem.getAqi();
        cityAQIDisplay.setText(aqiText);
        setCardColour(cardView, cityNameDisplay, cityAQIDisplay, currentItem);
        setSeverityMessage(currentItem.getAqi(), cardSeverity);
    }

    private void setSeverityMessage(int aqi, TextView cardSeverity) {
        String lowMessage = "Severity: Low";
        String moderateMessage = "Severity: Moderate";
        String sensitiveMessage = "Severity: Slightly Unhealthy";
        String unhealthyMessage = "Severity: Unhealthy";
        String vUnhealthyMessage = "Severity: Very Unhealthy";
        String hazMessage = "Severity: Hazardous";
        if (aqi < 50) {
            cardSeverity.setText(lowMessage);
            cardSeverity.setTextColor(Color.WHITE);
        } else if (aqi < 100) {
            cardSeverity.setText(moderateMessage);
            cardSeverity.setTextColor(Color.BLACK);
        } else if (aqi < 150) {
            cardSeverity.setText(sensitiveMessage);
            cardSeverity.setTextColor(Color.WHITE);
        } else if (aqi < 200) {
            cardSeverity.setText(unhealthyMessage);
            cardSeverity.setTextColor(Color.WHITE);
        } else if (aqi < 300) {
            cardSeverity.setText(vUnhealthyMessage);
            cardSeverity.setTextColor(Color.WHITE);
        } else {
            cardSeverity.setText(hazMessage);
            cardSeverity.setTextColor(Color.WHITE);
        }
    }

    /**
     * Sets the colour of the card in the RecyclerView based on the region's AQI
     * @param cardView the current holder
     * @param cityNameDisplay textview in card
     * @param cityAQIDisplay textview in card
     * @param currentItem current data
     */
    private void setCardColour(CardView cardView, TextView cityNameDisplay,
                               TextView cityAQIDisplay, CityRankObject currentItem) {
        if (currentItem.getAqi() < 50) {
            cardView.setCardBackgroundColor(Color.rgb(0, 166, 110));
            cityNameDisplay.setTextColor(Color.WHITE);
            cityAQIDisplay.setTextColor(Color.WHITE);
        } else if (currentItem.getAqi() >= 50 && currentItem.getAqi() < 100) {
            cardView.setCardBackgroundColor(Color.rgb(79, 240, 10));
            cityNameDisplay.setTextColor(Color.BLACK);
            cityAQIDisplay.setTextColor(Color.BLACK);
        } else if (currentItem.getAqi() >= 100 && currentItem.getAqi() < 150) {
            cardView.setCardBackgroundColor(Color.rgb(240, 178, 10));
            cityNameDisplay.setTextColor(Color.WHITE);
            cityAQIDisplay.setTextColor(Color.WHITE);
        } else if (currentItem.getAqi() >= 150 && currentItem.getAqi() < 200) {
            cardView.setCardBackgroundColor(Color.rgb(242, 76, 70));
            cityNameDisplay.setTextColor(Color.WHITE);
            cityAQIDisplay.setTextColor(Color.WHITE);
        } else if (currentItem.getAqi() >= 200 && currentItem.getAqi() < 300) {
            cardView.setCardBackgroundColor(Color.rgb(219, 7, 187));
            cityNameDisplay.setTextColor(Color.WHITE);
            cityAQIDisplay.setTextColor(Color.WHITE);
        } else {
            cardView.setCardBackgroundColor(Color.rgb(112, 6, 66));
            cityNameDisplay.setTextColor(Color.WHITE);
            cityAQIDisplay.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return _cityList.size();
    }

}
