package br.com.consultai.post;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.Cartao;
import br.com.consultai.utils.DialogUtil;
import okhttp3.Request;

public class PostAtualizaCartao extends AsyncTask<Cartao, Void, String>{

    private Context context;
    private ProgressDialog mDialog;
    private FirebaseAnalytics mFirebaseAnalytics;


    public PostAtualizaCartao(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Cartao... cartaos) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Cartao cartao = cartaos[0];

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/novo_cartao";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(cartao));
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

            String apelido = jsonObject.getString("apelido");
            String numero = jsonObject.getString("numero");
            int estudante = jsonObject.getInt("estudante");

            Cartao cartao = new Cartao();
            cartao.setApelido(apelido);
            cartao.setEstudante(estudante);
            cartao.setNumeroCartao(numero);

            MainFragment.APELIDO = apelido;
            MainFragment.txtNomeBilhete.setText(apelido);

            ContaFragment.CARTAO = cartao;
            ContaFragment.loadCartao();

            DialogUtil.hideProgressDialog(mDialog);
            Toast.makeText(context, "Seu cartão foi atualizado.", Toast.LENGTH_LONG).show();

            Giroscopio giro = new Giroscopio(context);
            giro.execute();

            Bundle bundle2 = new Bundle();
            bundle2.putString("giroscopio", Giroscopio.gyro);
            bundle2.putString("velocidade_digi_email", RegisterActivity.tempoEmail);
            bundle2.putString("velocidade_digi_senha", RegisterActivity.tempoSenha);
            bundle2.putString("velocidade_digi_nome", RegisterActivity.tempoNome);
            bundle2.putString("velocidade_digi_sexo", RegisterActivity.tempoSexo);
//            bundle2.putString("velocidade_clique", null);
            bundle2.putString("posicao_clique", ContaFragment.coords);
            bundle2.putString("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
            mFirebaseAnalytics.logEvent("editar_bilhete", bundle2);

            giro.cancel(true);

        } catch (JSONException e) {
            DialogUtil.hideProgressDialog(mDialog);
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos atualizando seus dados.");
    }
}
