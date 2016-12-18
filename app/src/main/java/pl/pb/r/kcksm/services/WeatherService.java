package pl.pb.r.kcksm.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import pl.pb.r.kcksm.interfaces.OpenWeatherMapApi;
import pl.pb.r.kcksm.model.ForecastData;
import pl.pb.r.kcksm.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService extends IntentService {

    public static final String ACTION_UPDATE_LOCALIZATION_WEATHER = "ACTION_UPDATE_LOCALIZATION_WEATHER";
    public static final String ACTION_FORECAST_WEATHER = "ACTION_FORECAST_WEATHER";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_CITY = "EXTRA_CITY";

    public final static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String IMG_URL = "http://openweathermap.org/img/w/%s.png";
    public final static String API_KEY = "e64ed702a71529f3da697c8c68941ff8";

    private static Retrofit retrofit = getRetrofitInstance();
    private static OpenWeatherMapApi weatherApi = getOpenWeatherApiInstance();

    private WeatherData weather;
    private Map<String, Float> coordinate = new HashMap<String, Float>();

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WeatherService", "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("WeatherService", "onHandleIntent()");
        if (intent == null) return;
        switch (intent.getAction()) {
            case ACTION_UPDATE_LOCALIZATION_WEATHER:
                float lon = intent.getFloatExtra(EXTRA_LONGITUDE, 22.45521f);
                float lat = intent.getFloatExtra(EXTRA_LATITUDE, 53.647155f);
                coordinate.put("lon", lon);
                coordinate.put("lat", lat);
                getWeather();
                break;
            case ACTION_FORECAST_WEATHER:
                String city = intent.getStringExtra(EXTRA_CITY);
                getForecastData(city);
                break;
        }
    }

    private void getWeather() {
        Call<WeatherData> call = getOpenWeatherApiInstance().getWeatherCity(coordinate.get("lat"),
                coordinate.get("lon"),
                "pl",
                API_KEY);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                EventBus.getDefault().post(response.body());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getForecastData(String city) {
        Call<ForecastData> call = getOpenWeatherApiInstance().getForecastCity(
                city,
                "pl",
                API_KEY);
        call.enqueue(new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                EventBus.getDefault().post(response.body());
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }

    private static OpenWeatherMapApi getOpenWeatherApiInstance() {
        if (weatherApi == null)
            weatherApi = getRetrofitInstance().create(OpenWeatherMapApi.class);
        return weatherApi;
    }


}
