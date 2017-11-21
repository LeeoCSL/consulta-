package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Date;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.model.Rotina;
import br.com.consultai.model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 21/11/2017.
 */

public class PostRotinaRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;


public PostRotinaRequest(Context context){
            this.context = context;
        }


    protected String doInBackground(String... strings) {

        String id_usuario = strings[0];
        String hora = strings[1];

        String weekday = strings[3];
        String tipo = strings[4];

        Rotina rotina = new Rotina();
        rotina.setId_usuario(id_usuario);
        rotina.setHora(hora);
        rotina.setValor(strings[2]);
        rotina.setWeekday(strings[2]);
        rotina.setTipo(tipo);

        String url = "https://consultai.000webhostapp.com/nova_rotina";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null){
            Log.i("recebi", s);



        }
    }

    @Override
    protected void onPreExecute() {

    }
}