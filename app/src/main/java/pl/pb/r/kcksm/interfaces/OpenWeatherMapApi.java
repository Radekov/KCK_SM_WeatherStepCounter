package pl.pb.r.kcksm.interfaces;

import pl.pb.r.kcksm.model.ForecastData;
import pl.pb.r.kcksm.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Radosław Naruszewicz on 2016-11-26.
 */

public interface OpenWeatherMapApi {
    //@GET("data/2.5/weather?lat={lat}&lon={lon}&units=metric&APPID={appid}")
    @GET("weather?units=metric")
    Call<WeatherData> getWeatherCity(@Query("lat") float lat,
                                     @Query("lon") float lon,
                                     @Query("lang") String lang,
                                     @Query("APPID") String appid);

    @GET("forecast/daily?units=metric")
    Call<ForecastData> getForecastCity(@Query("q") String city,
                                       @Query("lang") String lang,
                                       @Query("APPID") String appid);
}
