package br.com.consultai.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.consultai.Fragments.EditarFragment;
import br.com.consultai.R;

public class EditarActivity extends AppCompatActivity {

    TextView txteste;
    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        txteste = (TextView) findViewById(R.id.txteste);

        FragmentManager fragM = getSupportFragmentManager();
        FragmentTransaction fragT = fragM.beginTransaction();
        EditarFragment editar = new EditarFragment();
        editar.setArguments(getIntent().getExtras());
        fragT.replace(R.id.fragmentEdt, editar);
        fragT.commit();

        //                FragmentManager fragmentManager = getChildFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EditarFragment editarFragment = new EditarFragment();
//                fragmentTransaction.replace(R.id.fragment, editarFragment).commit();

/*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tipo = sharedPref.getString("userTipo", "");

//        Toast.makeText(this, tipo, Toast.LENGTH_SHORT).show();
        if(tipo.equals("COMUM")){
            txteste.setText("Comum");
//            Toast.makeText(this, "COMUM", Toast.LENGTH_SHORT).show();
        }
        else if (tipo.equals("ESTUDANTE")){
            txteste.setText("Estudante");
//            Toast.makeText(this, "ESTUDANTE", Toast.LENGTH_SHORT).show();

        }

*/


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
