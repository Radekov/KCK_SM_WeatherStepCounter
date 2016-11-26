package pl.pb.r.kcksm;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import pl.pb.r.kcksm.http.WeatherHttpClient;
import pl.pb.r.kcksm.listeners.CountStepListener;
import pl.pb.r.kcksm.listeners.SensorStepListener;
import pl.pb.r.kcksm.model.Weather;
import pl.pb.r.kcksm.wheater.JSONWeatherParse;

public class MainActivity extends AppCompatActivity implements CountStepListener, LocationListener {

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;

    private LocationManager locationManager =null;

    private TextView mTVCounter;
    private TextView city;
    private TextView temperature;
    private TextView latTV;
    private TextView lonTV;
    private ImageView imageView;
    private SensorEventListener mSensorListener;

    private JSONWeatherTask task;

    private Map<String, Float> coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coordinate = new HashMap<String, Float>();

        coordinate.put("lon", new Float(22.455217));
        coordinate.put("lat", new Float(53.6471559));

        //TODO: przenieść do kontekstu aplikacji, wywalić by działało w tle - Service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-stepcounter
        * Większa precyzja i opóźnienie też większe
        * */
        //mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        createmSensorListener();


        setContentView(R.layout.activity_main);
        mTVCounter = (TextView) findViewById(R.id.tv_counter);
        city = (TextView) findViewById(R.id.cityTView);
        temperature = (TextView) findViewById(R.id.tempTView);
        imageView = (ImageView) findViewById(R.id.imageView);
        latTV = (TextView)findViewById(R.id.tv_lat);
        lonTV = (TextView)findViewById(R.id.tv_lon);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        task = new JSONWeatherTask();
        task.execute(coordinate);
    }

    private void createmSensorListener() {
        if (mSensorListener == null) {
            mSensorListener = new SensorStepListener();
            ((SensorStepListener) mSensorListener).addListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //mSensorManager.registerListener(mSensorListener,mSensorStepCounter,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mSensorListener, mSensorStepCounter, SensorManager.SENSOR_DELAY_UI);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(mSensorListener);
        //mSensorListener = null;
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(task != null)
            task.cancel(false);
    }

    @Override
    public void countStep(float count) {
        mTVCounter.setText(String.valueOf(count));
    }

    @Override
    public void onLocationChanged(Location location) {
        coordinate.put("lat",new Float((float)location.getLatitude()));
        coordinate.put("lon",new Float((float)location.getLongitude()));

        latTV.setText(Double.toString(location.getLatitude()));
        //task.doInBackground(coordinate);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class JSONWeatherTask extends AsyncTask<Map<String, Float>, Void, Weather> {
        @Override
        protected Weather doInBackground(Map<String, Float>... params) {
            Weather weather = new Weather();
            Map<String, Float> cord = params[0];
            String data = ((new WeatherHttpClient()).getWeatherData(cord.get("lat"), cord.get("lon")));
            System.out.println(data);

            try {
                weather = JSONWeatherParse.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            Picasso.with(MainActivity.this).load(WeatherHttpClient.getImgUrl(weather.currentCondition.getIcon())).into(imageView);
            city.setText(weather.location.getCity() + "," + weather.location.getCountry());
            temperature.setText(weather.temperature.getTemp() + "C");
        }
    }

}

//TODO Zapisać tekst licznika do Bundle