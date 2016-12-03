package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Rain {
    @SerializedName("3h")
    @Expose
    public Float _3h;
}
