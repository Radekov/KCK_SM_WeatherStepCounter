package pl.pb.r.kcksm.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class Main {
    @SerializedName("temp")
    @Expose
    public Float temp;
    @SerializedName("pressure")
    @Expose
    public Float pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
//    @SerializedName("temp_min")
//    @Expose
//    public Float tempMin;
//    @SerializedName("temp_max")
//    @Expose
//    public Float tempMax;
//    @SerializedName("sea_level")
//    @Expose
//    public Float seaLevel;
//    @SerializedName("grnd_level")
//    @Expose
//    public Float grndLevel;
}
