package br.com.consultai.get;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.LoginActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static br.com.consultai.Fragments.MainFragment.recarga;
import static br.com.consultai.Fragments.MainFragment.saldoGet;
import static br.com.consultai.Fragments.MainFragment.saldoPost;

/**
 * Created by leonardo.ribeiro on 28/11/2017.
 */

public class GetRotinaRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static String sd;


    protected String doInBackground(String... strings) {

//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        String userID = strings[0];

        String url = "https://zazzytec.com.br/get_rotina?id=" + userID + "&login_token=" + LoginActivity.LOGIN_TOKEN;

        Log.i("url", url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.i("resp_server", response.body().string());
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


//                String saldo = jsonObject.getString("user_saldo");


//                Bundle bundle = new Bundle();
//                bundle.putDouble("saldo", Double.parseDouble(saldo));
//                MainFragment.tvSaldo.setText("R$ " + saldo);
//                MainFragment.dialog.dismiss();

//                Bundle bundle2 = new Bundle();
//                bundle2.putString("acelerometro_x", null);
//                bundle2.putString("acelerometro_y", null);
//                bundle2.putString("acelerometro_z", null);
//                bundle2.putString("velocidade_digitacao", null);
//                bundle2.putString("velocidade_clique", null);
//                bundle2.putString("posicao_clique", null);
//                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                bundle2.putString("id_celular", null);
//                mFirebaseAnalytics.logEvent("atualizacao_saldo_sucesso", bundle2);






        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {

    }
}

