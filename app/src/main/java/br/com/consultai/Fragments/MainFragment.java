package br.com.consultai.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.serv.GetSaldoRequest;


public class MainFragment extends Fragment {

    public static double SALDO = 0.0;
    public static ProgressDialog dialog;

    public static TextView tvSaldo;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tvSaldo = view.findViewById(R.id.txt_valor);

        if(getArguments() != null){
            SALDO = getArguments().getDouble("saldo");
            tvSaldo.setText("R$ " +SALDO);
        }

        Button btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(getContext());
                dialog.setTitle("Aguarde");
                dialog.show();

                GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            SALDO = savedInstanceState.getDouble("saldo");
            tvSaldo.setText("R$ " +SALDO);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble("saldo", SALDO);
    }
}