package pl.pb.r.kcksm.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import pl.pb.r.kcksm.R;
import pl.pb.r.kcksm.model.weather.Day;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class ForecastPagerAdapter extends PagerAdapter {

    private Typeface weatherFont;

    private static String deggreC = (char) 0x00B0 + "C";

    List<Day> dayWeathers;
    private Context mContext;

    private TextView mDay;
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
        Day day = dayWeathers.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.row_weather_city, container, false);
        setUpViews(layout);
        fillViews(dayWeathers.get(position));
        container.addView(layout);
//        return super.instantiateItem(container, position);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void setUpViews(ViewGroup v){
        mDay = (TextView)v.findViewById(R.id.forecastDay);
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

    //TODO Multi language
    private void fillViews(Day day){
        mDay.setText(setTime(day.dt));
        mPressure.setText(day.pressure + "hPa");
        mHumidity.setText(day.humidity+"%");
        mDescription.setText(day.weather.get(0).description);
        //
        mIcon.setText(day.weather.get(0).icon);
        mWindSpeed.setText(day.speed +"m/s");
        mWindDeg.setText(day.deg+(char) 0x00B0+"");
        mClouds.setText(day.clouds+"%");

        mTempMor.setText(day.temp.morn+deggreC);
        mTempDay.setText(day.temp.day+deggreC);
        mTempEve.setText(day.temp.eve+deggreC);
        mTempNig.setText(day.temp.night+deggreC);
    }

    private String setTime(long time){
        Calendar calendar = new GregorianCalendar(new Locale("pl","PL"));
        calendar.setTimeInMillis(time*1000L);
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("E MMMM dd.MM.yyyy", new Locale("pl","PL"));
        return sdf.format(calendar.getTimeInMillis());
    }
}
