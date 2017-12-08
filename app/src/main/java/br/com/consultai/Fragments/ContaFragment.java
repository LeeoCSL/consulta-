package br.com.consultai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.get.GetCartaoRequest;
import br.com.consultai.model.Cartao;
import br.com.consultai.post.PostAtualizaCartao;

/**
 * Created by leonardo.ribeiro on 14/11/2017.
 */

public class ContaFragment extends Fragment {

    public ContaFragment(){}

    public static EditText mApelido;
    public static EditText mNumero;

    private static CheckBox mEstudante;

    public static String apelido;
    public static String numero;
    public static int estudante;

    public static Cartao CARTAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_conta, null);

        mApelido = view.findViewById(R.id.edt_apelido);
        mNumero = view.findViewById(R.id.edt_numero);
        mEstudante = view.findViewById(R.id.cb_estudante);

        Button btnSalvar = view.findViewById(R.id.btn_salvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerSave();
            }
        });

        if(CARTAO != null){
            loadCartao();
        }else{
            GetCartaoRequest request = new GetCartaoRequest(getContext());
            request.execute();
        }

        return view;
    }

    public static void loadCartao(){
        mApelido.setText(CARTAO.getApelido());
        mNumero.setText(CARTAO.getNumeroCartao());

        if(CARTAO.getEstudante() == 1){
            mEstudante.setChecked(true);
        }else{
            mEstudante.setChecked(false);
        }
    }

    public void handlerSave(){
        validateDataFromEditText();
    }

    private void validateDataFromEditText(){
        String apelido = mApelido.getText().toString().trim();
        String numero = mNumero.getText().toString().trim();

        if(apelido.isEmpty()){
            mApelido.setError("Por favor, defina um apelido para o seu cartão.");
            return;
        }

        if(numero.isEmpty()){
            mNumero.setError("Por favor, defina o número do seu cartão.");
            return;
        }

        Cartao cartao = new Cartao();
        cartao.setApelido(apelido);
        cartao.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        cartao.setLoginToken(LoginActivity.LOGIN_TOKEN);
        cartao.setNumeroCartao(numero);

        if(mEstudante.isChecked()){
            cartao.setEstudante(1);
        }else{
            cartao.setEstudante(0);
        }

        PostAtualizaCartao postAtualizaCartao = new PostAtualizaCartao(getContext());
        postAtualizaCartao.execute(cartao);
    }
}
