package br.com.consultai.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by leonardo.ribeiro on 10/01/2018.
 */

public class CalcHora {
    public static Date horaLogout, horaLogin;
    static long dtf;

    public static long dtfs;

    public static void inicioTempo() {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("hh");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        horaLogout = data_atual;
        String hora_atual = dateFormat_hora.format(data_atual);


//        Log.i("data_atual", data_atual.toString());
        Log.i("hora_logout", hora_atual); // Esse é o que você quer

    }
}
