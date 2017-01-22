package pl.pb.r.kcksm.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import pl.pb.r.kcksm.App;
import pl.pb.r.kcksm.activity.MainActivity;
import pl.pb.r.kcksm.listeners.CountStepListener;
import pl.pb.r.kcksm.listeners.SensorStepListener;
import pl.pb.r.kcksm.model.DaoSession;
import pl.pb.r.kcksm.model.SumStep;
import pl.pb.r.kcksm.model.WeatherData;
import pl.pb.r.kcksm.model.weather.Weather;

/*https://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-stepcounter*/
public class StepCounterService extends Service implements CountStepListener {

    private SensorManager mSensorManager;
    private Sensor mSensorStepCounter;
    private SensorEventListener mSensorListener;
    private Integer mStepsAtBegging = 0;
    private static Integer mStepsAtWeather = 0;
    private Weather weather = null;

    private static SumStepsDaoService sumStepsDaoService;

    public StepCounterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sumStepsDaoService = SumStepsDaoService.getInstance();
        createmSensorListener();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(mSensorListener, mSensorStepCounter, SensorManager.SENSOR_DELAY_UI);
        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("SteoCounterService","onBind()");
        return null;
    }

    @Override
    public String countStep(Integer count) {
        String result = String.valueOf(count);
        mStepsAtWeather++;
        mStepsAtBegging++;
        try {
//            sumStepsDaoService.updateSumStep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateStepsInActivity(result);
        return result;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void updateWeather(WeatherData wd) {
        if (weather == null) {
            weather = wd.weather.get(0);
            SumStep sumStep = sumStepsDaoService.findAndUpdateSumStep(weather, mStepsAtWeather);
            ((SensorStepListener) mSensorListener).setCounter(sumStep.getSteps());
            updateStepsInActivity(String.format(Locale.US,"%d",((SensorStepListener) mSensorListener).getCounter()));
            return;
        }
        if (weather.description.equals(wd.weather.get(0).description))
            return;

        SumStep sumStep = sumStepsDaoService.updateSumStep(mStepsAtWeather);
        mStepsAtWeather = 0;
        weather = wd.weather.get(0);
        sumStepsDaoService.findAndUpdateSumStep(weather, 0);
        ((SensorStepListener) mSensorListener).setCounter(sumStep.getSteps());
        updateStepsInActivity(String.format(Locale.US,"%d",((SensorStepListener) mSensorListener).getCounter()));
    }

    public static SumStep updateTable(){
        SumStep sumStep = sumStepsDaoService.updateSumStep(mStepsAtWeather);
        mStepsAtWeather = 0;
        return sumStep;
    }

    private void updateStepsInActivity(String result){
        Intent it = new Intent(MainActivity.ACTION_UPDATE_STEP);
        it.putExtra(MainActivity.EXTRA_STEP, result);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(it);
    }

    private void createmSensorListener() {
        if (mSensorListener == null) {
            mSensorListener = new SensorStepListener();
            ((SensorStepListener) mSensorListener).addListener(this);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("StepCounterService","onDestroy()");
        super.onDestroy();
        if (mSensorListener != null) {
            ((SensorStepListener) mSensorListener).removeListener(this);
        }
        EventBus.getDefault().unregister(this);
        sumStepsDaoService.updateSumStep(mStepsAtWeather);
        mSensorManager.unregisterListener(mSensorListener);
    }
}
