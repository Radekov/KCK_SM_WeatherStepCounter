package pl.pb.r.kcksm.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

import pl.pb.r.kcksm.interfaces.OpenWeatherMapApi;
import pl.pb.r.kcksm.model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WheaterService extends IntentService {

    public final static String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String IMG_URL = "http://openweathermap.org/img/w/%s.png";
    public final static String API_KEY = "e64ed702a71529f3da697c8c68941ff8";
    public final static String METRIC = "metric";

    private WeatherData weather;
    private Map<String, Float> coordinate = new HashMap<String, Float>();

    public static final String ACTION_UPDATE_WEATHER = "ACTION_UPDATE_WEATHER";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";

    private static Retrofit retrofit = getRetrofitInstance();
    private static OpenWeatherMapApi weatherApi = getOpenWeatherApiInstance();

    public WheaterService() {
        super("WheaterService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        float lon = intent.getFloatExtra(EXTRA_LONGITUDE, coordinate.get("lon"));
        float lat = intent.getFloatExtra(EXTRA_LATITUDE, coordinate.get("lat"));

        coordinate.put("lon", lon);
        coordinate.put("lat", lat);
        System.out.println("------------------------");
        //sendWeather(getWeather(intent));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO: pobrać ostatnią pozycję z pliku
        coordinate.put("lon", new Float(22.455217));
        coordinate.put("lat", new Float(53.6471559));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //sendWeather(getWeather(intent));
        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    private WeatherData getWeather(Intent intent) {
        //weatherApi.getWeatherCity(22.45521f, 53.647155f, "metric", "e64ed702a71529f3da697c8c68941ff8");
        Call<WeatherData> call = getOpenWeatherApiInstance().getWeatherCity(coordinate.get("lat"),
                coordinate.get("lon"),
                "pl",
                API_KEY);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weather = response.body();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println(t);
            }
        });


        return null;
    }

    private boolean sendWeather(WeatherData w) {
        Intent intent = new Intent("NewWeather");
        //intent.putExtra(MainActivity.EXTRA_WEATHER,w);
        //sendBroadcast(intent2);
        return LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
    private static OpenWeatherMapApi getOpenWeatherApiInstance(){
        if(weatherApi == null)
            weatherApi = getRetrofitInstance().create(OpenWeatherMapApi.class);
        return weatherApi;
    }

    public static Call<WeatherData> getActuallWeatherData(float lon, float lat){
        Call<WeatherData> call = getOpenWeatherApiInstance().getWeatherCity(lat,
                lon,
                "pl",
                API_KEY);
        return call;
    }
}
