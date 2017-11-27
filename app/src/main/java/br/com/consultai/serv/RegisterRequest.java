package br.com.consultai.serv;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Usuario;
import br.com.consultai.utils.DialogFactory;
import br.com.consultai.utils.DialogUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class RegisterRequest extends AsyncTask<Usuario, Void, String> {

    private Context context;
    private AlertDialog.Builder dialog;

    private FirebaseAnalytics mFirebaseAnalytics;
    public RegisterRequest(Context context){
        this.context = context;
    }

    private ProgressDialog mDialog;

    protected String doInBackground(Usuario... usuarios) {

        Usuario usuario = usuarios[0];

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        String url = "https://zazzytec.com.br/register";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType2, gson.toJson(usuario));
        builder.post(body);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        DialogUtil.hideProgressDialog(mDialog);
        context.startActivity(new Intent(context, CadastroCartaoActivity.class));

        try{
            JSONObject jsonObject = new JSONObject(response);
//
            LoginActivity.LOGIN_TOKEN = jsonObject.getString("login_token");

//
            Log.i("logintoken", LoginActivity.LOGIN_TOKEN);
//
            Bundle bundle2 = new Bundle();
            bundle2.putString("acelerometro_x", null);
            bundle2.putString("acelerometro_y", null);
            bundle2.putString("acelerometro_z", null);
            bundle2.putString("velocidade_digi_email", RegisterActivity.tempoEmail);
            bundle2.putString("velocidade_digi_senha", RegisterActivity.tempoSenha);
            bundle2.putString("velocidade_digi_nome", RegisterActivity.tempoNome);
            bundle2.putString("velocidade_digi_sexo", RegisterActivity.tempoSexo);
            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", null);
            bundle2.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_sucesso", bundle2);
//
//            //TODO popular evento
//
        }catch (JSONException e) {
            e.printStackTrace();
                    Bundle bundle2 = new Bundle();
            bundle2.putString("acelerometro_x", null);
            bundle2.putString("acelerometro_y", null);
            bundle2.putString("acelerometro_z", null);
            bundle2.putString("velocidade_digi_email", RegisterActivity.tempoEmail);
            bundle2.putString("velocidade_digi_senha", RegisterActivity.tempoSenha);
            bundle2.putString("velocidade_digi_nome", RegisterActivity.tempoNome);
            bundle2.putString("velocidade_digi_sexo", RegisterActivity.tempoSexo);
            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", null);
            bundle2.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_erro", bundle2);
        }



      /*  try {
            JSONObject jsonObject = new JSONObject(s);

            String loginToken = jsonObject.getString("login_token");
            String saldo = jsonObject.getString("user_saldo");

            LoginActivity.LOGIN_TOKEN = loginToken;

            Bundle bundle2 = new Bundle();
            bundle2.putString("acelerometro_x", null);
            bundle2.putString("acelerometro_y", null);
            bundle2.putString("acelerometro_z", null);
            bundle2.putString("velocidade_digi_email", tempoEmail);
            bundle2.putString("velocidade_digi_senha", tempoSenha);
            bundle2.putString("velocidade_digi_nome", tempoNome);
            bundle2.putString("velocidade_digi_sexo", tempoSexo);
            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", null);
            bundle2.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_sucesso", bundle2);

            //TODO popular evento

            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", Double.parseDouble(saldo));
            Intent intent = new Intent(context, CadastroCartaoActivity.class);
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
            bundle.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_erro", bundle);
            //TODO popular evento

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(context,"Falha na conexão com o servidor. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();

        }*/
    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos processando seus dados.");
    }
}

