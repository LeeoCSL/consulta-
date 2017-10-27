package br.com.consultai.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.model.User;
import br.com.consultai.utils.DialogFactory;
import br.com.consultai.utils.ToastUtils;
import butterknife.BindView;

import static br.com.consultai.utils.TextUtils.isEmailValid;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.edt_nome)
    EditText mUserName;

    @BindView(R.id.edt_senha)
    EditText mUserPassword;

    @BindView(R.id.edt_dtn)
    EditText mUserDtn;

    @BindView(R.id.edt_email)
    EditText mUserEmail;

    private static final int GALLERY_PICK = 1;

    private String userName;
    private String userPassword;
    private String userDtn;
    private String userEmail;

    private DatabaseReference mDatabaseCustomers;


    private Spinner sp;
    private ImageView sexo;

    private String[] resSexo = new String[]{"Masculino", "Feminino"};

    private FirebaseAuth mFirebaseAuth;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabaseCustomers = FirebaseDatabase.getInstance().getReference().child("customers");

        mFirebaseAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, resSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.sp_sexo);
        sp.setAdapter(adapter);
    }

    public void handlerUserRegister(View v) {
        validateDataFromEditText();
    }

    public void validateDataFromEditText() {


        userEmail = mUserEmail.getText().toString().trim();
        userName = mUserName.getText().toString().trim();
        userDtn = mUserDtn.getText().toString().trim();
        userPassword = mUserPassword.getText().toString().trim();


        if (TextUtils.isEmpty(userName)) {
            mUserName.setError(getString(R.string.name_field_empty));
            return;
        }

        if (TextUtils.isEmpty(userDtn)) {
            mUserDtn.setError(getString(R.string.invalid_telephone));
            return;
        }

        if (!isEmailValid(userEmail)) {
            mUserEmail.setError(getString(R.string.invalid_email));
            return;
        }

        if (userPassword.length() < 6) {
            mUserPassword.setError(getString(R.string.your_password_needs_more_caracters));
            return;
        }
        else {
            loginWithEmailAndPassword();
        }
    }

    private void loginWithEmailAndPassword() {

        DialogFactory.createDialog(this, getString(R.string.enter), getString(R.string.validating_information));

        mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userID = mFirebaseAuth.getCurrentUser().getUid();

                        if (mUri != null) {
                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(mUri).build();
                            authResult.getUser().updateProfile(request);
                        }


                        User user = new User();
                        user.setKey(userID);
                        user.setName(userName);
                        user.setDtn(userDtn);
                        user.setEmail(userEmail);

                        mDatabaseCustomers.child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DialogFactory.destroyDialog();
                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("type_login", "User");
                                editor.commit();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                })
                .addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        DialogFactory.destroyDialog();

                        ToastUtils.makeText(RegisterActivity.this, e.getMessage());

                        if (e.getClass() == FirebaseAuthUserCollisionException.class) {
                            ToastUtils.makeText(RegisterActivity.this, getString(R.string.email_already_used_with_facebook_account));
                        }
                    }
                });
    }

}
