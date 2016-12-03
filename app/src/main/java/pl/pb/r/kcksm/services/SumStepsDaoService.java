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

    private SumStepsDaoService(App app){
        sumStepDao =  app.getDaoSession().getSumStepDao();
    }

    public static SumStepsDaoService newInstance(App app){
        INSTANCE = new SumStepsDaoService(app);
        return INSTANCE;
    }
    public static SumStepsDaoService getInstance() throws Exception{
        if(INSTANCE == null)
            throw new Exception();
        return INSTANCE;
    }

    public String sumAllSteps(){
        Integer result = 0;
        for (SumStep s:sumStepDao.loadAll())
            result+=s.getSteps();
//        Query query = sumStepDao.queryBuilder().where(
//                new WhereCondition.StringCondition(
//                        "(SELECT SUM(steps) FROM SUM_STEP)")
//        ).build();
        return String.valueOf(result);
    }

    public SumStep setActuallWeather(WeatherData wd) throws Exception {
        if(wd == null)
            throw new Exception();
        return findAndUpdateSumStep(wd.weather.get(0),0);
    }

    public SumStep updateSumStep(Integer countSteps){
        return findAndUpdateSumStep(actuallWeather,countSteps);
    }

    public SumStep findAndUpdateSumStep(Weather w, Integer countSteps){
        actuallWeather = w;
        String description = actuallWeather.description;
        String icon = actuallWeather.icon;

        SumStep result = null;

        try{
            result = sumStepDao.queryBuilder()
                    .where(
                            SumStepDao.Properties.Weather.eq(
                                    description)
                    )
                    .unique();
        }
        catch (Exception ex){
        }
        if(result == null){
            result = new SumStep();
            result.setWeather(description);
            result.setIco(icon);
            result.setSteps(countSteps);
            sumStepDao.insert(result);
        }
        else{
            result.setSteps(result.getSteps()+countSteps);
            sumStepDao.update(result);
        }
        return result;
    }

}
