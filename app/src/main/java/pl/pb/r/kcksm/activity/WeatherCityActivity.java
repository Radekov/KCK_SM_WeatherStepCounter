package pl.pb.r.kcksm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.adapters.ForecastPagerAdapter;
import pl.pb.r.kcksm.model.ForecastData;
import pl.pb.r.kcksm.model.weather.Day;
import pl.pb.r.kcksm.services.WeatherService;

public class WeatherCityActivity extends AppCompatActivity {

    public static final String EXTRA_CITY = "EXTRA_CITY";

    protected Toolbar mToolbar;
    private ViewPager mViewPager;
    private TextView mCity;

    //TMP
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);
        setToolbar();
        setUpViews();

        mToolbar = (Toolbar) findViewById(R.id.forecastToolbar);
        searchForecastCity(getIntent().getStringExtra(EXTRA_CITY));
    }

    private void setUpViews() {
        mCity = (TextView) findViewById(R.id.forecastCity);
        mViewPager = (ViewPager) findViewById(R.id.forecastViewPager);
        mEditText = (EditText) findViewById(R.id.searchForecastCity);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.forecastToolbar);
        setSupportActionBar(mToolbar);
        //Do wyświetlenia obrazka cofnięcia do poprzedniego Activity @R
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //TODO Zmienić obrazek na strzałkę @R
        mToolbar.setNavigationIcon(android.R.drawable.ic_menu_week);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
    }

    private void searchForecastCity(String city) {
        Intent i = new Intent(getApplicationContext(), WeatherService.class);
        i.setAction(WeatherService.ACTION_FORECAST_WEATHER);
        i.putExtra(WeatherService.EXTRA_CITY, city);
        startService(i);
    }

    public void searchForecast(View v) {
        String city = mEditText.getText().toString();
        Log.d("searchForecast", city);
        if (city.length() == 0) return;
        searchForecastCity(city);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateForecastView(ForecastData fd) {
        for (Day list : fd.days) {
            Log.d("weather:", list.weather.get(0).id + " " + list.weather.get(0).description
                    + " at time: " + list.dt);
        }
        Log.d("All size:", "" + fd.days.size());
        mCity.setText(fd.city.name + ", " + fd.city.country);
        mViewPager.setAdapter(new ForecastPagerAdapter(fd.days, getApplicationContext()));
    }
}
