package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.model.Mobile;
import br.com.consultai.model.Usuario;
import br.com.consultai.serv.RegisterRequest;
import br.com.consultai.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @BindView(R.id.edt_email)
    EditText mEmail;

    @BindView(R.id.edt_senha)
    EditText mPassword;

    @BindView(R.id.edt_nome)
    EditText mName;

    @BindView(R.id.sp_sexo)
    Spinner mSexo;

    public static final String REGISTER = "register";

    private String [] resSexo = new String[]{"Masculino", "Feminino"};


    private ProgressDialog mDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    SharedPreferences sharedPref;

    private String notification_token;

    private String userName, userEmail, userPassword, userNasc;

    String sexo;

    String userSexo;

    String SO = "android";

    String deviceBrand = android.os.Build.MANUFACTURER;

    String serialNumber = Build.SERIAL;

    String imei = "00000000000000";

    TelephonyManager tm;

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

        mDialog = new ProgressDialog(this);
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

                        Log.i("usuario", usuario.toString());

                        RegisterRequest register = new RegisterRequest(RegisterActivity.this);
                        register.execute(usuario);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,  "Algum erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }
}

