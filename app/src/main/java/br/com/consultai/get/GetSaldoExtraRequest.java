
package br.com.consultai.get;

/**
 * Created by leonardo.ribeiro on 29/11/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.utils.DialogUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static br.com.consultai.Fragments.MainFragment.recarga;
import static br.com.consultai.Fragments.MainFragment.saldoGet;
import static br.com.consultai.Fragments.MainFragment.saldoPost;


public class GetSaldoExtraRequest extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ProgressDialog mDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    public GetSaldoExtraRequest(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(mContext, "Aguarde", "Estamos atualizando seu saldo.");
    }

    @Override
    protected String doInBackground(String... strings) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

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
            if (MainFragment.tipoGet.equals("0")) {
                JSONObject jsonObject = new JSONObject(s);


                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));
                MainFragment.tvSaldo.setText("R$ " + saldo);
                MainFragment.dialog.dismiss();

                Giroscopio giro = new Giroscopio(mContext);
                giro.execute();

                Bundle bundle2 = new Bundle();
                bundle.putString("giroscopio", Giroscopio.gyro);
//                bundle2.putString("velocidade_clique", null);
                bundle2.putString("posicao_clique", MainFragment.coords);
                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFirebaseAnalytics.logEvent("atualizacao_saldo_sucesso", bundle2);
                giro.cancel(true);

            } else if (MainFragment.tipoGet.equals("1")) {
                JSONObject jsonObject = new JSONObject(s);


                String saldo = jsonObject.getString("user_saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + recarga;


                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                post.execute(saldoPost);
//                MainFragment.dialog.dismiss();

            }
            //viagem extra 3,8
            else if (MainFragment.tipoGet.equals("2")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                if (Double.valueOf(saldo) < 3.8) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Você nao tem saldo suficiente para uma viagem extra, realize uma recarga antes");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create();
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putDouble("saldo", Double.parseDouble(saldo));

                    MainFragment.saldoGet = Float.parseFloat(saldo);

                    saldoPost = saldoGet - 3.8f;

                    MainFragment.tvSaldo.setText("R$ " + saldoPost);

                    br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                    post.execute(saldoPost);
//                MainFragment.dialog.dismiss();

                    Giroscopio giro = new Giroscopio(mContext);
                    giro.execute();


                    Bundle bundle2 = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle2.putString("velocidade_clique", null);
                    bundle2.putString("posicao_clique", MainFragment.coords);
                    bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mFirebaseAnalytics.logEvent("viagem_extra_3_80", bundle2);
                    giro.cancel(true);


                }
            }
//viagem extra 3.0
            else if (MainFragment.tipoGet.equals("3")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                if (Double.valueOf(saldo) < 3.0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Você nao tem saldo suficiente para uma viagem extra, realize uma recarga antes");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create();
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putDouble("saldo", Double.parseDouble(saldo));

                    MainFragment.saldoGet = Float.parseFloat(saldo);

                    saldoPost = saldoGet - 3.0f;

                    MainFragment.tvSaldo.setText("R$ " + saldoPost);

                    br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                    post.execute(saldoPost);
//                MainFragment.dialog.dismiss();
                    Giroscopio giro = new Giroscopio(mContext);
                    giro.execute();

                    Bundle bundle2 = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle2.putString("velocidade_clique", null);
                    bundle2.putString("posicao_clique", MainFragment.coords);
                    bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mFirebaseAnalytics.logEvent("viagem_extra_3_00", bundle2);
                    giro.cancel(true);

                }
            }
//viagem extra 1,9
            else if (MainFragment.tipoGet.equals("4")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                if (Double.valueOf(saldo) < 1.9) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Você nao tem saldo suficiente para uma viagem extra, realize uma recarga antes");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create();
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putDouble("saldo", Double.parseDouble(saldo));

                    MainFragment.saldoGet = Float.parseFloat(saldo);

                    saldoPost = saldoGet - 1.9f;

                    MainFragment.tvSaldo.setText("R$ " + saldoPost);

                    br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                    post.execute(saldoPost);
//                MainFragment.dialog.dismiss();
                    Giroscopio giro = new Giroscopio(mContext);
                    giro.execute();

                    Bundle bundle2 = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle2.putString("velocidade_clique", null);
                    bundle2.putString("posicao_clique", MainFragment.coords);
                    bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mFirebaseAnalytics.logEvent("viagem_extra_1_90", bundle2);
                    giro.cancel(true);

                }
            }

            //viagem a menos 3,8
            else if (MainFragment.tipoGet.equals("5")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 3.8f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                post.execute(saldoPost);
//                MainFragment.dialog.dismiss();
                Giroscopio giro = new Giroscopio(mContext);
                giro.execute();

                Bundle bundle2 = new Bundle();
                bundle.putString("giroscopio", Giroscopio.gyro);
                bundle2.putString("velocidade_clique", null);
                bundle2.putString("posicao_clique", MainFragment.coords);
                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFirebaseAnalytics.logEvent("viagem_menos_3_80", bundle2);
                giro.cancel(true);

            }
//viagem menos 3.0
            else if (MainFragment.tipoGet.equals("6")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 3.0f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                post.execute(saldoPost);
//                MainFragment.dialog.dismiss();
                Giroscopio giro = new Giroscopio(mContext);
                giro.execute();

                Bundle bundle2 = new Bundle();
                bundle.putString("giroscopio", Giroscopio.gyro);
                bundle2.putString("velocidade_clique", null);
                bundle2.putString("posicao_clique", MainFragment.coords);
                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFirebaseAnalytics.logEvent("viagem_menos_3_00", bundle2);
                giro.cancel(true);

            }
//viagem menos 1,9
            else if (MainFragment.tipoGet.equals("7")) {
                JSONObject jsonObject = new JSONObject(s);

                String saldo = jsonObject.getString("saldo");

                Bundle bundle = new Bundle();
                bundle.putDouble("saldo", Double.parseDouble(saldo));

                MainFragment.saldoGet = Float.parseFloat(saldo);

                saldoPost = saldoGet + 1.9f;

                MainFragment.tvSaldo.setText("R$ " + saldoPost);

                br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(mContext);
                post.execute(saldoPost);
//                MainFragment.dialog.dismiss();
                Giroscopio giro = new Giroscopio(mContext);
                giro.execute();

                Bundle bundle2 = new Bundle();
                bundle.putString("giroscopio", Giroscopio.gyro);
                bundle2.putString("velocidade_clique", null);
                bundle2.putString("posicao_clique", MainFragment.coords);
                bundle2.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                bundle2.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFirebaseAnalytics.logEvent("viagem_menos_1_90", bundle2);
                giro.cancel(true);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
