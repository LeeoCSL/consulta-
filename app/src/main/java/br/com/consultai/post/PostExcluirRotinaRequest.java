package br.com.consultai.post;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.model.Usuario;
import br.com.consultai.utils.DialogUtil;
import okhttp3.Request;


/**
 * Created by leonardo.ribeiro on 28/11/2017.
 */

public class PostExcluirRotinaRequest extends AsyncTask<Usuario, Void, String> {


    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ProgressDialog mDialog;

    public PostExcluirRotinaRequest(Context context) {
        this.context = context;
    }

    protected String doInBackground(Usuario... users) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Usuario usuario = users[0];

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/delete_rotina";

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
        DialogUtil.hideProgressDialog(mDialog);

        int value = Integer.parseInt(s);

        if (value == 1) {
            for (int i = 0; i < MainFragment.DIAS_ATIVOS.length; i++) {
                MainFragment.DIAS_ATIVOS[i] = 0;
            }
            MainFragment.loadImages();
            EditarActivity.ROTINA_IDA = null;
            EditarActivity.ROTINA_VOLTA = null;

            Giroscopio giro = new Giroscopio(context);
            giro.execute();

            Bundle bundle = new Bundle();
            bundle.putString("giroscopio", Giroscopio.gyro);
            bundle.putString("velocidade_digitacao", null);
            bundle.putString("velocidade_clique", null);
//            bundle.putString("posicao_clique", MainFragment.coords);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
            mFirebaseAnalytics.logEvent("excluir_rotina", bundle);
            giro.cancel(true);

        }


    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos excluindo suas rotinas.");
    }
}


