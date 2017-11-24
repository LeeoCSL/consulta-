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

import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.User;
import br.com.consultai.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class RegisterRequest extends AsyncTask<Usuario, Void, Usuario> {

    private Context context;
    private AlertDialog.Builder dialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    public RegisterRequest(Context context){
        this.context = context;
    }


    protected Usuario doInBackground(Usuario... usuarios) {

        Usuario usuario = usuarios[0];

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Gson gson = new Gson();

        OkHttpClient client = new OkHttpClient();

        String url = "https://zazzytec.com.br/register";

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        Log.i("userGson", gson.toJson(usuario));
        RequestBody body = RequestBody.create(mediaType2, gson.toJson(usuario));

        builder.post(body);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson1 = new Gson();
            Log.i("resposta", response.body().string());
            return gson1.fromJson(response.body().string(), Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Usuario usuario) {

        Log.i("USUARIO", usuario.toString());


      /*  try {
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
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", null);
            mFirebaseAnalytics.logEvent("cadastro_erro", bundle);
            //TODO popular evento

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(context,"Falha na conexão com o servidor. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();

        }*/
    }

    @Override
    protected void onPreExecute() {

    }
}

