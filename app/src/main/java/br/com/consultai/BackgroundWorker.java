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
                            URLEncoder.encode("senha", "UTF-8") + "=" +URLEncoder.encode(userPassword, "UTF-8") + "&" +
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













            /*    try {

                    URL url = new URL(RegisterActivity.REGISTER_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream os = httpURLConnection.getOutputStream();

                    BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String postData =
                            URLEncoder.encode("id","UTF-8") + "=" +URLEncoder.encode(userID, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(userEmail, "UTF-8") + "&" +
                                    URLEncoder.encode("senha", "UTF-8") + "=" +URLEncoder.encode(userPassword, "UTF-8") + "&" +
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
                            URLEncoder.encode("id","UTF-8") + "=" +URLEncoder.encode(userID, "UTF-8") + "&" +
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
//        }
//
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("LoginRequest status");

        context.startActivity(new Intent(context, MainActivity.class));
    }
}
