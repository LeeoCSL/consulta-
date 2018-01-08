package br.com.consultai.post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Acc;
import br.com.consultai.fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Cartao;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by renan.boni on 08/12/2017.
 */

public class RegisterCartaoRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog.Builder dialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    String cartaoApelido;

    public RegisterCartaoRequest(Context context) {
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

        if (Boolean.parseBoolean(strings[5])) {
            cartao.setEstudante(1);
        } else {
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
//            if(s == null){
//                FirebaseAuth.getInstance().getCurrentUser()
//                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            createAlert();
//                        }
//                    }
//                });
//                return;
//            }

            JSONObject jsonObject = new JSONObject(s);

            String loginToken = jsonObject.getString("login_token");
            double saldo = jsonObject.getDouble("saldo");
            String apelido = jsonObject.getString("apelido");

            LoginActivity.LOGIN_TOKEN = loginToken;

            MainFragment.SALDO = saldo;
            MainFragment.APELIDO = apelido;

            EditarActivity.ROTINA_IDA = null;
            EditarActivity.ROTINA_VOLTA = null;

            for (int i = 0; i < MainFragment.DIAS_ATIVOS.length; i++) {
                MainFragment.DIAS_ATIVOS[i] = 0;
            }
            //MainFragment.txtNomeBilhete.setText(apelido);

            Giroscopio giro = new Giroscopio(context);
            giro.execute();
            Acc acc = new Acc(context);
            acc.execute();
            Bundle bundle2 = new Bundle();
            bundle2.putString("giroscopio", Giroscopio.gyro);
            bundle2.putString("acelerometro", Acc.Acc);
            bundle2.putString("velocidade_dig_apelido", CadastroCartaoActivity.tempoApelido);
            bundle2.putString("velocidade_dig_numero", CadastroCartaoActivity.tempoNumero);
            bundle2.putString("velocidade_dig_saldo", CadastroCartaoActivity.tempoSaldo);
            bundle2.putString("velocidade_clique", CadastroCartaoActivity.tempoClique);
            bundle2.putString("posicao_clique", CadastroCartaoActivity.coords);
            bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", Build.SERIAL);
            mFirebaseAnalytics.logEvent("cadastro_bilhete_sucesso", bundle2);
            //TODO popular evento
            giro.cancel(true);
            acc.cancel(true);


            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", saldo);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);

            CadastroCartaoActivity.mDialog.dismiss();
            context.startActivity(intent);

        } catch (Exception e) {
            CadastroCartaoActivity.mDialog.dismiss();

//            FirebaseAuth.getInstance().getCurrentUser()
//                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        createAlert();
//                    }
//                }
//            });
            return;
        }
    }

    @Override
    protected void onPreExecute() {

    }

    private void createAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ops!");
        builder.setMessage("Falha no comunicação com o servidor. Infelizmente não conseguimos terminar seu cadastro. Tente novamente mais tarde.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.mDialog.dismiss();
            }
        });

        builder.show();
    }
}