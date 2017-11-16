package br.com.consultai.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.serv.GetSaldoRequest;

/**
 * Created by leonardo.ribeiro on 14/11/2017.
 */

public class Principal extends Fragment {

    public static final String SALDO = "saldo";

    String user_id;

    String user_saldo = "1000";

    String tipo;

    TextView txt_valor;

    Float recarga;

    Float saldo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_principal, null);

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        txt_valor = (TextView) view.findViewById(R.id.txt_valor);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        tipo = sharedPref.getString("userTipo", "COMUM");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        Toast.makeText(getContext(), "Viagem extra onibus Comum", Toast.LENGTH_SHORT).show();

                    }
                });
                maisIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Viagem extra integraçao Comum", Toast.LENGTH_SHORT).show();


                    }
                });

                maisEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Viagem extra onibus Estudante", Toast.LENGTH_SHORT).show();

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        return view;
    }

    public void recargaBilhete(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

        Intent intent = new Intent(getContext(), EditarActivity.class);
        startActivity(intent);

    }

    public void testeSaldo(View v){


        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
        getSaldoRequest.execute(user_id);
//        txt_valor.setText(Utility.formatValue(Float.parseFloat(user_id)));
//        BackgroundWorker worker = new BackgroundWorker(MainActivity.this);
//        worker.execute(SALDO, user_id, user_saldo);
    }


}
