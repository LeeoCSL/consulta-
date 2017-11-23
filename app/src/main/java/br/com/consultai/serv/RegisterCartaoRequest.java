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

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
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
    private FirebaseAnalytics mFirebaseAnalytics;


    public RegisterCartaoRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        String id = strings[0];
        String token = strings[1];
        String cartaoNumero = strings[2];
        String cartaoApelido = strings[3];



        Cartao cartao = new Cartao();
        cartao.setId(id);
        cartao.setToken(token);
        cartao.setNumero(cartaoNumero);
        cartao.setApelido(cartaoApelido);
        cartao.setSaldo(Float.parseFloat(strings[4]));
        cartao.setEstudante(Boolean.parseBoolean(strings[5]));


        Gson gson2 = new Gson();

        OkHttpClient client2 = new OkHttpClient();

        String url2 = "https://consultai.000webhostapp.com/novo_cartao";

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

        try {
            JSONObject jsonObject = new JSONObject(s);

            String loginToken = jsonObject.getString("login_token");
            String saldo = jsonObject.getString("user_saldo");

            LoginActivity.LOGIN_TOKEN = loginToken;

            Bundle bundle2 = new Bundle();
            bundle2.putString("acelerometro_x", null);
            bundle2.putString("acelerometro_y", null);
            bundle2.putString("acelerometro_z", null);
            bundle2.putString("velocidade_digitacao", null);
            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", null);
            bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_bilhete_sucesso", bundle2);
            //TODO popular evento

            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", Double.parseDouble(saldo));
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();




        }

    }

    @Override
    protected void onPreExecute() {



    }
}