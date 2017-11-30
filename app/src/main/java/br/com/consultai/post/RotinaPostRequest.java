package br.com.consultai.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.R;
import br.com.consultai.Tabs.Tab_Ida;
import br.com.consultai.Tabs.Tab_Volta;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.model.Rotina;
import br.com.consultai.utils.DialogUtil;
import okhttp3.Request;

/**
 * Created by renan.boni on 27/11/2017.
 */

public class RotinaPostRequest extends AsyncTask<Rotina, Void, String> {

    private static Activity context;
    private ProgressDialog mDialog;

    private Rotina rotinaIda;
    private Rotina rotinaVolta;

    public RotinaPostRequest(Activity context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos salvando a sua rotina.");
    }

    @Override
    protected String doInBackground(Rotina... rotinas) {

        rotinaIda = rotinas[0];
        rotinaVolta = rotinas[1];

        List<Rotina> rotinasJSON = new ArrayList<>();
        rotinasJSON.add(rotinaIda);
        rotinasJSON.add(rotinaVolta);

        Gson gson = new Gson();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://zazzytec.com.br/nova_rotina";

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(rotinasJSON));
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

        if(value == 1){
            EditarActivity.ROTINA_IDA = rotinaIda;
            EditarActivity.ROTINA_VOLTA = rotinaVolta;

            context.finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Ops!");
            builder.setMessage("Erro na comunicação com o servidor. Tente novamente mais tarde.");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }
    }

}
