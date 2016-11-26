package pl.pb.r.kcksm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.util.Map;

import pl.pb.r.kcksm.http.WeatherHttpClient;
import pl.pb.r.kcksm.listeners.CountStepListener;
import pl.pb.r.kcksm.listeners.SensorStepListener;
import pl.pb.r.kcksm.model.Weather;
import pl.pb.r.kcksm.services.WheaterService;

public class MainActivity extends AppCompatActivity implements CountStepListener, LocationListener {

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;

    public static final String EXTRA_WEATHER = "EXTRA_WEATHER";

    private LocationManager locationManager = null;

    private TextView mTVCounter;
    private TextView city;
    private TextView temperature;
    private TextView latTV;
    private TextView lonTV;
    private ImageView imageView;
    private SensorEventListener mSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: przenieść do kontekstu aplikacji, wywalić by działało w tle - Service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-stepcounter
        * Większa precyzja i opóźnienie też większe
        * */
        //mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        createmSensorListener();


        //TODO Przenieść do fragmentu
        setContentView(R.layout.activity_main);
        mTVCounter = (TextView) findViewById(R.id.tv_counter);
        city = (TextView) findViewById(R.id.cityTView);
        temperature = (TextView) findViewById(R.id.tempTView);
        imageView = (ImageView) findViewById(R.id.imageView);
        latTV = (TextView) findViewById(R.id.tv_lat);
        lonTV = (TextView) findViewById(R.id.tv_lon);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

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

        IntentFilter f=new IntentFilter(WheaterService.ACTION_UPDATE_WEATHER);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(onEvent, f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(mSensorListener);
        //mSensorListener = null;
        locationManager.removeUpdates(this);
        
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(onEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void countStep(float count) {
        mTVCounter.setText(String.valueOf(count));
    }

    @Override
    public void onLocationChanged(Location location) {
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

    private BroadcastReceiver onEvent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case WheaterService.ACTION_UPDATE_WEATHER:
                    updateView(intent.getParcelableExtra(EXTRA_WEATHER));
                    break;
            }
        }
    };

    private void updateView(Parcelable p) {
        Weather weather = (Weather) p;
        Picasso.with(MainActivity.this).load(WeatherHttpClient.getImgUrl(weather.currentCondition.getIcon())).into(imageView);
        city.setText(weather.location.getCity() + "," + weather.location.getCountry());
        temperature.setText(weather.temperature.getTemp() + "C");


    }
}

//TODO Zapisać tekst licznika do Bundle