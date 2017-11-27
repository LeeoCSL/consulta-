package br.com.consultai.utils;
import android.util.Log;

import java.util.Date;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leonardo.ribeiro on 24/11/2017.
 */

public class UtilTempoDigitacao {
  public static Date dt1, dt2;
   static long dtf;

    public static long dtfs;

    public static void inicioTempo() {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("mm:ss");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        dt1 = data_atual;
        String hora_atual = dateFormat_hora.format(data_atual);


        Log.i("data_atual", data_atual.toString());
        Log.i("hora_atual", hora_atual); // Esse é o que você quer

    }

    public static void fimTempo() {
        SimpleDateFormat dateFormat_hora2 = new SimpleDateFormat("mm:ss");
        Date data2 = new Date();

        Calendar cal2 = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        cal2.setTime(data2);
        Date data_atual2 = cal2.getTime();


        dt2 = data_atual2;

        calendar.setTimeInMillis(dt2.getTime() - dt1.getTime());

        dtf = calendar.getTimeInMillis();

        dtfs = (dtf / 1000) % 60;

        String hora_atual2 = dateFormat_hora2.format(data_atual2);


        Log.i("data_atual", data_atual2.toString());
        Log.i("hora_atual", hora_atual2); // Esse é o que você quer
        Log.i("conta", String.valueOf(dtfs)); // Esse é o que você quer



    }
}

