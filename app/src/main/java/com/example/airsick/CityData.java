package com.example.airsick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityData {
    private List<Float> geo;

    @SerializedName("name")
    @Expose
    private String cityName;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Float> getGeo() {
        return geo;
    }

    public void setGeo(List<Float> geo) {
        this.geo = geo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
