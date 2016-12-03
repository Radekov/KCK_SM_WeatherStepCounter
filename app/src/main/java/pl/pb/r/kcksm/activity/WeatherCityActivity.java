package pl.pb.r.kcksm.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.ForecastData;
import pl.pb.r.kcksm.services.WeatherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherCityActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private ForecastDataTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_city);
        setUpViews();

        mToolbar = (Toolbar) findViewById(R.id.forecastToolbar);

        task = new ForecastDataTask();
        task.execute("Grajewo");
    }

    private void setUpViews() {
        mViewPager = (ViewPager) findViewById(R.id.forecastViewPager);
        mRecyclerView = (RecyclerView) findViewById(R.id.forecastRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_forecast, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onDestroy() {
        if (task == null)
            task.cancel(false);
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
                    for (ForecastData.List list : response.body().list) {
                        Log.d("weather:", list.weather.get(0).id + " " + list.weather.get(0).description
                                + " at time: " + list.dtTxt);
                    }
                    Log.d("All size:", "" + response.body().list.size());

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
