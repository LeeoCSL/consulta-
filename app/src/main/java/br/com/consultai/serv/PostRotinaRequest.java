package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAnalytics mFirebaseAnalytics;
    String tp;


    public PostRotinaRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        String id_usuario = strings[0];
        String hora = strings[1];
        String valor = strings[2];
        String weekday = strings[3];
        String tipo = strings[4];

        tp = tipo;

        Rotina rotina = new Rotina();

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
        if(tp == "0") {

            Bundle bundle = new Bundle();
            bundle.putString("tipo_rotina", "ida");
            bundle.putString("acelerometro_x", null);
            bundle.putString("acelerometro_y", null);
            bundle.putString("acelerometro_z", null);
            bundle.putString("velocidade_digitacao", null);
            bundle.putString("velocidade_clique", null);
            bundle.putString("posicao_clique", null);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_erro", bundle);
        }

        if(tp == "1") {

            Bundle bundle = new Bundle();
            bundle.putString("tipo_rotina", "volta");
            bundle.putString("acelerometro_x", null);
            bundle.putString("acelerometro_y", null);
            bundle.putString("acelerometro_z", null);
            bundle.putString("velocidade_digitacao", null);
            bundle.putString("velocidade_clique", null);
            bundle.putString("posicao_clique", null);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_erro", bundle);
        }

    }

    @Override
    protected void onPreExecute() {



    }
}