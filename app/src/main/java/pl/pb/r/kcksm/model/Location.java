package pl.pb.r.kcksm.model;

import java.io.Serializable;

/**
 * Created by Radosław Naruszewicz on 2016-11-25.
 * https://github.com/survivingwithandroid/Swa-app/tree/master/WeatherApp/src/com/survivingwithandroid/weatherapp
 */

public class Location implements Serializable {

    private float longitude;
    private float latitude;

    private String country;
    private String city;
    private int sunrise;
    private int sunset;

    public Location() {
    }

    public Location(float longitude, float latitude, String country, String city, int sunrise, int sunset) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.city = city;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
    //TODO ZROBIĆ BUILDER


    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
