package br.com.consultai.post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 08/12/2017.
 */
public class RegisterRequest extends AsyncTask<Usuario, Void, String> {

    private Context context;

    private FirebaseAnalytics mFirebaseAnalytics;

    public RegisterRequest(Context context) {
        this.context = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    protected String doInBackground(Usuario... usuarios) {

        Usuario usuario = usuarios[0];

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
        if (response == null) {
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
                                LoginActivity.mDialog.dismiss();
                            }
                        });

                        builder.show();
                    }
                }
            });
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(response);

            LoginActivity.LOGIN_TOKEN = jsonObject.getString("login_token");

            LoginActivity.mDialog.dismiss();
            context.startActivity(new Intent(context, CadastroCartaoActivity.class));

    /*            Giroscopio giro = new Giroscopio(context);
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
                Log.v("cad", RegisterActivity.coords);
                mFirebaseAnalytics.logEvent("cadastro_sucesso", bundle2);
                giro.cancel(true);*/
            //

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

