package br.com.consultai.serv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import br.com.consultai.MainActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.User;
import okhttp3.Request;

/**
 * Created by leonardo.ribeiro on 13/11/2017.
 */

public class LoginRequest extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog.Builder dialog;

    private boolean success = false;

    public LoginRequest(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... strings) {

        String userID = strings[0];
        String userEmail = strings[1];
        String userPassword = strings[2];
        String notificationToken = strings[3];
        String userDeviceBrand = strings[4];


        //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        User user = new User();
        user.setUser_id(userID);
        user.setUser_email(userEmail);
        user.setUser_password(userPassword);
        user.setNotification_token(notificationToken);
        user.setDevice_brand(userDeviceBrand);


        Gson gson = new Gson();

        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        String url = "https://consultai.000webhostapp.com/auth";

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        okhttp3.MediaType mediaType =
                okhttp3.MediaType.parse("application/json; charset=utf-8");


        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, gson.toJson(user));
        builder.post(body);

        Request request = builder.build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
//            Log.i("resp_server_login", response.body().string());
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

            String loginToken = jsonObject.getString("login_token");
            String saldo = jsonObject.getString("user_saldo");

            LoginActivity.LOGIN_TOKEN = loginToken;

            Bundle bundle = new Bundle();
            bundle.putDouble("saldo", Double.parseDouble(saldo));
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {

    }
}
