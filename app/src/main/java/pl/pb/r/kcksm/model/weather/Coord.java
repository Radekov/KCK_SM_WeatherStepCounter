package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Coord {
    @SerializedName("lon")
    @Expose
    public Float lon;
    @SerializedName("lat")
    @Expose
    public Float lat;
}
