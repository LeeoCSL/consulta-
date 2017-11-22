package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static br.com.consultai.Fragments.MainFragment.recarga;
import static br.com.consultai.Fragments.MainFragment.saldoGet;
import static br.com.consultai.Fragments.MainFragment.saldoPost;
import static br.com.consultai.Fragments.MainFragment.txtVlr;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class GetSaldoRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;
    String tipoGet;

    public static String sd;

    public GetSaldoRequest(Context context) {
        this.context = context;
    }


    protected String doInBackground(String... strings) {


        String userID = strings[0];
        tipoGet = strings[1];

        String url = "https://consultai.000webhostapp.com/user_saldo?id=" + userID + "&login_token=" + LoginActivity.LOGIN_TOKEN;

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
        try {
            if (tipoGet.equals("0")) {
                JSONObject jsonObject = new JSONObject(s);


                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));
                MainFragment.tvSaldo.setText("R$ " + saldo);
                MainFragment.dialog.dismiss();
            } else if (tipoGet.equals("1")) {
                JSONObject jsonObject = new JSONObject(s);


                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + recarga;


                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }
            //viagem extra 3,8
            else if (tipoGet.equals("2")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet - 3.8f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }
//viagem extra 3.0
            else if (tipoGet.equals("3")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet - 3.0f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }
//viagem extra 1,9
            else if (tipoGet.equals("4")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet - 1.9f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }

            //viagem a menos 3,8
            else if (tipoGet.equals("5")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 3.8f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }
//viagem menos 3.0
            else if (tipoGet.equals("6")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 3.0f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }
//viagem menos 1,9
            else if (tipoGet.equals("7")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 1.9f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                MainFragment.metodoPost();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {

    }
}