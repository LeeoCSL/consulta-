package br.com.consultai;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by leonardo.ribeiro on 05/12/2017.
 */

public class Giroscopio extends AsyncTask<String, String, String> implements SensorEventListener {

    String xText, yText, zText;
    Sensor mySensor;
    SensorManager SM;

    public static String gyro = "teste";

    Context mContext;


    public Giroscopio(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... Strings) {

        SM = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor

        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register sensor listener
        SM.registerListener(this, mySensor, 999999999);

        return gyro;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        xText = ("X: " + (int) sensorEvent.values[0]);
        yText = ("Y: " + (int) sensorEvent.values[1]);
        zText = ("Z: " + (int) sensorEvent.values[2]);

        gyro = "giroscopio: " + xText + " " + yText+ " "+ zText;



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}