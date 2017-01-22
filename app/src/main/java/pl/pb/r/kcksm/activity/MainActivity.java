package pl.pb.r.kcksm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import pl.pb.r.kcksm.App;
import pl.pb.r.kcksm.Constans;
import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.WeatherData;
import pl.pb.r.kcksm.services.GPSLocationService;
import pl.pb.r.kcksm.services.StepCounterService;
import pl.pb.r.kcksm.services.WeatherService;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_STEP = "EXTRA_STEP";
    public static final String ACTION_UPDATE_STEP = "ACTION_UPDATE_STEP";

    private TextView mTVCounter;
    private TextView city;
    private TextView temperature;
    private TextView pressure;
    private TextView humidity;
    private TextView description;
    private TextView imgDescr;

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setUpViews();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mStepReceiver, new IntentFilter(ACTION_UPDATE_STEP));
    }

    protected void setUpViews() {
        mTVCounter = (TextView) findViewById(R.id.tv_counter);
        city = (TextView) findViewById(R.id.tv_city);
        temperature = (TextView) findViewById(R.id.tv_temperature);
        pressure = (TextView) findViewById(R.id.tv_pressure);
        humidity = (TextView) findViewById(R.id.tv_humidity);
        description = (TextView) findViewById(R.id.tv_description);
        imgDescr = (TextView) findViewById(R.id.imgDescr);
        fillViews();
    }
    protected void fillViews(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        temperature.setText(sharedPref.getString(getString(R.string.saved_last_temp), getString(R.string.default_temperature)));
        city.setText(sharedPref.getString(getString(R.string.saved_last_city), getString(R.string.default_city)));
        imgDescr.setText(sharedPref.getString(getString(R.string.saved_last_ico), getString(R.string.default_ico)));
        description.setText(sharedPref.getString(getString(R.string.saved_last_description), getString(R.string.default_description)));
        pressure.setText(sharedPref.getString(getString(R.string.saved_last_pressure), getString(R.string.default_pressure)));
        humidity.setText(sharedPref.getString(getString(R.string.saved_last_humidity), getString(R.string.default_humidity)));
        mTVCounter.setText(sharedPref.getString(getString(R.string.saved_last_steps), getString(R.string.default_steps)));
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
                StepCounterService.updateTable();
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


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        saveLastWeather();
    }

    protected void saveLastWeather(){
        Log.d("Main ico weather", imgDescr.getText().toString());
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_last_temp), temperature.getText().toString());
        editor.putString(getString(R.string.saved_last_city), city.getText().toString());
        editor.putString(getString(R.string.saved_last_ico), imgDescr.getText().toString());
        editor.putString(getString(R.string.saved_last_description),description.getText().toString());
        editor.putString(getString(R.string.saved_last_pressure),pressure.getText().toString());
        editor.putString(getString(R.string.saved_last_humidity),humidity.getText().toString());
        editor.putString(getString(R.string.saved_last_steps),mTVCounter.getText().toString());
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mStepReceiver);
        ((App)getApplication()).stopService(GPSLocationService.class);
        ((App)getApplication()).stopService(StepCounterService.class);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateWeatherView(WeatherData wd) {
        Log.d("Update weather:",wd.id.toString() + " " + wd.weather.get(0).id);
        city.setText(wd.name + "," + wd.sys.country);
        description.setText(wd.weather.get(0).description);
        temperature.setText(String.format(Locale.US, "%.2f " + Constans.DEGGRE_C, wd.main.temp));
        pressure.setText(String.format(Locale.US, "%.2f " + Constans.PRESSURE, wd.main.pressure));
        humidity.setText(String.format(Locale.US, "%d" + Constans.HUMIDITY, wd.main.humidity));

        imgDescr.setText(Constans.getWeatherIco(wd.weather.get(0).id));
//        Picasso.with(MainActivity.this)
//                .load(String.format(
//                        Locale.US,
//                        WeatherService.IMG_URL,
//                        wd.weather.get(0).icon))
//                .into(imgDescr);
    }

    private BroadcastReceiver mStepReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BroadcastReceiver","onReceive: "+ intent.getAction());
            switch (intent.getAction()) {
                case ACTION_UPDATE_STEP:
                    mTVCounter.setText(intent.getStringExtra(EXTRA_STEP));
            }
        }
    };
}