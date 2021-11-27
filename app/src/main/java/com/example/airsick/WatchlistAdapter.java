package com.example.airsick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {


    // creating a variable for array list and context.
    private ArrayList<CityRankObject> watchlistModalArrayList;
    private Context context;
    //public WatchlistAdapterListener onClickListener;

    // creating a constructor for our variables.
    public WatchlistAdapter(ArrayList<CityRankObject> watchlistModalArrayList, Context context) {
        this.watchlistModalArrayList = watchlistModalArrayList;
        this.context = context;
    }

        @NonNull
        @Override
        public WatchlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // below line is to inflate our layout.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WatchlistAdapter.ViewHolder holder, int position) {
            // setting data to our views of recycler view.
            CityRankObject modal = watchlistModalArrayList.get(position);
            holder.cityName.setText(modal.getName());
            holder.cityAQI.setText(modal.getAqi());
        }

        @Override
        public int getItemCount() {
            // returning the size of array list.
            return watchlistModalArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            // creating variables for our views.
            private TextView cityAQI, cityName;
            private Button favButton;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                // initializing our views with their ids.
                cityName = itemView.findViewById(R.id.cardCityName);
                cityAQI = itemView.findViewById(R.id.cardCityAQI);
                favButton = itemView.findViewById(R.id.favButton);
                CityRankObject modal = watchlistModalArrayList.get(getAdapterPosition());
                favButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("here");
                        Toast toast = Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        }

//        public interface WatchlistAdapterListener {
//            void favouriteButtonOnClick(View v, int position);
//        }
}
