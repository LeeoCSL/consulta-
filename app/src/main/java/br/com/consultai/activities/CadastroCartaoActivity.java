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
import butterknife.ButterKnife;

public class CadastroCartaoActivity extends AppCompatActivity {

    @BindView(R.id.edt_apelido)
    EditText mApelido;

    @BindView(R.id.edt_saldo)
    EditText mSaldo;

    @BindView(R.id.edt_numero)
    EditText mNumero;

    String apelido, numero;
    int saldo;

    Button btnProximo;

    Boolean estudante;

    CheckBox checkEstudante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cartao);

        ButterKnife.bind(this);

        checkEstudante = (CheckBox) findViewById(R.id.checkEstudante);

        btnProximo = (Button) findViewById(R.id.btnProximo);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDataFromEditText();


            }
        });


    }


    private void validateDataFromEditText() {
        apelido = mApelido.getText().toString().trim();
        String saldoStr = mSaldo.getText().toString().trim();
        numero = mNumero.getText().toString().trim();


        if (TextUtils.isEmpty(apelido)) {
            mApelido.setError("O campo apelido está vazio.");
            return;
        }

        if (TextUtils.isEmpty(saldoStr)) {
            mSaldo.setError("O campo saldo está vazio.");
            return;
        }


        if (TextUtils.isEmpty(numero)) {
            mNumero.setError("O campo numero está vazio.");
            return;
        }

        if (checkEstudante.isChecked()) {
            estudante = true;
        }
        if (!checkEstudante.isChecked()) {
            estudante = false;
        }

        saldo = Integer.parseInt(mSaldo.getText().toString().trim());

        createCartao(numero, apelido, String.valueOf(saldo), String.valueOf(estudante));
    }

    private void createCartao(String numero, String apelido, String saldo, String estudante) {

//        Toast.makeText(this, numero + ", " + apelido + ", " + saldo + ", " + estudante + ", " , Toast.LENGTH_SHORT).show();

        RegisterCartaoRequest registerCartao = new RegisterCartaoRequest(this);
        //TODO incluir tipo
        registerCartao.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), LoginActivity.LOGIN_TOKEN, numero, apelido, saldo, estudante);


        Intent intent = new Intent(CadastroCartaoActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
