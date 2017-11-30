package br.com.consultai.get;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Tabs.Tab;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Rotina;
import br.com.consultai.utils.DialogUtil;
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

    private ProgressDialog mDialog;

    public GetRotinaRequest(Context context){
        this.context = context;
    }

    protected String doInBackground(String... strings) {

//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String url = "https://zazzytec.com.br/get_rotina?id_usuario=" + userID + "&login_token=" + LoginActivity.LOGIN_TOKEN;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray array = new JSONArray(s);

            List<Rotina> rotinas = new ArrayList<>();

            if(array.length() < 1){
                return;
            }

            for(int i = 0; i < array.length(); i++){
                JSONObject object = (JSONObject) array.get(i);

                Rotina rotina = new Rotina();

                rotina.setIdRotina(object.getString("id_rotina"));
                rotina.setDomingo(object.getInt("domingo"));
                rotina.setSegunda(object.getInt("segunda"));
                rotina.setTerca(object.getInt("terca"));
                rotina.setQuarta(object.getInt("quarta"));
                rotina.setQuinta(object.getInt("quinta"));
                rotina.setSexta(object.getInt("sexta"));
                rotina.setSabado(object.getInt("sabado"));
                rotina.setHora(object.getString("hora"));
                rotina.setValor(object.getDouble("valor"));
                rotina.setTipo(object.getInt("tipo"));

                rotinas.add(rotina);
            }

            Rotina ida = rotinas.get(0);
            Rotina volta = rotinas.get(1);

            if(ida != null){
                EditarActivity.ROTINA_IDA = ida;
            }

            if(volta != null){
                EditarActivity.ROTINA_VOLTA = volta;
            }

            MainFragment.DIAS_ATIVOS = ida.getDays();
            MainFragment.loadImages();

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

