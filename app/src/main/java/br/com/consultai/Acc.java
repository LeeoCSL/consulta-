package br.com.consultai;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by leonardo.ribeiro on 20/12/2017.
 */

public class Acc extends AsyncTask<String, String, String> implements SensorEventListener {

    public static String xText, yText, zText, aText;
    Sensor mySensor, mySensorGyro;
    SensorManager SM, SMG;

    public static String Acc = "teste";

    Context mContext;
    public Acc(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... Strings) {

        SM = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
        //Accelerometer Sensor

        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        //Register sensor listener
        SM.registerListener(this, mySensor, 999999999);



        return Acc;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {



        xText = String.valueOf(sensorEvent.values[0]);
        yText = String.valueOf(sensorEvent.values[1]);
        zText = String.valueOf(sensorEvent.values[2]);

        Acc =xText + " " + yText+ " "+ zText;



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
