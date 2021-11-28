package com.example.airsick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForecastData {

    private DailyData daily;

    public DailyData getDaily() {
        return daily;
    }

    public void setDaily(DailyData daily) {
        this.daily = daily;
    }


}

class DailyData {

    @SerializedName("pm25")
    @Expose
    private ArrayList<ParticleData> pm25;

    public ArrayList<ParticleData> getPm25() {
        return pm25;
    }

    public void setPm25(ArrayList<ParticleData> pm25) {
        this.pm25 = pm25;
    }



}


class ParticleData {

    private String day;
    private int avg;

    public String getForecastDay() {
        return day;
    }

    public void setForecastDay(String forecastDay) {
        this.day = forecastDay;
    }

    public int getAverage() {
        return avg;
    }

    public void setAverage(int average) {
        this.avg = average;
    }

}