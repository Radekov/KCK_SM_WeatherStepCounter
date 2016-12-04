package pl.pb.r.kcksm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import pl.pb.r.kcksm.model.weather.City;
import pl.pb.r.kcksm.model.weather.Clouds;
import pl.pb.r.kcksm.model.weather.Day;
import pl.pb.r.kcksm.model.weather.Main;
import pl.pb.r.kcksm.model.weather.Rain;
import pl.pb.r.kcksm.model.weather.Temp;
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
//    @SerializedName("cod")
//    @Expose
//    public String cod;
//    @SerializedName("message")
//    @Expose
//    public Float message;
//    @SerializedName("cnt")
//    @Expose
//    public Integer cnt;
    @SerializedName("list")
    @Expose
    public List<Day> days = new ArrayList<Day>();
}
