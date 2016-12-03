package pl.pb.r.kcksm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Naruszewicz on 2016-12-03.
 */

public class DayForecastModel {
    public List<ForecastData.List> weathers;

    public DayForecastModel() {
        weathers = new ArrayList<>();
    }
}
