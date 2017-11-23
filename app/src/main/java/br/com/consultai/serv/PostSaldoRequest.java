package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class PostSaldoRequest extends AsyncTask<String, Void, String>  {
    private Context context;
    private AlertDialog.Builder dialog;


    public PostSaldoRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {


        String userID3 = strings[0];
        String userSaldo = strings[1];

        User user3 = new User();
        user3.setId(userID3);

        user3.setUser_saldo(userSaldo);


        Gson gson3 = new Gson();

        OkHttpClient client3 = new OkHttpClient();

        String url3 = "https://consultai.000webhostapp.com/update_user";

        Request.Builder builder3 = new Request.Builder();

        builder3.url(url3);

        MediaType mediaType3 =
                MediaType.parse("application/json; charset=utf-8");


        RequestBody body3 = RequestBody.create(mediaType3, gson3.toJson(user3));
        builder3.post(body3);

        Request request3 = builder3.build();

        try {
            Response response = client3.newCall(request3).execute();
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
    }

    @Override
    protected void onPreExecute() {

    }
}
