package pl.pb.r.kcksm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Radosław Naruszewicz on 2016-11-26.
 * https://github.com/survivingwithandroid/Swa-app/tree/master/WeatherApp/src/com/survivingwithandroid/weatherapp
 */

public class Weather implements Parcelable {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();

    public class CurrentCondition {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;

        private float pressure;
        private float humidity;

        public int getWeatherId() {
            return weatherId;
        }

        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }


    }

    public class Temperature {
        private float temp;
        private float minTemp;
        private float maxTemp;

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        public float getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    public class Wind {
        private float speed;
        private float deg;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDeg() {
            return deg;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }


    }

    public class Rain {
        private String time;
        private float ammount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmmount() {
            return ammount;
        }

        public void setAmmount(float ammount) {
            this.ammount = ammount;
        }


    }

    public class Snow {
        private String time;
        private float ammount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmmount() {
            return ammount;
        }

        public void setAmmount(float ammount) {
            this.ammount = ammount;
        }


    }

    public class Clouds {
        private int perc;

        public int getPerc() {
            return perc;
        }

        public void setPerc(int perc) {
            this.perc = perc;
        }


    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        /*Location*/
        location.setLongitude(in.readFloat());
        location.setLatitude(in.readFloat());

        location.setCountry(in.readString());
        location.setCity(in.readString());

        location.setSunrise(in.readInt());
        location.setSunset(in.readInt());
        /*CurrentCondition*/
        currentCondition.setPressure(in.readFloat());
        currentCondition.setHumidity(in.readFloat());

        currentCondition.setCondition(in.readString());
        currentCondition.setDescr(in.readString());
        currentCondition.setIcon(in.readString());

        currentCondition.setWeatherId(in.readInt());
        /*Temperature*/
        temperature.setTemp(in.readFloat());
        temperature.setMinTemp(in.readFloat());
        temperature.setMaxTemp(in.readFloat());

        /*Wind*/
        wind.setSpeed(in.readFloat());
        wind.setDeg(in.readFloat());

        /*Rain*/
        rain.setAmmount(in.readFloat());
        rain.setTime(in.readString());

        /*Snow*/
        snow.setAmmount(in.readFloat());
        snow.setTime(in.readString());

        /*Clouds*/
        clouds.setPerc(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*Location*/
        dest.writeFloat(location.getLongitude());
        dest.writeFloat(location.getLatitude());

        dest.writeString(location.getCountry());
        dest.writeString(location.getCity());

        dest.writeInt(location.getSunrise());
        dest.writeInt(location.getSunset());

        /*CurrentCondition*/
        dest.writeFloat(currentCondition.getPressure());
        dest.writeFloat(currentCondition.getHumidity());

        dest.writeString(currentCondition.getCondition());
        dest.writeString(currentCondition.getDescr());
        dest.writeString(currentCondition.getIcon());

        dest.writeInt(currentCondition.getWeatherId());
        /*Temperature*/
        dest.writeFloat(temperature.getTemp());
        dest.writeFloat(temperature.getMinTemp());
        dest.writeFloat(temperature.getMaxTemp());

        /*Wind*/
        dest.writeFloat(wind.getSpeed());
        dest.writeFloat(wind.getDeg());

        /*Rain*/
        dest.writeFloat(rain.getAmmount());
        dest.writeString(rain.getTime());

        /*Snow*/
        dest.writeFloat(snow.getAmmount());
        dest.writeString(snow.getTime());

        /*Clouds*/
        dest.writeInt(clouds.getPerc());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
