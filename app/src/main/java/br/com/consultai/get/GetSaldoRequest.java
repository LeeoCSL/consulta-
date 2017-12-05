package br.com.consultai.get;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by renan.boni on 27/11/2017.
 */

public class GetSaldoRequest extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mDialog;

    public GetSaldoRequest(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(mContext, "Aguarde", "Estamos atualizando seu saldo.");
    }

    @Override
    protected String doInBackground(String... strings) {

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String url = "https://zazzytec.com.br/user_saldo?id=" + userID + "&login_token=" + LoginActivity.LOGIN_TOKEN;

        Log.i("urlman", url);

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
