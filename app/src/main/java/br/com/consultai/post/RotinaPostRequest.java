package br.com.consultai.post;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.R;
import br.com.consultai.Tabs.Tab_Ida;
import br.com.consultai.Tabs.Tab_Volta;
import br.com.consultai.model.Rotina;
import br.com.consultai.utils.DialogUtil;
import okhttp3.Request;

/**
 * Created by renan.boni on 27/11/2017.
 */

public class RotinaPostRequest extends AsyncTask<Rotina, Void, String> {

    private static Activity context;
    private ProgressDialog mDialog;
    private Rotina rotina;

    public static int[] INDEXES = new int[7];

    public static boolean firstTab;

    public RotinaPostRequest(Activity context){
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        mDialog = DialogUtil.showProgressDialog(context, "Aguarde", "Estamos salvando a sua rotina.");
    }

    @Override
    protected String doInBackground(Rotina... rotinas) {

        rotina = rotinas[0];

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
        Log.i("response_int", s);
        int response = Integer.parseInt(s);

        if(firstTab){
            if(response == 1){
                DialogUtil.hideProgressDialog(mDialog);

                changeImage();

                MainFragment.loadImages();
                //Tab_Ida.ROTINA_IDA.setDays(rotina.getDays());

                alteraTab();
            }
        }else{
            if(response == 1){
                DialogUtil.hideProgressDialog(mDialog);

                changeImage();

                MainFragment.loadImages();
                Tab_Volta.ROTINA_VOLTA.setDays(rotina.getDays());

                context.finish();
            }
        }
    }

    public void changeImage(){
/*        if(MainFragment.DIAS_ATIVOS[0] == 0){
            MainFragment.DIAS_ATIVOS[0] = rotina.getDomingo();
        }
        if(MainFragment.DIAS_ATIVOS[1] == 0){
            MainFragment.DIAS_ATIVOS[1] = rotina.getSegunda();
        }
        if(MainFragment.DIAS_ATIVOS[2] == 0){
            MainFragment.DIAS_ATIVOS[2] = rotina.getTerca();
        }
        if(MainFragment.DIAS_ATIVOS[3] == 0){
            MainFragment.DIAS_ATIVOS[3] = rotina.getQuarta();
        }
        if(MainFragment.DIAS_ATIVOS[4] == 0){
            MainFragment.DIAS_ATIVOS[4] = rotina.getQuinta();
        }
        if(MainFragment.DIAS_ATIVOS[5] == 0){
            MainFragment.DIAS_ATIVOS[5] = rotina.getSexta();
        }
        if(MainFragment.DIAS_ATIVOS[6] == 0){
            MainFragment.DIAS_ATIVOS[6] = rotina.getSabado();
        }*/
/*
        for(int i = 0; i < MainFragment.DIAS_ATIVOS.length; i++){
            if(rotina.getDays()[i] != MainFragment.DIAS_ATIVOS[i]){
                if(rotina.getDays()[i] == 0 && MainFragment.DIAS_ATIVOS[i] != 0){
                    MainFragment.DIAS_ATIVOS[i] = rotina.getDays()[i];
                }
            }

        }*/

        /*
        for(int i = 0; i < INDEXES.length; i++){
            if(INDEXES[i] == 1){
                MainFragment.DIAS_ATIVOS[i] = rotina.getDays()[i];
            }
        }*/
    }

    public static void alteraTab(){
        TabLayout tabhost = (TabLayout) context.findViewById(R.id.tabs);
        tabhost.getTabAt(1).select();
        firstTab = false;
    }
}
