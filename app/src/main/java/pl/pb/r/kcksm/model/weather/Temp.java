package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Temp {
    @SerializedName("day")
    @Expose
    public Float day;
    @SerializedName("min")
    @Expose
    public Float min;
    @SerializedName("max")
    @Expose
    public Float max;
    @SerializedName("night")
    @Expose
    public Float night;
    @SerializedName("eve")
    @Expose
    public Float eve;
    @SerializedName("morn")
    @Expose
    public Float morn;
}
