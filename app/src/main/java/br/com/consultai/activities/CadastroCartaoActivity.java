package br.com.consultai.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.serv.RegisterCartaoRequest;
import br.com.consultai.serv.RegisterRequest;
import br.com.consultai.utils.Utility;
import butterknife.BindView;

public class CadastroCartaoActivity extends AppCompatActivity {

    @BindView(R.id.edt_apelido)
    TextInputLayout mApelido;

    @BindView(R.id.edt_saldo)
    TextInputLayout mSaldo;

    @BindView(R.id.edt_numero)
    TextInputLayout mNumero;

   String apelido, numero;
   int saldo;

    Button btnProximo;

    Boolean estudante;

    CheckBox checkEstudante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cartao);

        checkEstudante = (CheckBox) findViewById(R.id.checkEstudante);

        btnProximo = (Button) findViewById(R.id.btnProximo);

        mApelido = (TextInputLayout)findViewById(R.id.edt_apelido);
        mSaldo = (TextInputLayout)findViewById(R.id.edt_saldo);
        mNumero = (TextInputLayout)findViewById(R.id.edt_numero);


        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromEditText();


            }
        });


    }



    private void validateDataFromEditText(){
        apelido = mApelido.getEditText().getText().toString().trim();
        String saldoStr = mSaldo.getEditText().getText().toString().trim();
        numero = mNumero.getEditText().getText().toString().trim();


        if(TextUtils.isEmpty(apelido)){
            mApelido.setError("O campo apelido está vazio.");
            return;
        }

        if(TextUtils.isEmpty(saldoStr)){
            mSaldo.setError("O campo saldo está vazio.");
            return;
        }


        if(TextUtils.isEmpty(numero)){
            mNumero.setError("O campo numero está vazio.");
            return;
        }

        if(checkEstudante.isChecked()) {
            estudante = true;
        }
        if(!checkEstudante.isChecked()) {
            estudante = false;
        }

        saldo = Integer.parseInt(mSaldo.getEditText().getText().toString().trim());

        Toast.makeText(this, estudante.toString(), Toast.LENGTH_SHORT).show();

        createCartao(numero, apelido, String.valueOf(saldo), String.valueOf(estudante));
    }

    private void createCartao(String numero, String apelido, String saldo, String estudante){

                        RegisterCartaoRequest registerCartao = new RegisterCartaoRequest(this);
                        //TODO incluir tipo
                        registerCartao.execute(numero, apelido, saldo, estudante);



                        Intent intent = new Intent(CadastroCartaoActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }


}
