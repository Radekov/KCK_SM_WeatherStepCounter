package pl.pb.r.kcksm.activity;

import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.List;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.adapters.ForecastPagerAdapter;
import pl.pb.r.kcksm.model.ForecastData;
import pl.pb.r.kcksm.model.weather.Day;
import pl.pb.r.kcksm.services.WeatherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherCityActivity extends AppCompatActivity {

    public static final String EXTRA_CITY = "EXTRA_CITY";

    protected Toolbar mToolbar;
    private ViewPager mViewPager;
    private List<AsyncTask> tasks = new ArrayList<>();
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

    private void searchForecastCity(String city) {
        ForecastDataTask task = new ForecastDataTask();
        task.execute(city);
        tasks.add(task);
    }

    public void searchForecast(View v) {
        String city = mEditText.getText().toString();
        Log.d("searchForecast",city);
        if(city.length() == 0) return;
        searchForecastCity(city);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_forecast, menu);

//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
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
    protected void onDestroy() {
        for (AsyncTask task :
                tasks) {
            task.cancel(false);
        }
        super.onDestroy();
    }

    private class ForecastDataTask extends AsyncTask<String, Void, ForecastData> {

        @Override
        protected ForecastData doInBackground(String... strings) {
            String city = strings[0];

            Call<ForecastData> call = WeatherService.getForecastData(city);
            Log.d("URL", call.request().url().toString());
            call.enqueue(new Callback<ForecastData>() {
                @Override
                public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                    for (Day list : response.body().days) {
                        Log.d("weather:", list.weather.get(0).id + " " + list.weather.get(0).description
                                + " at time: " + list.dt);
                    }
                    Log.d("All size:", "" + response.body().days.size());
                    mCity.setText(response.body().city.name + ", " + response.body().city.country);
                    mViewPager.setAdapter(new ForecastPagerAdapter(response.body().days, getApplicationContext()));

                }

                @Override
                public void onFailure(Call<ForecastData> call, Throwable t) {
                    Log.w("onFailure", "Nie udało się\n");
                    t.printStackTrace();
                }
            });

            return null;
        }
    }
}
