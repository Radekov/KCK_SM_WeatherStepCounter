package pl.pb.r.kcksm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import pl.pb.r.kcksm.model.weather.City;
import pl.pb.r.kcksm.model.weather.Clouds;
import pl.pb.r.kcksm.model.weather.Main;
import pl.pb.r.kcksm.model.weather.Rain;
import pl.pb.r.kcksm.model.weather.Weather;
import pl.pb.r.kcksm.model.weather.Wind;

/**
 * Created by Radosław Naruszewicz on 2016-12-01.
 */
//https://openweathermap.org/forecast5
public class ForecastData {

    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cod")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public Float message;
    //Number of lines returned by this API call
//    @SerializedName("cnt")
//    @Expose
//    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<List> list = new ArrayList<List>();

    public class List {

        @SerializedName("dt")
        @Expose
        public Integer dt;
        @SerializedName("main")
        @Expose
        public Main main;
        @SerializedName("weather")
        @Expose
        public java.util.List<Weather> weather = new ArrayList<Weather>();
        @SerializedName("clouds")
        @Expose
        public Clouds clouds;
        @SerializedName("wind")
        @Expose
        public Wind wind;
        @SerializedName("rain")
        @Expose
        public Rain rain;
        @SerializedName("sys")
        @Expose
        public Sys_ sys;
        @SerializedName("dt_txt")
        @Expose
        public String dtTxt;

    }

    public class Sys {

        @SerializedName("population")
        @Expose
        public Integer population;

    }

    public class Sys_ {

        @SerializedName("pod")
        @Expose
        public String pod;

    }

}
