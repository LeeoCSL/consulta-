package br.com.consultai.post;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Cartao;
import br.com.consultai.model.Usuario;
import br.com.consultai.utils.DialogUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class LoginRequest extends AsyncTask<Usuario, Void, String> {

    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ProgressDialog mDialog;

    public LoginRequest(Context context) {
        this.context = context;
    }

    protected String doInBackground(Usuario... usuarios) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Usuario usuario = usuarios[0];

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/auth";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(usuario));
        builder.post(body);

        Request request = builder.build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
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
            String numero = jsonObject.getString("numero_cartao");
            LoginActivity.LOGIN_TOKEN = loginToken;

            if (numero.equals("-1")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Por favor, cadastre as informações do seu cartão para continuar");
                builder.setCancelable(false);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(context, CadastroCartaoActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.show();

            }
            else if (!numero.equals("-1")) {
                double saldo = jsonObject.getDouble("user_saldo");
                String apelido = jsonObject.getString("apelido");
                int estudante = jsonObject.getInt("estudante");



                MainFragment.APELIDO = apelido;
                MainFragment.SALDO = saldo;
                MainFragment.ESTUDANTE = estudante;

//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString("loginToken", loginToken);
//            editor.commit();



                ContaFragment.apelido = apelido;
                ContaFragment.estudante = estudante;
                ContaFragment.numero = String.valueOf(numero);
                ContaFragment.estudante = estudante;

                Giroscopio giro = new Giroscopio(context);
                giro.execute();


                Bundle bundle2 = new Bundle();
                bundle2.putString("giroscopio", Giroscopio.gyro);

                bundle2.putString("velocidade_digi_email", LoginActivity.tempoEmail);
                bundle2.putString("velocidade_digi_senha", LoginActivity.tempoSenha);
//                bundle2.putString("velocidade_clique", null);
//                bundle2.putString("posicao_clique", null);
                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFirebaseAnalytics.logEvent("login_email_sucesso", bundle2);
                //TODO popular evento

                LoginActivity.mDialog.dismiss();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }





        } catch (Exception e) {
            e.printStackTrace();

            Giroscopio giro = new Giroscopio(context);
            giro.execute();


            Bundle bundle = new Bundle();
            bundle.putString("giroscopio", Giroscopio.gyro);
//            bundle.putString("velocidade_digitacao", null);
//            bundle.putString("velocidade_clique", null);
//            bundle.putString("posicao_clique", null);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
            mFirebaseAnalytics.logEvent("login_email_erro", bundle);

            giro.cancel(true);

            LoginActivity.mDialog.dismiss();
        }
    }

    @Override
    protected void onPreExecute() {
    }
}
