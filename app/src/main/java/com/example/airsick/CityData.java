package com.example.airsick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The type City data.
 */
public class CityData {
    private List<Float> geo;

    @SerializedName("name")
    @Expose
    private String cityName;

    private String url;

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets geo.
     *
     * @return the geo
     */
    public List<Float> getGeo() {
        return geo;
    }

    /**
     * Sets geo.
     *
     * @param geo the geo
     */
    public void setGeo(List<Float> geo) {
        this.geo = geo;
    }

    /**
     * Gets city name.
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets city name.
     *
     * @param cityName the city name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
