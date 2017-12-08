package br.com.consultai.get;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Cartao;
import br.com.consultai.model.Rotina;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRotinaRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

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

            if(array.length() < 1 || s.equals("[]")){
                for(int i = 0; i < MainFragment.DIAS_ATIVOS.length; i++){
                    MainFragment.DIAS_ATIVOS[i] = 0;
                    MainFragment.loadImages();
                }
                EditarActivity.ROTINA_IDA = null;
                EditarActivity.ROTINA_VOLTA = null;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {

    }
}

