package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.MainActivity;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class GetSaldoRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;


    public GetSaldoRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {

        String userID = strings[0];

        String url = "https://consultai.000webhostapp.com/user_saldo?id="+userID;

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
            double saldo = Double.parseDouble(s.substring(1, s.length() - 1));

            MainFragment.tvSaldo.setText("R$ "+saldo);
            MainFragment.dialog.dismiss();
        }
    }

    @Override
    protected void onPreExecute() {

    }
}