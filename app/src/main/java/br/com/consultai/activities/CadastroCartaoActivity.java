package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.post.RegisterCartaoRequest;
import br.com.consultai.utils.UtilTempoDigitacao;
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

    public static String tempoApelido, tempoNumero, tempoSaldo;
    public static String coords;
    public static String tempoClique;
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

        btnProximo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();
                }
                tempoClique = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });



        mApelido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoApelido = String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });
        mSaldo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoSaldo = String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });

        mNumero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoNumero = String.valueOf(UtilTempoDigitacao.dtfs);


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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                case MotionEvent.ACTION_MOVE:
//                case MotionEvent.ACTION_UP:
        }

        coords = coords + " x: " + String.valueOf(x) + " y: " + String.valueOf(y) + " | ";

        Log.v("xy", String.valueOf(x) + " " + String.valueOf(y));
//        Toast.makeText(this, x + " " +y, Toast.LENGTH_SHORT).show();
        return false;

    }

}
