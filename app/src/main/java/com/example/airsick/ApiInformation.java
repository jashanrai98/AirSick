package com.example.airsick;

import java.util.List;

public class ApiInformation {

    private ApiData data;

    public ApiData getData() {
        return data;
    }

    public void setData(ApiData data) {
        this.data = data;
    }
}

class ApiData {
    private Integer aqi;
    private TimeData time;
    private CityData city;
    private ForecastData forecast;

    public TimeData getTime() {
        return time;
    }

    public void setTime(TimeData time) {
        this.time = time;
    }

    public CityData getCity() {
        return city;
    }

    public void setCity(CityData city) {
        this.city = city;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public ForecastData getForecast() {
        return forecast;
    }

    public void setForecast(ForecastData forecast) {
        this.forecast = forecast;
    }
}

