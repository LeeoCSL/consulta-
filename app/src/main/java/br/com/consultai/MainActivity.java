package br.com.consultai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static final String SALDO = "saldo";

    String user_id;

    String user_saldo = "100";

    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        tipo = sharedPref.getString("userTipo", "COMUM");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.viagem_mais, null);
                ImageView comoFunciona = (ImageView) mView.findViewById(R.id.btnComoFunciona);
                Button maisOnibusComum = (Button) mView.findViewById(R.id.btnMaisOnibusComum);
                Button maisEstudante = (Button) mView.findViewById(R.id.btnMaisEstudante);
                Button maisIntegracaoComum = (Button) mView.findViewById(R.id.btnMaisIntegracaoComum);

                Button menosOnibusComum = (Button) mView.findViewById(R.id.btnMenosOnibusComum);
                Button menosIntegracaoComum = (Button) mView.findViewById(R.id.btnMenosIntegracaoComum);
                Button menosEstudante = (Button) mView.findViewById(R.id.btnMenosEstudante);

                if(tipo.equals("COMUM")){
                    maisOnibusComum.setVisibility(View.VISIBLE);
                    maisIntegracaoComum.setVisibility(View.VISIBLE);
                    menosOnibusComum .setVisibility(View.VISIBLE);
                    menosIntegracaoComum.setVisibility(View.VISIBLE);
                } else if(tipo.equals("ESTUDANTE")){
                    maisEstudante.setVisibility(View.VISIBLE);
                    menosEstudante.setVisibility(View.VISIBLE);
                }

                comoFunciona.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam fermentum magna nec enim elementum, vitae pharetra leo cursus. Sed at cursus nunc, a tincidunt felis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam porta lacus id pretium dapibus. Praesent ac tristique ligula, sed tempus lectus.");

                        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                maisOnibusComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Viagem extra onibus Comum", Toast.LENGTH_SHORT).show();

                    }
                });
                maisIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Viagem extra integraçao Comum", Toast.LENGTH_SHORT).show();


                    }
                });

                maisEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Viagem extra onibus Estudante", Toast.LENGTH_SHORT).show();

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });





}

    public void teste(View v){

    }

    public void editar(View v){

        Intent intent = new Intent(MainActivity.this, EditarActivity.class);
        startActivity(intent);

    }

    public void testeSaldo(View v){

        BackgroundWorker worker = new BackgroundWorker(MainActivity.this);
        worker.execute(SALDO, user_id, user_saldo);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();


        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
