package br.com.consultai.get;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.fragments.ContaFragment;
import br.com.consultai.fragments.MainFragment;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Cartao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by renan.boni on 07/12/2017.
 */

public class GetCartaoRequest extends AsyncTask<String, Void, String> {

    private Context context;

    public GetCartaoRequest(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String url = "https://zazzytec.com.br/get_cartao?id="+userID+"&token="+ LoginActivity.LOGIN_TOKEN;

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

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
            JSONObject jsonObject = new JSONObject(s);

            Log.i("resp_server", s);

            if(jsonObject != null){
                String apelido = jsonObject.getString("apelido");
                String numero = jsonObject.getString("numero");
                int estudante = jsonObject.getInt("estudante");
                double saldo = jsonObject.getDouble("saldo");

                MainFragment.tipo = estudante;

                Cartao cartao = new Cartao();
                cartao.setApelido(apelido);
                cartao.setNumeroCartao(numero);
                cartao.setEstudante(estudante);
                cartao.setSaldo(saldo);

                ContaFragment.CARTAO = cartao;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}