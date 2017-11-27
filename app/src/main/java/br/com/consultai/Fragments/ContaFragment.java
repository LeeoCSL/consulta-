package br.com.consultai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import br.com.consultai.R;
import br.com.consultai.serv.GetCartaoRequest;

/**
 * Created by leonardo.ribeiro on 14/11/2017.
 */

public class ContaFragment extends Fragment {

    public ContaFragment(){}

    public static EditText mApelido;
    public static EditText mNumero;

    private CheckBox mEstudante;

    public static String apelido;
    public static String numero;
    public static int estudante;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_conta, null);

        mApelido = view.findViewById(R.id.edt_apelido);
        mNumero = view.findViewById(R.id.edt_numero);
        mEstudante = view.findViewById(R.id.cb_estudante);

        GetCartaoRequest request = new GetCartaoRequest(getContext());
        request.execute();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mApelido.setText(apelido);
        mNumero.setText(numero);

        if(estudante == 1){
            mEstudante.setChecked(true);
        }else{
            mEstudante.setChecked(false);
        }
    }
}
