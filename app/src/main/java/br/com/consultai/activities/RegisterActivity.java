package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import br.com.consultai.BackgroundWorker;
import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.serv.Register;
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

    @BindView(R.id.sp_tipo)
    Spinner mTipo;

    public static final String REGISTER = "register";

    private String [] resTipo = new String[]{"Comum", "Estudante"};

    private String [] resSexo = new String[]{"Masculino", "Feminino"};

    private StorageReference mImageStorage;

    private DatabaseReference mUserDatabase;

    private ProgressDialog mDialog;

    private FirebaseAnalytics mFirebaseAnalytics;

    SharedPreferences sharedPref;

    private String notification_token;

    private String userName, user_email, user_password, userNasc;

    String userSexo;

    String userTipo;

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

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, resTipo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTipo = (Spinner) findViewById(R.id.sp_tipo);
        mTipo.setAdapter(adapterTipo);





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
        userTipo = (String) mTipo.getSelectedItem();




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
            mNasc.setError("Número de telefone vazio.");
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

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);

                        UserProfileChangeRequest profUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(authResult.getUser().getDisplayName())
//
                                .build();

                        authResult.getUser().updateProfile(profUpdate);

//                        User user = new User();
//                        user.setUser_email(user_email);
//                        user.setName(userName);
//                        user.setDtn(userNasc);
//                        user.setSexo(userSexo);
//                        ref.setValue(user);

//                        Bundle bundle  = new Bundle();
//                        bundle.putString("user_email", user_email);
//                        bundle.putString("nome", userName);
//                        mFirebaseAnalytics.logEvent("cadastro_ok", bundle);

                        Register register = new Register(RegisterActivity.this);
                        register.execute(user_id, user_email, user_password, notification_token, device_brand);

                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("userTipo", userTipo.toUpperCase());
                        editor.commit();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,  "Algum erro ocorreu. Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageURI = data.getData();

            CropImage.activity(imageURI)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mDialog.setTitle("Aguarde...");
                mDialog.setMessage("Estamos carregando a sua imagem.");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                Uri resultUri = result.getUri();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(resultUri).build();
                user.updateProfile(request);


                String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final File thumbFilePath = new File(resultUri.getPath());

                Bitmap thumbNail;

                byte[] thumbBytes = null;

//                try {
//                    thumbNail =
//                            new Compressor(this)
//                                    .setMaxWidth(200)
//                                    .setMaxHeight(200)
//                                    .setQuality(75)
//                                    .compressToBitmap(thumbFilePath);
//
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    thumbNail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    thumbBytes = baos.toByteArray();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                StorageReference filePath = mImageStorage.child("profile_images")
                        .child(currentUserID +".jpg");

                final StorageReference thumbnailFilePath = mImageStorage.child("profile_images")
                        .child("thumbs").child(currentUserID + ".jpg");

                final byte[] finalThumbBytes = thumbBytes;

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            String downloadURL = task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumbnailFilePath.putBytes(finalThumbBytes);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){

                                    }
                                }
                            });

                            mUserDatabase.child("image").setValue(downloadURL)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                mDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Sua imagem foi salva.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar sua imagem.", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
