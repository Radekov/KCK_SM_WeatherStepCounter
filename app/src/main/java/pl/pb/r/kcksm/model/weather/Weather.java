package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Weather {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("icon")
    @Expose
    public String icon;
}
