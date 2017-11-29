package br.com.consultai.get;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
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


        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        String url = "https://zazzytec.com.br/get_rotina?id_usuario=" + userID + "&login_token=" + LoginActivity.LOGIN_TOKEN;

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

//                JSONObject jsonObject = new JSONObject(s);
//            JSONArray jArray = new JSONArray(s);
//            JSONArray array2 = jArray.getJSONArray(1);
//            JSONArray array1 = jArray.getJSONArray(2);
//
//            Log.v("array 1", String.valueOf(array1));
//            Log.v("array 2", String.valueOf(array2));
            Toast.makeText(context, "post exec", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, String.valueOf(array1), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "post exec erro", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    @Override
    protected void onPreExecute() {

    }
}

