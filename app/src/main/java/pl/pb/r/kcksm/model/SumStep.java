package pl.pb.r.kcksm.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Radosław Naruszewicz on 2016-11-27.
 */

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
    @Unique
    private String ico;

    @NotNull
    private Integer steps;

    public SumStep() {
    }

    @Generated(hash = 484290474)
    public SumStep(Long id, @NotNull String weather, @NotNull String ico,
            @NotNull Integer steps) {
        this.id = id;
        this.weather = weather;
        this.ico = ico;
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

    public String getIco() {
        return this.ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
}
