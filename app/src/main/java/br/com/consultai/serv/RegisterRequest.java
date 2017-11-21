package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.login.Login;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import br.com.consultai.MainActivity;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class RegisterRequest extends AsyncTask<String, Void, String> {
    private Context context;
    private AlertDialog.Builder dialog;


    public RegisterRequest(Context context){
        this.context = context;
    }


    protected String doInBackground(String... strings) {

        String userID2 = strings[0];
        String userEmail2 = strings[1];
        String userPassword2 = strings[2];
        String notificationToken2 = strings[3];
        String userDeviceBrand2 = strings[4];


        User user2 = new User();
        user2.setUser_id(userID2);
        user2.setUser_email(userEmail2);
        user2.setUser_password(userPassword2);
        user2.setNotification_token(notificationToken2);
        user2.setDevice_brand(userDeviceBrand2);

        Gson gson2 = new Gson();

        OkHttpClient client2 = new OkHttpClient();

        String url2 = "https://consultai.000webhostapp.com/register";

        Request.Builder builder2 = new Request.Builder();

        builder2.url(url2);

        MediaType mediaType2 =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body2 = RequestBody.create(mediaType2, gson2.toJson(user2));

        Log.i("JSON2", gson2.toJson(user2));

        builder2.post(body2);

        Request request2 = builder2.build();

        try {
            Response response = client2.newCall(request2).execute();
            Log.i("resp_server", response.body().string());
            return response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);

            String loginToken = jsonObject.getString("login_token");
            String saldo = jsonObject.getString("user_saldo");

            LoginActivity.LOGIN_TOKEN = loginToken;

            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", Double.parseDouble(saldo));
            Intent intent = new Intent(context, CadastroCartaoActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

            context.startActivity(new Intent(context, RegisterActivity.class));

        }

    }

    @Override
    protected void onPreExecute() {



    }
}

