package pl.pb.r.kcksm.services;

import pl.pb.r.kcksm.App;
import pl.pb.r.kcksm.model.SumStep;
import pl.pb.r.kcksm.model.SumStepDao;
import pl.pb.r.kcksm.model.WeatherData;
import pl.pb.r.kcksm.model.weather.Weather;

/**
 * Created by Radosław Naruszewicz on 2016-11-28.
 */

public class SumStepsDaoService {

    private SumStepDao sumStepDao;
    private static SumStepsDaoService INSTANCE;
    private Weather actuallWeather = null;

    private SumStepsDaoService(App app) {
        sumStepDao = app.getDaoSession().getSumStepDao();
    }

    public static SumStepsDaoService newInstance(App app) {
        INSTANCE = new SumStepsDaoService(app);
        return INSTANCE;
    }

    public static SumStepsDaoService getInstance() {
        if (INSTANCE == null)
            return null;
        return INSTANCE;
    }

    public String sumAllSteps() {
        Integer result = 0;
        for (SumStep s : sumStepDao.loadAll())
            result += s.getSteps();
        //FIXED
//        Query query = sumStepDao.queryBuilder().where(
//                new WhereCondition.StringCondition(
//                        "(SELECT SUM(steps) FROM SUM_STEP)")
//        ).build();
        return String.valueOf(result);
    }

    public SumStep setActuallWeather(WeatherData wd) throws Exception {
        if (wd == null)
            throw new Exception();
        return findAndUpdateSumStep(wd.weather.get(0), 0);
    }

    public SumStep updateSumStep(Integer countSteps) {
        return findAndUpdateSumStep(actuallWeather, countSteps);
    }

    public SumStep findAndUpdateSumStep(Weather w, Integer countSteps) {
        actuallWeather = w;
        if(actuallWeather == null) return null;
        Long id = new Long(w.id);
        String description = actuallWeather.description;
        SumStep result = null;
        try {
            result = sumStepDao.queryBuilder()
                    .where(SumStepDao.Properties.Id.eq(id))
                    .unique();
        } catch (Exception ex) {
        }
        if (result == null) {
            result = new SumStep();
            result.setId(id);
            result.setWeather(description);
            result.setSteps(countSteps);
            sumStepDao.insert(result);
        } else {
            result.setSteps(result.getSteps() + countSteps);
            sumStepDao.update(result);
        }
        return result;
    }

    public void deleteAll(){
        sumStepDao.deleteAll();
    }
}
