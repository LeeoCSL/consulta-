package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.consultai.R;
import br.com.consultai.model.Usuario;
import br.com.consultai.post.PostExcluirRotinaRequest;
import br.com.consultai.utils.UtilTempoDigitacao;
import br.com.consultai.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @BindView(R.id.edt_email)
    EditText mEmail;

    @BindView(R.id.edt_senha)
    EditText mPassword;

    @BindView(R.id.edt_confirmar_senha)
    EditText mPasswordC;

    @BindView(R.id.edt_nome)
    EditText mName;

    @BindView(R.id.sp_sexo)
    Spinner mSexo;

    public static final String REGISTER = "register";

    private String [] resSexo = new String[]{"Masculino", "Feminino"};

    private FirebaseAnalytics mFirebaseAnalytics;

    SharedPreferences sharedPref;

    private String notification_token;

    private String userName, userEmail, userPassword, userNasc, userPasswordC;

    String sexo;

    String userSexo;

    String SO = "android";

    String deviceBrand = android.os.Build.MANUFACTURER;

    String serialNumber = Build.SERIAL;

    String imei = "00000000000000";

    TelephonyManager tm;

    public static String tempoEmail, tempoSenha, tempoNome, tempoSexo;

    public static String coords = "coordenadas";

    public static ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        imei = tm.getDeviceId();

        ButterKnife.bind(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, resSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSexo.setAdapter(adapter);

        notification_token = FirebaseInstanceId.getInstance().getToken();

        mAuth = FirebaseAuth.getInstance();

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoEmail =  String.valueOf(UtilTempoDigitacao.dtfs);


            }

        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();
                } else {
                    UtilTempoDigitacao.fimTempo();
                }
                tempoSenha =  String.valueOf(UtilTempoDigitacao.dtfs);
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();
                } else {
                    UtilTempoDigitacao.fimTempo();
                }
                tempoNome =  String.valueOf(UtilTempoDigitacao.dtfs);
            }
        });
        mSexo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();
                } else {
                    UtilTempoDigitacao.fimTempo();
                }
                tempoSexo =  String.valueOf(UtilTempoDigitacao.dtfs);
            }
        });
    }


    public void handlerLogin(View v){
        finish();
    }

    public void handlerUserRegister(View v){
        validateDataFromEditText();
    }

    private void validateDataFromEditText(){
        userName = mName.getText().toString().trim();
        userEmail = mEmail.getText().toString().trim();
        userPassword = mPassword.getText().toString().trim();
        userPasswordC = mPasswordC.getText().toString().trim();
        userSexo = (String) mSexo.getSelectedItem();

        sexo = String.valueOf(userSexo.charAt(0));

        if(TextUtils.isEmpty(userName)){
            mName.setError("O campo nome está vazio.");
            return;
        }

        if(!Utility.isEmailValid(userEmail)){
            mEmail.setError("Email inválido.");
            return;
        }

        if(userPassword.length() < 6){
            mPassword.setError("Sua senha deve ter no mínimo 6 caracteres.");
            return;
        }
        if(!userPassword.equals(userPasswordC)){
            mPassword.setError("As senhas nao correspondem.");
            mPasswordC.setError("As senhas nao correspondem.");
            return;
        }

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde");
        mDialog.setMessage("Estamos verificando suas credenciais.");
        mDialog.setCancelable(false);
        mDialog.show();

        createUser(userEmail, userPassword);
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        Usuario usuario = new Usuario();
                        usuario.setId(userID);
                        usuario.setEmail(userEmail);
                        usuario.setSenha(userPassword);
                        usuario.setNome(userName);
                        usuario.setNotificationToken(notification_token);
                        usuario.setSexo(sexo.charAt(0));
                        usuario.setModelo(deviceBrand);
                        usuario.setSerialMobile(serialNumber);
                        usuario.setIdUsuario(userID);
                        usuario.setSistemaOperacional("ANDROID");


                        PostExcluirRotinaRequest.RegisterRequest register = new PostExcluirRotinaRequest.RegisterRequest(RegisterActivity.this);
                        register.execute(usuario);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,  "Algum erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
                mDialog.dismiss();
            }
        });
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

