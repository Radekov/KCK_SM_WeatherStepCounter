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

    public static final String ACTION_UPDATE_WEATHER = "ACTION_UPDATE_LOCALIZATION";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";

    public final static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String IMG_URL = "http://openweathermap.org/img/w/%s.png";
    public final static String API_KEY = "e64ed702a71529f3da697c8c68941ff8";
    public final static String METRIC = "metric";

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
        float lon = intent.getFloatExtra(EXTRA_LONGITUDE, 22.45521f);
        float lat = intent.getFloatExtra(EXTRA_LATITUDE, 53.647155f);

        coordinate.put("lon", lon);
        coordinate.put("lat", lat);
        getWeather();
    }


//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("WeatherService","onStartCommand()");
//        return START_NOT_STICKY;
//    }

    private void getWeather() {
        Call<WeatherData> call = getOpenWeatherApiInstance().getWeatherCity(coordinate.get("lat"),
                coordinate.get("lon"),
                "pl",
                API_KEY);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weather = response.body();
                EventBus.getDefault().post(weather);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Log.d("WService getWeather", "weather");
        System.out.println(weather);
    }

    //TMP zastąpić aby serwis za to odpowiadał a nie jego statyczna metoda
    //Pogoda OBECNA
    public static Call<WeatherData> getActuallWeatherData(float lon, float lat) {
        Call<WeatherData> call = getOpenWeatherApiInstance().getWeatherCity(lat,
                lon,
                "pl",
                API_KEY);
        return call;
    }

    //TMP możliwe, że przenieść nawet do oddzielnego serwisu
    //Pogoda KILKU DNIOWA
    public static Call<ForecastData> getForecastData(String city) {
        Call<ForecastData> call = getOpenWeatherApiInstance().getForecastCity(
                city,
                "pl",
                API_KEY);
        return call;
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
