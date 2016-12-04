package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import pl.pb.r.kcksm.model.ForecastData;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class City {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("coord")
    @Expose
    public Coord coord;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("population")
    @Expose
    public Integer population;
}
