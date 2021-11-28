package com.example.airsick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Forecast data.
 */
public class ForecastData {

    private DailyData daily;

    /**
     * Gets daily.
     *
     * @return the daily
     */
    public DailyData getDaily() {
        return daily;
    }

    /**
     * Sets daily.
     *
     * @param daily the daily
     */
    public void setDaily(DailyData daily) {
        this.daily = daily;
    }


}

/**
 * The type Daily data.
 */
class DailyData {

    @SerializedName("pm25")
    @Expose
    private ArrayList<ParticleData> pm25;

    /**
     * Gets pm 25.
     *
     * @return the pm 25
     */
    public ArrayList<ParticleData> getPm25() {
        return pm25;
    }

    /**
     * Sets pm 25.
     *
     * @param pm25 the pm 25
     */
    public void setPm25(ArrayList<ParticleData> pm25) {
        this.pm25 = pm25;
    }



}


/**
 * The type Particle data.
 */
class ParticleData {

    private String day;
    private int avg;

    /**
     * Gets forecast day.
     *
     * @return the forecast day
     */
    public String getForecastDay() {
        return day;
    }

    /**
     * Sets forecast day.
     *
     * @param forecastDay the forecast day
     */
    public void setForecastDay(String forecastDay) {
        this.day = forecastDay;
    }

    /**
     * Gets average.
     *
     * @return the average
     */
    public int getAverage() {
        return avg;
    }

    /**
     * Sets average.
     *
     * @param average the average
     */
    public void setAverage(int average) {
        this.avg = average;
    }

}