package br.com.consultai.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.consultai.Giroscopio;
import br.com.consultai.R;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        Giroscopio giro = new Giroscopio(this);
        giro.execute();*/
        setContentView(R.layout.activity_splash_screen);
     /*   mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
                    bundle.putString("giroscopio", Giroscopio.gyro);

                    mFirebaseAnalytics.logEvent("Tracking", bundle);

                }
                else {
                    Log.i("MyApp", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);*/

   /*     giro.cancel(true);*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, 2500);



    }
}
