package com.example.airsick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView _cardView;

        public ViewHolder(CardView v) {
            super(v);
            _cardView = v;
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
        cityAQIDisplay.setText(String.valueOf(currentItem.getAqi()));
    }

    @Override
    public int getItemCount() {
        return _cityList.size();
    }
}
