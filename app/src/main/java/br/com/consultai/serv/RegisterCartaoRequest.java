package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Cartao;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class RegisterCartaoRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;


    public RegisterCartaoRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {

        String cartaoNumero = strings[0];
        String cartaoApelido = strings[1];



        Cartao cartao = new Cartao();
        cartao.setNumero(cartaoNumero);
        cartao.setApelido(cartaoApelido);
        cartao.setSaldo(Float.parseFloat(strings[2]));
        cartao.setEstudante(Boolean.parseBoolean(strings[3]));


        Gson gson2 = new Gson();

        OkHttpClient client2 = new OkHttpClient();

        String url2 = "https://consultai.000webhostapp.com/register";

        Request.Builder builder2 = new Request.Builder();

        builder2.url(url2);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body2 = RequestBody.create(mediaType2, gson2.toJson(cartao));

        Log.i("JSON2", gson2.toJson(cartao));

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