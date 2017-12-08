package br.com.consultai;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.roughike.bottombar.OnMenuTabClickListener;


import br.com.consultai.Fragments.ComoUsarFragment;
import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.utils.BottomNavigationViewHelper;
//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;
//import com.crashlytics.android.CrashlyticsInitProvider;

public class MainActivity extends AppCompatActivity {



    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("tokenasxs", token);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        Fabric.with(this, new Crashlytics());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(getIntent().getExtras());


        fragmentTransaction.replace(R.id.fragment, mainFragment).commit();

/*        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();

                            Log.i("idtoken", idToken);
                        } else {

                        }
                    }
                });*/
    }


    public void logout(){

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.fragment, new MainFragment()).commit();
                    return true;
                case R.id.navigation_cartao:
                    fragmentTransaction.replace(R.id.fragment, new ContaFragment()).commit();
                    return true;
                case R.id.navigation_help:
                    fragmentTransaction.replace(R.id.fragment, new ComoUsarFragment()).commit();
                    return true;
                case R.id.navigation_exit:

                    Giroscopio giro = new Giroscopio(MainActivity.this);
                    giro.execute();

                    Bundle bundle = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle.putString("velocidade_clique", null);
//                    bundle.putString("posicao_clique", MainFragment.coords);
                    bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle.putString("id_celular", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mFirebaseAnalytics.logEvent("logout", bundle);

                    giro.cancel(true);

                    logout();
                    return true;
            }
                return false;
            }

        };
    }

