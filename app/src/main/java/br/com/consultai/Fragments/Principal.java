package br.com.consultai.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.get.GetSaldoRequest;

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
//        txt_valor.setText(Utility.formatValue(Float.parseFloat(id)));
//        BackgroundWorker worker = new BackgroundWorker(MainActivity.this);
//        worker.execute(SALDO, id, user_saldo);
    }


}
