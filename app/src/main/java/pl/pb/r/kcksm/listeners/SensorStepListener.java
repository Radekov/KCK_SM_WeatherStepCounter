package pl.pb.r.kcksm.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Naruszewicz on 2016-11-25.
 */

public class SensorStepListener implements SensorEventListener {
    private List<CountStepListener> listeners = new ArrayList<CountStepListener>();
    private float counter = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        counter += event.values[0];
        for(CountStepListener l : listeners){
            l.countStep((int)counter);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void addListener(CountStepListener toAdd){
        listeners.add(toAdd);
    }

    public float getCounter() {
        return counter;
    }
}
