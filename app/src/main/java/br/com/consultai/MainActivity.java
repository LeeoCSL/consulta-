package br.com.consultai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
//import com.roughike.bottombar.OnMenuTabClickListener;


import br.com.consultai.Fragments.ComoUsar;
import br.com.consultai.Fragments.Conta;
import br.com.consultai.Fragments.Principal;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.serv.Get;
import br.com.consultai.serv.Saldo;
import br.com.consultai.utils.Utility;

public class MainActivity extends ActionBarActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.bottomBarConta) {
                    Conta frag2 = (Conta) fm.findFragmentById(R.id.fragment1);
                }
                if (tabId == R.id.bottomBarInicio) {
                    Principal frag1 = (Principal) fm.findFragmentById(R.id.fragment1);
                }
                if (tabId == R.id.bottomBarAjuda) {
                    ComoUsar frag3 = (ComoUsar) fm.findFragmentById(R.id.fragment1);
                }
                if (tabId == R.id.bottomBarSair) {
                    logout();
                }
            }
        });

//        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
//            @Override
//            public void onTabReSelected(@IdRes int tabId) {
//                if (tabId == R.id.bottomBarInicio) {
//                    // The tab with id R.id.tab_favorites was reselected,
//                    // change your content accordingly.
//                }
//            }
//        });

    }

        public void logout(){
            FirebaseAuth.getInstance().signOut();


            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
               finish(); //TODO
        }


    }

