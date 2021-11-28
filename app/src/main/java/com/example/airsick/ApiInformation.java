package com.example.airsick;

import java.util.List;

/**
 * The type Api information.
 */
public class ApiInformation {

    private ApiData data;

    /**
     * Gets data.
     *
     * @return the data
     */
    public ApiData getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(ApiData data) {
        this.data = data;
    }
}

/**
 * The type Api data.
 */
class ApiData {
    private Integer aqi;
    private TimeData time;
    private CityData city;
    private ForecastData forecast;

    /**
     * Gets time.
     *
     * @return the time
     */
    public TimeData getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(TimeData time) {
        this.time = time;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public CityData getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(CityData city) {
        this.city = city;
    }

    /**
     * Gets aqi.
     *
     * @return the aqi
     */
    public Integer getAqi() {
        return aqi;
    }

    /**
     * Sets aqi.
     *
     * @param aqi the aqi
     */
    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    /**
     * Gets forecast.
     *
     * @return the forecast
     */
    public ForecastData getForecast() {
        return forecast;
    }

    /**
     * Sets forecast.
     *
     * @param forecast the forecast
     */
    public void setForecast(ForecastData forecast) {
        this.forecast = forecast;
    }
}

