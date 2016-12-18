package pl.pb.r.kcksm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import pl.pb.r.kcksm.App;
import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.listeners.CountStepListener;
import pl.pb.r.kcksm.listeners.SensorStepListener;
import pl.pb.r.kcksm.model.DaoSession;
import pl.pb.r.kcksm.model.SumStepDao;
import pl.pb.r.kcksm.model.WeatherData;
import pl.pb.r.kcksm.services.SumStepsDaoService;
import pl.pb.r.kcksm.services.WeatherService;

public class MainActivity extends AppCompatActivity implements CountStepListener {

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;

    private TextView mTVCounter;
    private TextView city;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView description;
    private ImageView imageView;

    private SensorEventListener mSensorListener;

    //    private WeatherTask task;
    private DaoSession daoSession;
    private SumStepDao sumStepDao;

    protected Toolbar toolbar;

    public static final String EXTRA_WEATHER = "EXTRA_WEATHER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        daoSession = ((App) getApplication()).getDaoSession();

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
        setToolbar();
        setUpViews();

//        Map<String, Float> c = new HashMap<>();
//        c.put("lon", 22.455217f);
//        c.put("lat", 53.6471559f);
//        task = new WeatherTask();
//        task.execute(c);
    }

    protected void setUpViews() {
        mTVCounter = (TextView) findViewById(R.id.tv_counter);
        city = (TextView) findViewById(R.id.tv_city);
        temperature = (TextView) findViewById(R.id.tv_temperature);
        pressure = (TextView) findViewById(R.id.tv_pressure);
        humidity = (TextView) findViewById(R.id.tv_humidity);
        description = (TextView) findViewById(R.id.tv_description);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    protected void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.actions);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stats:
                Intent intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                break;
            case R.id.forecast:
                intent = new Intent(this, WeatherCityActivity.class);
                String cityCountry = city.getText().toString();
                String extra = cityCountry.substring(0, cityCountry.indexOf(','));
                intent.putExtra(WeatherCityActivity.EXTRA_CITY, extra);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void createmSensorListener() {
        if (mSensorListener == null) {
            mSensorListener = new SensorStepListener();
            ((SensorStepListener) mSensorListener).addListener(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorStepCounter, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (task != null) task.cancel(false);
    }

    @Override
    public String countStep(Integer count) {
        String result = String.valueOf(count);
        mTVCounter.setText(result);

        try {
            //TODO do zmiany
            SumStepsDaoService.getInstance().updateSumStep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateWeatherView(WeatherData wd) {

        city.setText(wd.name + "," + wd.sys.country);
        description.setText(wd.weather.get(0).description);
        temperature.setText(String.format(Locale.US,"%.2f C", wd.main.temp));
        pressure.setText(String.format(Locale.US,"%.2f ", wd.main.pressure));
        humidity.setText(String.format(Locale.US,"%d %%", wd.main.humidity));


        Picasso.with(MainActivity.this)
                .load(String.format(
                        Locale.US,
                        WeatherService.IMG_URL,
                        wd.weather.get(0).icon))
                .into(imageView);
    }


//    private class WeatherTask extends AsyncTask<Map<String, Float>, Void, WeatherData> {
//        WeatherData result = null;
//
//        @Override
//        protected WeatherData doInBackground(Map<String, Float>... params) {
//            Map<String, Float> coord = params[0];
//            Call<WeatherData> call = WeatherService.getActuallWeatherData(
//                    coord.get("lon"), coord.get("lat")
//            );
//            call.enqueue(new Callback<WeatherData>() {
//                @Override
//                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
//                    result = response.body();
//                    SumStep actuallStep = null;
//                    try {
//                        actuallStep = SumStepsDaoService.getInstance().setActuallWeather(result);
//                        //TODO do zmiany
//                        ((SensorStepListener)mSensorListener).setCounter(actuallStep.getSteps());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    updateView(response.body(), String.valueOf(actuallStep.getSteps()));
//                }
//
//                @Override
//                public void onFailure(Call<WeatherData> call, Throwable t) {
//
//                }
//            });
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(WeatherData weatherData) {
//            super.onPostExecute(weatherData);
//            if (weatherData == null) return;
//            //updateView(weatherData);
//
//        }
//    }

}

//TODO Zapisać tekst licznika do Bundle