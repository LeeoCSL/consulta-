package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
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
    String cartaoApelido;

    public RegisterCartaoRequest(Context context){
        this.context = context;
    }

    protected String doInBackground(String... strings) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        String id = strings[0];
        String token = strings[1];
        String cartaoNumero = strings[2];
        cartaoApelido = strings[3];

        Cartao cartao = new Cartao();
        cartao.setIdUsuario(id);
        cartao.setLoginToken(token);
        cartao.setNumeroCartao(cartaoNumero);
        cartao.setApelido(cartaoApelido);
        cartao.setSaldo(Float.parseFloat(strings[4]));

        if(Boolean.parseBoolean(strings[5])){
            cartao.setEstudante(1);
        }else{
            cartao.setEstudante(0);
        }

        Gson gson2 = new Gson();

        OkHttpClient client2 = new OkHttpClient();

        String url2 = "https://zazzytec.com.br/novo_cartao";

        Request.Builder builder2 = new Request.Builder();

        builder2.url(url2);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body2 = RequestBody.create(mediaType2, gson2.toJson(cartao));

        builder2.post(body2);

        Request request2 = builder2.build();

        try {
            Response response = client2.newCall(request2).execute();
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
            double saldo = jsonObject.getDouble("saldo");
            String apelido = jsonObject.getString("apelido");

            LoginActivity.LOGIN_TOKEN = loginToken;

            MainFragment.SALDO = saldo;
            //MainFragment.txtNomeBilhete.setText(apelido);

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
            bundle.putDouble("saldo", saldo);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);

            CadastroCartaoActivity.mDialog.dismiss();

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            Log.i("errox", e.toString());
        }
    }

    @Override
    protected void onPreExecute() {

    }
}