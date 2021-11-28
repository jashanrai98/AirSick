package com.example.airsick;

/**
 * The type City rank object.
 */
public class CityRankObject implements Comparable<CityRankObject> {

    private String name;
    private int aqi;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets aqi.
     *
     * @return the aqi
     */
    public int getAqi() {
        return aqi;
    }

    /**
     * Sets aqi.
     *
     * @param aqi the aqi
     */
    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    /**
     * Instantiates a new City rank object.
     *
     * @param name the name
     * @param aqi  the aqi
     */
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
