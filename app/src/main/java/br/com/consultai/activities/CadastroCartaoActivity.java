package br.com.consultai.activities;

import android.app.ProgressDialog;
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

import com.blackcat.currencyedittext.CurrencyEditText;
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
    CurrencyEditText mSaldo;

    @BindView(R.id.edt_numero)
    EditText mNumero;

    String apelido, numero;
    double  saldo;

    @BindView(R.id.btnProximo)
    Button btnProximo;

    Boolean estudante;

    @BindView(R.id.checkEstudante)
    CheckBox checkEstudante;

    public static ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cartao);

        ButterKnife.bind(this);


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

        saldo = Utility.stringToFloat(mSaldo.getText().toString().trim());

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde");
        mDialog.setMessage("Estamos verificando suas credenciais.");
        mDialog.show();

        createCartao(numero, apelido, String.valueOf(saldo), String.valueOf(estudante));
    }

    private void createCartao(String numero, String apelido, String saldo, String estudante) {
        RegisterCartaoRequest registerCartao = new RegisterCartaoRequest(this);

        //TODO incluir tipo
        registerCartao.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), LoginActivity.LOGIN_TOKEN, numero, apelido, saldo, estudante);
    }
}
