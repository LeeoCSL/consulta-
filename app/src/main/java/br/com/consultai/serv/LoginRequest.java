package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.User;
import okhttp3.Request;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class LoginRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog.Builder dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    private boolean success = false;

    public LoginRequest(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... strings) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        String userID = strings[0];
        String userEmail = strings[1];
        String userPassword = strings[2];
        String notificationToken = strings[3];



        //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        User user = new User();
        user.setId(userID);
        user.setEmail(userEmail);
        user.setSenha(userPassword);
        user.setNotification_token(notificationToken);



        Gson gson = new Gson();

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/auth";

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");


        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(user));
        builder.post(body);

        Request request = builder.build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
//            Log.i("resp_server_login", response.body().string());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);

            String loginToken = jsonObject.getString("login_token");
            String saldo = jsonObject.getString("user_saldo");
            String estudante = jsonObject.getString("estudante");
            String apelido = jsonObject.getString("apelido");

            


            LoginActivity.LOGIN_TOKEN = loginToken;
            MainFragment.apelido_bilhete = apelido;
            MainFragment.SALDO = Double.parseDouble(saldo);

            Bundle bundle2 = new Bundle();
            bundle2.putString("acelerometro_x", null);
            bundle2.putString("acelerometro_y", null);
            bundle2.putString("acelerometro_z", null);
            bundle2.putString("velocidade_digitacao", null);
            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", null);
            bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("login_email_sucesso", bundle2);
            //TODO popular evento

            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", Double.parseDouble(saldo));
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            Bundle bundle = new Bundle();
            bundle.putString("acelerometro_x", null);
            bundle.putString("acelerometro_y", null);
            bundle.putString("acelerometro_z", null);
            bundle.putString("velocidade_digitacao", null);
            bundle.putString("velocidade_clique", null);
            bundle.putString("posicao_clique", null);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("login_email_erro", bundle);


        }
    }

    @Override
    protected void onPreExecute() {

    }
}
