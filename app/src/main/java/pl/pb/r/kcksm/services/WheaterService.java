package pl.pb.r.kcksm.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import pl.pb.r.kcksm.MainActivity;
import pl.pb.r.kcksm.http.WeatherHttpClient;
import pl.pb.r.kcksm.model.Weather;
import pl.pb.r.kcksm.wheater.JSONWeatherParse;

public class WheaterService extends IntentService {

    public static final String ACTION_UPDATE_WEATHER = "ACTION_UPDATE_WEATHER";

    private Weather weather;
    private Map<String, Float> coordinate = new HashMap<String, Float>();

    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";
    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";

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
        sendWeather(getWeather(intent));
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
        sendWeather(getWeather(intent));

        //return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    private Weather getWeather(Intent intent){
        float lon = intent.getFloatExtra(EXTRA_LONGITUDE,coordinate.get("lon"));
        float lat = intent.getFloatExtra(EXTRA_LATITUDE,coordinate.get("lat"));

        coordinate.put("lon",lon);
        coordinate.put("lat",lat);

        weather = new Weather();
        String data = ((new WeatherHttpClient()).getWeatherData(coordinate.get("lat"), coordinate.get("lon")));
        try {
            weather = JSONWeatherParse.getWeather(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weather;
    }
    private boolean sendWeather(Weather w){
        Intent intent = new Intent("NewWeather");
        intent.putExtra(MainActivity.EXTRA_WEATHER,w);
        //sendBroadcast(intent2);
        return LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
