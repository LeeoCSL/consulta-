package br.com.consultai.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import br.com.consultai.R;
import br.com.consultai.model.User;
import br.com.consultai.model.Usuario;
import br.com.consultai.post.LoginRequest;
import br.com.consultai.post.PostExcluirRotinaRequest;
import br.com.consultai.post.PostLoginFBGoogle;
import br.com.consultai.post.RegisterRequest;
import br.com.consultai.utils.DialogFactory;
import br.com.consultai.utils.UtilTempoDigitacao;
import br.com.consultai.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public static String LOGIN_TOKEN;

    public static String emailParam;
    public static String emailGoogle;
    public static String linkFB;
    public static String nomeFB;
    public static String idFacebook;
    public static String emailFB;
    private FirebaseAnalytics mFirebaseAnalytics;

    String deviceBrand = android.os.Build.MANUFACTURER;

    String serialNumber = Build.SERIAL;

    public static final String LOGIN = "login";

    @BindView(R.id.input_email)
    EditText mLogin;

    @BindView(R.id.input_password)
    EditText mPassword;

    private FirebaseAuth mAuth;

    private LoginButton mLoginFacebook;
    private CallbackManager mCallbackManager;

    private SignInButton mGoogleLogin;

    private ImageView mLoginGoogle;

    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private String user_email, user_password;

    public static ProgressDialog mDialog;
    private String notification_token;

    public static String tempoEmail, tempoSenha;

    String name, gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mDialog = new ProgressDialog(this);
        ButterKnife.bind(this);

        if (LOGIN_TOKEN == null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            LOGIN_TOKEN = sp.getString("loginToken", "");
        }

        notification_token = FirebaseInstanceId.getInstance().getToken();

        mAuth = FirebaseAuth.getInstance();


        mLoginFacebook = (LoginButton) findViewById(R.id.login_fb);
        mGoogleLogin = (SignInButton) findViewById(R.id.login_google);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();

        mLoginFacebook.setReadPermissions("email", "public_profile", "user_birthday");

        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("facebook login", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {


            }
        });

        mLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    UtilTempoDigitacao.inicioTempo();

                } else {
                    UtilTempoDigitacao.fimTempo();
                }

                tempoEmail = String.valueOf(UtilTempoDigitacao.dtfs);


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
                tempoSenha = String.valueOf(UtilTempoDigitacao.dtfs);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                        UserProfileChangeRequest profUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(authResult.getUser().getDisplayName())
                                .setPhotoUri(Uri.parse("https://api.adorable.io/avatars/285/"
                                        + authResult.getUser().getDisplayName() + ".png"))
                                .build();

                        authResult.getUser().updateProfile(profUpdate);

                        User user = new User();
                        user.setEmail(authResult.getUser().getEmail());
//                        user.setName(authResult.getUser().getDisplayName());


                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("emailGoogle", user.getEmail());
//                        editor.putString("nome", user.getName());
                        editor.commit();

                        ref.setValue(user);
                        Usuario usuario = new Usuario();
                        usuario.setId(userID);
                        usuario.setEmail(user.getEmail());
                        usuario.setSenha("000000");
                        usuario.setNome(authResult.getUser().getDisplayName());
                        usuario.setNotificationToken(notification_token);
                        usuario.setSexo('I');
                        usuario.setModelo(deviceBrand);
                        usuario.setSerialMobile(serialNumber);
                        usuario.setIdUsuario(userID);
                        usuario.setSistemaOperacional("ANDROID");

                        PostLoginFBGoogle loginGoogle = new PostLoginFBGoogle(LoginActivity.this);
                        loginGoogle.execute(usuario);

                        Bundle bundle = new Bundle();
                        bundle.putString("email_google", user.getEmail());
                        bundle.putString("nome", sharedPref.getString("nome", ""));
                        mFirebaseAnalytics.logEvent("login_google_ok", bundle);
                    }
                });

    }

    private void validateDateFromEditText() {
        user_email = mLogin.getText().toString().trim();
        user_password = mPassword.getText().toString().trim();

        if (!Utility.isEmailValid(user_email)) {
            mLogin.setError("Email inválido.");
            mDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(user_password)) {
            mPassword.setError("Senha inválida.");
            mDialog.dismiss();
            return;
        }

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Aguarde");
        mDialog.setMessage("Estamos verificando suas credenciais.");
        mDialog.setCancelable(false);
        mDialog.show();

        loginWithEmailAndPassword();
    }

    private void loginWithEmailAndPassword() {
        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("emailParam", user_email);
                        editor.commit();

                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        Usuario usuario = new Usuario();
                        usuario.setId(userID);
                        usuario.setEmail(user_email);
                        usuario.setSenha(user_password);
                        usuario.setNotificationToken(notification_token);

                        LoginRequest login = new LoginRequest(LoginActivity.this);
                        login.execute(usuario);




                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (e.getClass() == FirebaseAuthUserCollisionException.class) {
                    Utility.makeText(LoginActivity.this,
                            "Email já está sendo usado em uma conta do facebook.");
                    mDialog.dismiss();
                    return;
                }
                if (e.getClass() == FirebaseAuthInvalidUserException.class) {
                    Utility.makeText(LoginActivity.this,
                            "Usuário não encontrado.");
                    mDialog.dismiss();
                    return;
                }
                if (e.getClass() == FirebaseAuthInvalidCredentialsException.class) {
                    mPassword.setError("Senha inválida");
                    mDialog.dismiss();
                    return;
                } else {
                    Utility.makeText(LoginActivity.this,
                            "Erro ao fazer login, tente novamente mais tarde.");
                }

            }
        });
    }

    public void handlerLogin(View v) {
        validateDateFromEditText();
    }

    public void register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void loginWithFacebook(View view) {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginFacebook.setReadPermissions("email", "public_profile");
        mLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Bundle bundle = new Bundle();
                bundle.putString("nome",sharedPref.getString("nome", ""));
                bundle.putString("email_facebook", sharedPref.getString("emailFB", ""));
                mFirebaseAnalytics.logEvent("login_facebook_ok", bundle);

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Bundle bundle = new Bundle();
                bundle.putString("nome",sharedPref.getString("nome", ""));
                bundle.putString("email_facebook", sharedPref.getString("emailFB", ""));
                mFirebaseAnalytics.logEvent("login_facebook_cancelado", bundle);
            }

            @Override
            public void onError(FacebookException error) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Bundle bundle = new Bundle();
                bundle.putString("nome",sharedPref.getString("nome", ""));
                bundle.putString("email_facebook", sharedPref.getString("emailFB", ""));
                mFirebaseAnalytics.logEvent("login_facebook_erro", bundle);

                Toast.makeText(LoginActivity.this, "Erro: " +error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
         AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            Profile profile = Profile.getCurrentProfile();

                            Usuario usuario = new Usuario();
                            usuario.setId(userID);
                            usuario.setEmail(user.getEmail());
                            usuario.setSenha("000000");
                            usuario.setNome(profile.getName());
                            usuario.setNotificationToken(notification_token);
                            usuario.setSexo('I');
                            usuario.setModelo(deviceBrand);
                            usuario.setSerialMobile(serialNumber);
                            usuario.setIdUsuario(userID);
                            usuario.setSistemaOperacional("ANDROID");

                            PostLoginFBGoogle loginFB = new PostLoginFBGoogle(LoginActivity.this);
                            loginFB.execute(usuario);

                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            DialogFactory.loadingDialog(this);

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                DialogFactory.hideLoadingDialog();
            } else {
                DialogFactory.hideLoadingDialog();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void handlerFakeFacebookLogin(View v) {
        if (v.getId() == R.id.login_fb_fake) {
            mLoginFacebook.performClick();
//            loginWithFacebook(v);
        }
    }

    public void handlerFakeGoogleLogin(View v) {
        if (v.getId() == R.id.login_google_fake) {
            //mGoogleLogin.performClick();
            signIn();
        }
    }

}
//TODO tratar erro de digitação