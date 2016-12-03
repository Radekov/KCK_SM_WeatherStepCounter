package pl.pb.r.kcksm.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import pl.pb.r.kcksm.model.weather.Clouds;
import pl.pb.r.kcksm.model.weather.Coord;
import pl.pb.r.kcksm.model.weather.Main;
import pl.pb.r.kcksm.model.weather.Rain;
import pl.pb.r.kcksm.model.weather.Weather;
import pl.pb.r.kcksm.model.weather.Wind;

/**
 * Created by Radosław Naruszewicz on 2016-11-27.
 */
//TODO Parcelable
public class WeatherData {

    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("weather")
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("rain")
    @Expose
    public Rain rain;
    @SerializedName("clouds")
    @Expose
    public Clouds clouds;
    @SerializedName("dt")
    @Expose
    public Integer dt;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("cod")
    @Expose
    public Integer cod;

    public class Sys {
        @SerializedName("message")
        @Expose
        public Float message;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("sunrise")
        @Expose
        public Integer sunrise;
        @SerializedName("sunset")
        @Expose
        public Integer sunset;
    }
}