package pl.pb.r.kcksm.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import pl.pb.r.kcksm.model.DayForecastModel;
import pl.pb.r.kcksm.model.ForecastData;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class ForecastPagerAdapter extends PagerAdapter {

    List<DayForecastModel> dayWeathers;

    public ForecastPagerAdapter(List<ForecastData.List> dayWeathers) {
        super();
        this.dayWeathers = new ArrayList<>();
        //dayWeathers.get(0).dtTxt;

        Date parsed = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parsed = format.parse(dayWeathers.get(0).dtTxt);
            calendar.setTime(parsed);
        } catch (ParseException pe) {
            throw new IllegalArgumentException();
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        int firstDay = 8 - hour / 3;
        DayForecastModel model = new DayForecastModel();

        //FIXME Czy może lepiej sprawdzać po godzinie -- kolejny problem narzut tego długiego stringa na godzinę
        //TODO znaleźć inne rozwiązanie tego poniżej
        //this.dayWeather.size() - count ViewPager
        //this.dayWeather.get(x).size() - count row in RecyclerView
        for(int i = 0; i<firstDay;i++)
            model.weathers.add(dayWeathers.get(i));
        this.dayWeathers.add(model);
        model = new DayForecastModel();
        for(int i = firstDay; i<dayWeathers.size();i++){
            if(model.weathers.size() == 8){
                this.dayWeathers.add(model);
                model = new DayForecastModel();
            }
            model.weathers.add(dayWeathers.get(i));
        }
        this.dayWeathers.add(model);
    }

    @Override
    public int getCount() {
        return dayWeathers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
