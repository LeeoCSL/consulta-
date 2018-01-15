package br.com.consultai.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by leonardo.ribeiro on 10/01/2018.
 */

public class CalcHora {
    public static Date horaAtual;
    static long dtf;

    public static long dtfs;

    public static void inicioTempo() {
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("hh:mm:ss");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        horaAtual = data_atual;
        String hora_atual = dateFormat_hora.format(data_atual);

//        Toast.makeText(getApplicationContext(),hora_atual , Toast.LENGTH_SHORT).show();
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt("horaLogout", Integer.valueOf(hora_atual));
//        editor.commit();

//        Log.i("data_atual", data_atual.toString());
//        Log.i("hora_logout", hora_atual + "h"); // Esse é o que você quer

    }

//    public static void fimTempo() {
//        SimpleDateFormat dateFormat_hora2 = new SimpleDateFormat("hh:mm");
//        Date data2 = new Date();
//
//        Calendar cal2 = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        cal2.setTime(data2);
//        Date data_atual2 = cal2.getTime();
//        String hora_atual2 = dateFormat_hora2.format(data_atual2);
//
//        horaLogin = data_atual2;
//
//        Log.v("hora_login", hora_atual2);
//
////        dtfs = horaLogin - horaLogout;
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int hrOut = sharedPref.getInt("hora_atual", 0);
//        calendar.setTimeInMillis(horaLogin.getTime() - hrOut);
////
//        dtf = calendar.getTimeInMillis();
//
////        dtfs = (dtf / 1000) % 60;
////
////86400000
////
////
////        Log.i("data_atual", data_atual2.toString());
////        Log.i("hora_atual", hora_atual2); // Esse é o que você quer
//        Log.i("conta", String.valueOf(dtf)); // Esse é o que você quer
//
//
//
//    }
}
