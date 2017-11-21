package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
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
import br.com.consultai.serv.RegisterRequest;
import br.com.consultai.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int GALLERY_PICK = 1;

    @BindView(R.id.edt_email)
    TextInputLayout mEmail;

    @BindView(R.id.edt_senha)
    TextInputLayout mPassword;

    @BindView(R.id.til_nome)
    TextInputLayout mName;

    @BindView(R.id.edt_dtn)
    TextInputLayout mNasc;

    @BindView(R.id.sp_sexo)
    Spinner mSexo;



    public static final String REGISTER = "register";


    private String [] resSexo = new String[]{"Masculino", "Feminino"};

    private StorageReference mImageStorage;

    private DatabaseReference mUserDatabase;

    private ProgressDialog mDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    SharedPreferences sharedPref;

    private String notification_token;

    private String userName, user_email, user_password, userNasc;

    String userSexo;



    String device_brand = android.os.Build.MANUFACTURER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, resSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSexo = (Spinner) findViewById(R.id.sp_sexo);
        mSexo.setAdapter(adapter);


        String userID = FirebaseInstanceId.getInstance().getId();
        notification_token = FirebaseInstanceId.getInstance().getToken();

        mUserDatabase = FirebaseDatabase.getInstance().getReference()
                .child("users").child(userID);

        mImageStorage = FirebaseStorage.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        mEmail = (TextInputLayout)findViewById(R.id.edt_email);
        mPassword = (TextInputLayout)findViewById(R.id.edt_senha);
    }



    public void handlerLogin(View v){
        finish();
    }

    public void handlerUserRegister(View v){
        validateDataFromEditText();
    }

    private void validateDataFromEditText(){
        userName = mName.getEditText().getText().toString().trim();
        user_email = mEmail.getEditText().getText().toString().trim();
        user_password = mPassword.getEditText().getText().toString().trim();
        userNasc = mNasc.getEditText().getText().toString().trim();
        userSexo = (String) mSexo.getSelectedItem();


        if(TextUtils.isEmpty(userName)){
            mName.setError("O campo nome está vazio.");
            return;
        }

        if(!Utility.isEmailValid(user_email)){
            mEmail.setError("Email inválido.");
            return;
        }

        if(user_password.length() < 6){
            mPassword.setError("Sua senha deve ter no mínimo 6 caracteres.");
            return;
        }

        if(mNasc.getEditText().getText().toString().isEmpty()){
            mNasc.setError("Data de nascimento vazia");
            return;
        }
        createUser(user_email, user_password);
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        RegisterRequest register = new RegisterRequest(RegisterActivity.this);
                        //TODO incluir tipo
                        register.execute(user_id, user_email, user_password, notification_token, device_brand);




                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,  "Algum erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }


}

