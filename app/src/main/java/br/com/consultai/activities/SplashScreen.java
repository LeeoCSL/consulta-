package br.com.consultai.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.consultai.Acc;
import br.com.consultai.Giroscopio;
import br.com.consultai.MainActivity;
import br.com.consultai.R;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    public static String coords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Giroscopio giro = new Giroscopio(this);
        giro.execute();
        Acc acc = new Acc(SplashScreen.this);
        acc.execute();
        setContentView(R.layout.activity_splash_screen);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Branch branch = Branch.getInstance();
        branch.initSession(new Branch.BranchUniversalReferralInitListener() {
            @Override
            public void onInitFinished(BranchUniversalObject branchUniversalObject, LinkProperties linkProperties, BranchError error) {
                if (error == null) {

                    //variaveis que vão receber os parametros do link
                    String Origem = "organico";
                    String Campanha = "organico";

                    // parametros "channel" e "campaign"
                    if (linkProperties != null) {
                        Campanha = linkProperties.getCampaign();
                        Origem = linkProperties.getChannel();
                    }




//                    eventos firebase com as variaveis
                    Bundle bundle = new Bundle();
                    bundle.putString("origem", Origem);
                    bundle.putString("campanha", Campanha);
                    bundle.putString("giroscopio_X", Giroscopio.xText);
                    bundle.putString("giroscopio_Y", Giroscopio.yText);
                    bundle.putString("giroscopio_Z", Giroscopio.zText);
                    bundle.putString("acelerometro_X", Acc.xText);
                    bundle.putString("acelerometro_Y", Acc.yText);
                    bundle.putString("acelerometro_Z", Acc.zText);
                    bundle.putString("posicao_clique", coords);
                    bundle.putString("id_celular", Build.SERIAL);
                    mFirebaseAnalytics.logEvent("Tracking", bundle);



                }
                else {
                    Log.i("MyApp", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);





        giro.cancel(true);
        acc.cancel(true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
      /*          Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);

                finish();*/
                checkForCookie();
            }
        }, 2500);



    }

    public void checkForCookie(){
                /* COOKIE */
        SharedPreferences sharedPref = getSharedPreferences("PREF_LOGIN",Context.MODE_PRIVATE);
        String loginToken = sharedPref.getString("login_token", null);
        long hours = 60 * 60 * 24 * 7;

        if(loginToken != null){
            long lastTime = sharedPref.getLong("last_time", 0);

            // NAO PODE LOGAR
            if(System.currentTimeMillis() - lastTime > hours){
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);

                finish();
            }else{
                LoginActivity.LOGIN_TOKEN = loginToken;

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("login_token", loginToken);
                editor.putLong("last_time", System.currentTimeMillis());

                editor.commit();

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }else{
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);

            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                case MotionEvent.ACTION_MOVE:
//                case MotionEvent.ACTION_UP:
        }

        coords = coords + " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " | ";

        Log.v("xy", String.valueOf(x) + " " + String.valueOf(y));
//        Toast.makeText(this, x + " " +y, Toast.LENGTH_SHORT).show();
        return false;

    }
}
