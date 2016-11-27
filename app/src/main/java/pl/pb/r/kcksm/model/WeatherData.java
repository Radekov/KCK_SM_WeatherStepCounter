package pl.pb.r.kcksm.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    public class Clouds {
        @SerializedName("all")
        @Expose
        public Integer all;
    }

    public class Coord {
        @SerializedName("lon")
        @Expose
        public Float lon;
        @SerializedName("lat")
        @Expose
        public Float lat;
    }

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
        @SerializedName("temp_min")
        @Expose
        public Float tempMin;
        @SerializedName("temp_max")
        @Expose
        public Float tempMax;
        @SerializedName("sea_level")
        @Expose
        public Float seaLevel;
        @SerializedName("grnd_level")
        @Expose
        public Float grndLevel;
    }

    public class Rain {
        @SerializedName("3h")
        @Expose
        public Float _3h;
    }

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

    public class Wind {
        @SerializedName("speed")
        @Expose
        public Float speed;
        @SerializedName("deg")
        @Expose
        public Float deg;
    }

    /*
    public WeatherTMP(){};

    protected WeatherTMP(Parcel in) {
        base = in.readString();
        name = in.readString();

        //Coord
        coord = new Coord();
        coord.lon = in.readFloat();
        coord.lat = in.readFloat();

        //Main
        main = new Main();
        main.temp = in.readFloat();
        main.pressure = in.readFloat();
        main.humidity = in.readInt();
        main.tempMin = in.readFloat();
        main.tempMax = in.readFloat();
        main.seaLevel = in.readFloat();
        main.grndLevel = in.readFloat();
        //Rain
        //Sys
        //Weather
        //Wind
    }
    Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(base);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherTMP> CREATOR = new Creator<WeatherTMP>() {
        @Override
        public WeatherTMP createFromParcel(Parcel in) {
            return new WeatherTMP(in);
        }

        @Override
        public WeatherTMP[] newArray(int size) {
            return new WeatherTMP[size];
        }
    };
    */
}