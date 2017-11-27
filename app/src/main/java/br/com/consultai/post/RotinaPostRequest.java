package br.com.consultai.post;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.model.Rotina;
import br.com.consultai.utils.DialogUtil;
import okhttp3.Request;

/**
 * Created by renan.boni on 27/11/2017.
 */

public class RotinaPostRequest extends AsyncTask<Rotina, Void, String> {

    private Context context;
    private ProgressDialog mDialog;

    public RotinaPostRequest(Context context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos salvando a sua rotina.");
    }

    @Override
    protected String doInBackground(Rotina... rotinas) {

        Rotina rotina = rotinas[0];

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/nova_rotina";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(rotina));
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

        Log.i("rotinas", s);

        DialogUtil.hideProgressDialog(mDialog);
    }
}
