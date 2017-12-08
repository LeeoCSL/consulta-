package br.com.consultai.post;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Cartao;
import br.com.consultai.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 08/12/2017.
 */

public class PostLoginFBGoogle extends AsyncTask<Usuario, Void, String> {

    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ProgressDialog mDialog;

    public PostLoginFBGoogle(Context context) {
        this.context = context;
    }

    protected String doInBackground(Usuario... usuarios) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Usuario usuario = usuarios[0];

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/outro";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(usuario));
        builder.post(body);

        Request request = builder.build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
            String r = response.body().string();
Log.v("login", r );
            return r;
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

            String flag = jsonObject.getString("flag");

//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString("loginToken", loginToken);
//            editor.commit();


//            Giroscopio giro = new Giroscopio(context);
//            giro.execute();
//            Bundle bundle2 = new Bundle();
//            bundle2.putString("giroscopio", Giroscopio.gyro);
//            bundle2.putString("velocidade_digi_email", LoginActivity.tempoEmail);
//            bundle2.putString("velocidade_digi_senha", LoginActivity.tempoSenha);
//            bundle2.putString("velocidade_clique", null);
//            bundle2.putString("posicao_clique", null);
//            bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
//            bundle2.putString("id_celular", null);
//            mFirebaseAnalytics.logEvent("login_email_sucesso", bundle2);
//            //TODO popular evento
            LoginActivity.mDialog.dismiss();
            if (flag.equals("0")){
                Intent intent = new Intent(context, CadastroCartaoActivity.class);
                context.startActivity(intent);
            }
            else if(flag.equals("1")) {


                double saldo = jsonObject.getDouble("user_saldo");
                String apelido = jsonObject.getString("apelido");
                int estudante = jsonObject.getInt("estudante");
                String numero = jsonObject.getString("numero_cartao");



                LoginActivity.LOGIN_TOKEN = loginToken;
                MainFragment.APELIDO = apelido;
                MainFragment.SALDO = saldo;
                MainFragment.ESTUDANTE = estudante;

                ContaFragment.apelido = apelido;
                ContaFragment.estudante = estudante;
                ContaFragment.numero = numero;
                ContaFragment.estudante = estudante;

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            else if (flag.equals("-1")){
                FirebaseAuth.getInstance().getCurrentUser()
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Ops!");
                            builder.setMessage("Falha no comunicação com o servidor. Tente mais tarde.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    RegisterActivity.mDialog.dismiss();
                                }
                            });

                            builder.show();
                        }
                    }
                });
            }

        } catch (final JSONException e) {



            FirebaseAuth.getInstance().getCurrentUser()
                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Ops!");
                        builder.setMessage("Falha no comunicação com o servidor. Tente mais tarde." +e.getMessage());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RegisterActivity.mDialog.dismiss();
                            }
                        });

                        builder.show();
                    }
                }
            });
            return;
    /*
                Giroscopio giro = new Giroscopio(context);
                giro.execute();

                Bundle bundle2 = new Bundle();
                bundle2.putString("giroscopio", Giroscopio.gyro);
                bundle2.putString("velocidade_digi_email", RegisterActivity.tempoEmail);
                bundle2.putString("velocidade_digi_senha", RegisterActivity.tempoSenha);
                bundle2.putString("velocidade_digi_nome", RegisterActivity.tempoNome);
                bundle2.putString("velocidade_digi_sexo", RegisterActivity.tempoSexo);
    //            bundle2.putString("velocidade_clique", null);
                bundle2.putString("posicao_clique", RegisterActivity.coords);
                bundle2.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
    //            bundle2.putString("id_celular", null);
                mFirebaseAnalytics.logEvent("cadastro_erro", bundle2);

                giro.cancel(true);*/
        }
    }
    @Override
    protected void onPreExecute() {
    }


}


