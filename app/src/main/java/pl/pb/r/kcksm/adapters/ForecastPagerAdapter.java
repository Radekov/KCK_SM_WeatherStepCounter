package pl.pb.r.kcksm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import pl.pb.r.kcksm.Constans;
import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.weather.Day;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class ForecastPagerAdapter extends PagerAdapter {

    private Typeface weatherFont;

    List<Day> dayWeathers;
    private Context mContext;

    //private TextView mDay;
    private TextView mPressure;
    private TextView mHumidity;
    private TextView mDescription;
    private TextView mIcon;
    private TextView mWindSpeed;
    private TextView mWindDeg;
    private TextView mClouds;

    //FIXME
    private TextView mTempMor;
//    private TextView mIconMor;
    private TextView mTempDay;
//    private TextView mIconDay;
    private TextView mTempEve;
//    private TextView mIconEve;
    private TextView mTempNig;
//    private TextView mIconNig;

    public ForecastPagerAdapter(List<Day> dayWeathers, Context context) {
        super();
        this.dayWeathers = dayWeathers;
        this.mContext = context;
        this.weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weathericons.ttf");
    }

    @Override
    public int getCount() {
        return dayWeathers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o==view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.row_weather_city, container, false);
        setUpViews(layout);
        fillViews(dayWeathers.get(position));
        container.addView(layout);
        return layout;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return setTime(dayWeathers.get(position).dt);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void setUpViews(ViewGroup v){
        //mDay = (TextView)v.findViewById(R.id.forecastDay);
        mPressure = (TextView)v.findViewById(R.id.forecastPressure);
        mHumidity = (TextView)v.findViewById(R.id.forecastHumidity);
        mDescription = (TextView)v.findViewById(R.id.forecastDescription);
        mIcon = (TextView)v.findViewById(R.id.forecastIcon);
        mWindSpeed = (TextView)v.findViewById(R.id.forecastWindSpeed);
        mWindDeg = (TextView)v.findViewById(R.id.forecastWindDeg);
        mClouds = (TextView)v.findViewById(R.id.forecastClouds);

        mTempMor = (TextView)v.findViewById(R.id.forecastTempMor);
//        mIconMor = (TextView)v.findViewById(R.id.forecastIconMor);
        mTempDay = (TextView)v.findViewById(R.id.forecastTempDay);
//        mIconDay = (TextView)v.findViewById(R.id.forecastIconDay);
        mTempEve = (TextView)v.findViewById(R.id.forecastTempEve);
//        mIconEve = (TextView)v.findViewById(R.id.forecastIconEve);
        mTempNig = (TextView)v.findViewById(R.id.forecastTempNig);
//        mIconNig = (TextView)v.findViewById(R.id.forecastIconNig);
    }

    private void fillViews(Day day){
        mPressure.setText(day.pressure + Constans.PRESSURE);
        mHumidity.setText(day.humidity + Constans.HUMIDITY);
        mDescription.setText(day.weather.get(0).description);

        mIcon.setText(Constans.getWeatherIco(day.weather.get(0).id));
        Log.d("Ico:",day.weather.get(0).id+"");
        mWindSpeed.setText(day.speed +Constans.WIND_SPEED);
        mWindDeg.setRotation(day.deg*1.0f);
        mClouds.setText(day.clouds+"%");

        mTempMor.setText(day.temp.morn+Constans.DEGGRE_C);
        mTempDay.setText(day.temp.day+Constans.DEGGRE_C);
        mTempEve.setText(day.temp.eve+Constans.DEGGRE_C);
        mTempNig.setText(day.temp.night+Constans.DEGGRE_C);
    }

    private String setTime(long time){
        Calendar calendar = new GregorianCalendar(new Locale("pl","PL"));
        calendar.setTimeInMillis(time*1000L);
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("E MMMM dd.MM.yyyy", new Locale("pl","PL"));
        return sdf.format(calendar.getTimeInMillis());
    }
}
