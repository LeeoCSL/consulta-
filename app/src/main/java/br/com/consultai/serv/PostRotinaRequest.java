package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Rotina;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        String valor = strings[2];
        String weekday = strings[3];
        String tipo = strings[4];


        Rotina rotina = new Rotina();
        rotina.setId_usuario(id_usuario);
        rotina.setHora(hora);
        rotina.setValor(valor);
        rotina.setWeekday(weekday);
        rotina.setTipo(tipo);

        Gson gson2 = new Gson();

        OkHttpClient client2 = new OkHttpClient();

        String url2 = "https://consultai.000webhostapp.com/nova_rotina";

        Request.Builder builder2 = new Request.Builder();

        builder2.url(url2);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body2 = RequestBody.create(mediaType2, gson2.toJson(rotina));

        Log.i("JSON2", gson2.toJson(rotina));

        builder2.post(body2);

        Request request2 = builder2.build();

        try {
            Response response = client2.newCall(request2).execute();
            Log.i("resp_server", response.body().string());
            return response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //if -1 > volta registro

    }

    @Override
    protected void onPreExecute() {



    }
}