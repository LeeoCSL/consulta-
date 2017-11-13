package br.com.consultai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.consultai.activities.LoginActivity;
import br.com.consultai.activities.RegisterActivity;
import br.com.consultai.model.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leonardo.ribeiro on 08/11/2017.
 */


public class BackgroundWorker extends AsyncTask<String, Void, String> {

    private Context context;
    private AlertDialog.Builder dialog;


    public BackgroundWorker(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        String type = strings[0];


        switch (type){
            case LoginActivity.LOGIN:


                String userID = strings[1];
                String userEmail = strings[2];
                String userPassword = strings[3];
                String notificationToken = strings[4];
                String userDeviceBrand = strings[5];


                //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                User user = new User();
                user.setUser_id(userID);
                user.setUser_email(userEmail);
                user.setUser_password(userPassword);
                user.setNotification_token(notificationToken);
                user.setDevice_brand(userDeviceBrand);


                Gson gson = new Gson();

                OkHttpClient client = new OkHttpClient();

                String url = "https://consultai.000webhostapp.com/auth";

                Request.Builder builder = new Request.Builder();

                builder.url(url);

                MediaType mediaType =
                        MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(mediaType, gson.toJson(user));
                builder.post(body);

                Request request = builder.build();

                try {
                    Response response = client.newCall(request).execute();
                    Log.i("resp_server", response.body().string());
                    Gson gson4 = new Gson();
                    gson4.toJson(response.body().string()) ;
                    Log.i("json_teste", gson4.toString());

                    return response.body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    /*String userEmail = strings[1];
                    String userPassword = strings[2];
                    String notificationToken = strings[3];
                    String userDeviceBrand = strings[4];

                    URL url = new URL(LoginActivity.LOGIN_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream os = httpURLConnection.getOutputStream();

                    BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String postData =
                            URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                            URLEncoder.encode("user_password", "UTF-8") + "=" +URLEncoder.encode(userPassword, "UTF-8") + "&" +
                            URLEncoder.encode("notification_token", "UTF-8") + "=" +URLEncoder.encode(notificationToken, "UTF-8") + "&" +
                            URLEncoder.encode("user_device_brand", "UTF-8") + "=" +URLEncoder.encode(userDeviceBrand, "UTF-8");

                    writer.write(postData);
                    writer.flush();
                    writer.close();

                    os.close();

                    // LER CALLBACK
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

                    String result = null, line = null;

                    while((line = reader.readLine()) != null){
                        result += line;
                    }

                    reader.close();
                    is.close();
                    httpURLConnection.disconnect();*/



            case RegisterActivity.REGISTER:



                String userID2 = strings[1];
                String userEmail2 = strings[2];
                String userPassword2 = strings[3];
                String notificationToken2 = strings[4];
                String userDeviceBrand2 = strings[5];



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

            case MainActivity.SALDO:
                String userID3 = strings[1];
                String userSaldo = strings[2];

                User user3 = new User();
                user3.setUser_id(userID3);

                user3.setUser_saldo(userSaldo);


                Gson gson3 = new Gson();

                OkHttpClient client3 = new OkHttpClient();

                String url3 = "https://consultai.000webhostapp.com/update_user";

                Request.Builder builder3 = new Request.Builder();

                builder3.url(url3);

                MediaType mediaType3 =
                        MediaType.parse("application/json; charset=utf-8");


                RequestBody body3 = RequestBody.create(mediaType3, gson3.toJson(user3));
                builder3.post(body3);

                Request request3 = builder3.build();

                try {
                    Response response = client3.newCall(request3).execute();
                    Log.i("resp_server", response.body().string());
                    return response.body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            /*    try {

                    URL url = new URL(RegisterActivity.REGISTER_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream os = httpURLConnection.getOutputStream();

                    BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String postData =
                            URLEncoder.encode("user_id","UTF-8") + "=" +URLEncoder.encode(userID, "UTF-8") + "&" +
                            URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                                    URLEncoder.encode("user_password", "UTF-8") + "=" +URLEncoder.encode(userPassword, "UTF-8") + "&" +
                                    URLEncoder.encode("notification_token", "UTF-8") + "=" +URLEncoder.encode(notifcationToken, "UTF-8") + "&" +
                                    URLEncoder.encode("user_device_brand", "UTF-8") + "=" +URLEncoder.encode(userDeviceBrand, "UTF-8");

                    writer.write(postData);
                    writer.flush();
                    writer.close();

                    os.close();

                    // LER CALLBACK
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

                    String result = null, line = null;

                    while((line = reader.readLine()) != null){
                        result += line;
                    }

                    reader.close();
                    is.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
/*
            case MainActivity.ROTINA:
                String userID = strings[1];
                String horario = strings[2];
                String valor = strings[3];
                String dia = strings[4];

                URL url = null;

                try {
                    url = new URL(MainActivity.ROTINA_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream os = httpURLConnection.getOutputStream();

                    BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String postData =
                            URLEncoder.encode("user_id","UTF-8") + "=" +URLEncoder.encode(userID, "UTF-8") + "&" +
                                    URLEncoder.encode("horario", "UTF-8") + "=" + URLEncoder.encode(horario, "UTF-8") + "&" +
                                    URLEncoder.encode("valor", "UTF-8") + "=" +URLEncoder.encode(valor, "UTF-8") + "&" +
                                    URLEncoder.encode("dia", "UTF-8") + "=" +URLEncoder.encode(dia, "UTF-8");

                    writer.write(postData);
                    writer.flush();
                    writer.close();

                    os.close();

                    // LER CALLBACK
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

                    String result = null, line = null;

                    while((line = reader.readLine()) != null){
                        result += line;
                    }

                    reader.close();
                    is.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {


                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;*/
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Login status");

        context.startActivity(new Intent(context, MainActivity.class));
    }
}
