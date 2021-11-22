package com.example.airsick;

public class CityRankObject implements Comparable<CityRankObject> {

    private String name;
    private int aqi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public CityRankObject(String name, int aqi) {
        this.name = name;
        this.aqi = aqi;
    }

    @Override
    public int compareTo(CityRankObject cityRankObject) {
        if (this.aqi > cityRankObject.aqi) {
            return -1;
        } else if (this.aqi == cityRankObject.aqi) {
            return 0;
        } else {
            return 1;
        }
    }
}
