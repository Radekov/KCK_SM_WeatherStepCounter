package pl.pb.r.kcksm.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Radosław Naruszewicz on 2016-11-27.
 */
//https://openweathermap.org/weather-conditions
@Entity(
        generateConstructors = true,
        generateGettersSetters = true
)
public class SumStep {
    @Id
    private Long id;

    @NotNull
    @Unique
    private String weather;

    @NotNull
    private Integer steps;

    public SumStep() {
    }

    @Generated(hash = 291230068)
    public SumStep(Long id, @NotNull String weather, @NotNull Integer steps) {
        this.id = id;
        this.weather = weather;
        this.steps = steps;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Integer getSteps() {
        return this.steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return weather + " " + steps.toString();
    }
}
