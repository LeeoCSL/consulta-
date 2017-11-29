package br.com.consultai.post;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.utils.DialogUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by renan.boni on 27/11/2017.
 */

public class PostSaldoRequest extends AsyncTask<Double, Void, String> {

    private Context context;
    private ProgressDialog mDialog;

    public PostSaldoRequest(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos atualizando seu saldo.");
    }

    @Override
    protected String doInBackground(Double... strings) {
        String url = "https://zazzytec.com.br/update_user";

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        MediaType mediaType =
                MediaType.parse("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            jsonObject.put("user_saldo", String.valueOf(strings[0]));
            jsonObject.put("login_token", LoginActivity.LOGIN_TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        DialogUtil.hideProgressDialog(mDialog);

        try {
            JSONObject jsonObject = new JSONObject(s);
            double saldo = jsonObject.getDouble("saldo");

            MainFragment.SALDO = saldo;
            MainFragment.tvSaldo.setText("R$ " +saldo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}