package br.com.consultai.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.consultai.Acc;
import br.com.consultai.fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.model.Rotina;
import br.com.consultai.utils.Constants;
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

    private FirebaseAnalytics mFirebaseAnalytics;


    public RotinaPostRequest(Activity context) {
        this.context = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

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

        String url = Constants.URL + "nova_rotina";

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
//        int value = 1;
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//        Log.v("erro_rotina", s);
        if (value == 1) {
            EditarActivity.ROTINA_IDA = rotinaIda;
            EditarActivity.ROTINA_VOLTA = rotinaVolta;
            MainFragment.DIAS_ATIVOS = rotinaIda.getDays();
            MainFragment.loadImages();

            Giroscopio giro = new Giroscopio(context);
            giro.execute();
            Acc acc = new Acc(context);
            acc.execute();
            Bundle bundle = new Bundle();
            bundle.putString("giroscopio", Giroscopio.gyro);
            bundle.putString("acelerometro", Acc.Acc);
            bundle.putString("velocidade_clique", EditarActivity.tempoCliqueRotina);
            bundle.putString("posicao_clique", EditarActivity.coords);
            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
            bundle.putString("id_celular", Build.SERIAL);
            mFirebaseAnalytics.logEvent("cadastro_rotina", bundle);
            giro.cancel(true);
            acc.cancel(true);


            context.finish();
        } else {
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