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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.serv.Get;
import br.com.consultai.serv.Saldo;
import br.com.consultai.utils.Utility;

public class MainActivity extends AppCompatActivity {

    public static final String SALDO = "saldo";

    String user_id;

    String user_saldo = "1000";

    String tipo;

    TextView txt_valor;

    Float recarga;

    Float saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        txt_valor = (TextView) findViewById(R.id.txt_valor);

         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        tipo = sharedPref.getString("userTipo", "COMUM");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.viagem_mais, null);
                ImageView comoFunciona = (ImageView) mView.findViewById(R.id.btnComoFunciona);
                ImageView comoFuncionaMenos = (ImageView) mView.findViewById(R.id.btnComoFuncionaMenos);

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
                        builder.setMessage("Adicione uma viagem extra sempre que fizer um trajeto diferente do que está definido em sua rotina.");

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

                comoFuncionaMenos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Exclua uma viagem sempre que fizer um trajeto diferente do que está definido em sua rotina.");

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

    public void recargaBilhete(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_recarga, null);
        EditText edtValor = (EditText) mView.findViewById(R.id.edtValor);
        Button btnCancelar = (Button) mView.findViewById(R.id.btnCancelar);
        Button btnOk = (Button) mView.findViewById(R.id.btnOk);
        String rec = edtValor.getText().toString();
//        recarga = Utility.stringToFloat(edtValor.getText().toString());
        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saldo = saldo+recarga;
                saldo = 100.0f;
                txt_valor.setText(saldo.toString());

                dialog.dismiss();



            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });






    }

    public void attValor(){



    }
    public void atualizar(View v){

    }

    public void editar(View v){

        Intent intent = new Intent(MainActivity.this, EditarActivity.class);
        startActivity(intent);

    }

    public void testeSaldo(View v){


        Get get = new Get(MainActivity.this);
        get.execute(user_id);
//        txt_valor.setText(Utility.formatValue(Float.parseFloat(user_id)));
//        BackgroundWorker worker = new BackgroundWorker(MainActivity.this);
//        worker.execute(SALDO, user_id, user_saldo);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();


        Intent intent = new Intent(this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
