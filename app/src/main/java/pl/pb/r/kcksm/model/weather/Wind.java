package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Wind {
    @SerializedName("speed")
    @Expose
    public Float speed;
    @SerializedName("deg")
    @Expose
    public Float deg;
}
