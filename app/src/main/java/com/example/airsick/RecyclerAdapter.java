package com.example.airsick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        cityNameDisplay.setText(String.valueOf(currentItem.getName()));
        String aqiText = "Current AQI: " + currentItem.getAqi();
        cityAQIDisplay.setText(aqiText);
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
