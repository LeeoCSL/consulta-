package br.com.consultai.serv;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by renan.boni on 22/11/2017.
 */

public class GetCartaoRequest extends AsyncTask<String, Void, String> {

    private Context context;

    public GetCartaoRequest(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String url = "https://consultai.000webhostapp.com/get_cartao?id="+userID+"&token="+ LoginActivity.LOGIN_TOKEN;

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
                String estudante = jsonObject.getString("estudante");

                ContaFragment.mApelido.setText(apelido);
                ContaFragment.mNumero.setText(numero);

                ContaFragment.numero = numero;
                ContaFragment.apelido = apelido;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
