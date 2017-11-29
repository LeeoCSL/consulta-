package br.com.consultai.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.get.GetRotinaRequest;
import br.com.consultai.model.User;
import br.com.consultai.model.Usuario;
import br.com.consultai.model.Usuario2;
import br.com.consultai.post.PostExcluirRotinaRequest;
import br.com.consultai.serv.GetSaldoRequest;
import br.com.consultai.serv.PostSaldoRequest;
import br.com.consultai.utils.Utility;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainFragment extends Fragment {

    public static double SALDO = -1;
    public static String APELIDO;

    private boolean[] diasAtivos = new boolean[7];
    public static int[] DIAS_ATIVOS = new int[7];

    private static int[] checkedImg = new int[7];
    private static int[] uncheckedImg = new int[7];

    private static Button[] btnDias = new Button[7];

    public static ProgressDialog dialog;

    public static TextView tvSaldo;

    public static TextView txtVlr;

    public static int ESTUDANTE;

    ImageView img_logo;
    String tipoGet;

    public static TextView txtNomeBilhete;


    String tipo;

    public static float saldoGet;
    public static float saldoPost;
    Button btnRecarga;
    public static float recarga;

    Button btnExcluir;

    public MainFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeCheckeds();
        initializeUncheckeds();

    }

    protected void initializeCheckeds(){
        String mDrawableName = "checked";

        for(int i = 0; i < 7; i++){
            checkedImg[i] = getResources().getIdentifier(mDrawableName + i , "drawable", getContext().getPackageName());
        }
    }

    protected void initializeUncheckeds(){
        String mDrawableName = "unchecked";

        for(int i = 0; i < 7; i++){
            uncheckedImg[i] = getResources().getIdentifier(mDrawableName + i , "drawable", getContext().getPackageName());
        }
    }

    private void initializeButtons(View rootView){
        btnDias[0] = rootView.findViewById(R.id.selec_dom);
        btnDias[1] = rootView.findViewById(R.id.selec_seg);
        btnDias[2] = rootView.findViewById(R.id.selec_ter);
        btnDias[3] = rootView.findViewById(R.id.selec_qua);
        btnDias[4] = rootView.findViewById(R.id.selec_qui);
        btnDias[5] = rootView.findViewById(R.id.selec_sex);
        btnDias[6] = rootView.findViewById(R.id.selec_sab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initializeButtons(view);

        tipoGet = "0";

        txtNomeBilhete = (TextView) view.findViewById(R.id.txt_nome_bilhete);

        img_logo = (ImageView) view.findViewById(R.id.img_logo);

        btnExcluir = (Button) view.findViewById(R.id.btnExcluir);

        txtVlr = (TextView) view.findViewById(R.id.txtVlr);

        btnRecarga = (Button) view.findViewById(R.id.btnRecarga);

        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final Usuario2 user = new Usuario2();
                user.setId_Usuario(userID);
                user.setLogin_token(LoginActivity.LOGIN_TOKEN);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Atenção");
                builder.setMessage("Tem certeza que deseja excluir suas rotinas?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PostExcluirRotinaRequest excluir = new PostExcluirRotinaRequest(getContext());
                        excluir.execute(user);
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        btnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoGet = "1";

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Digite o valor da recarga");

                final CurrencyEditText input = new CurrencyEditText(getContext(), null);
                builder.setView(input);

                br.com.consultai.get.GetSaldoRequest request = new br.com.consultai.get.GetSaldoRequest(getContext());
                request.execute();

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            double value = Utility.stringToFloat(input.getText().toString());
                            double saldo = SALDO + value;

                            br.com.consultai.post.PostSaldoRequest post = new br.com.consultai.post.PostSaldoRequest(getContext());
                            post.execute(saldo);
                        }
                    });

                builder.show();

            }
        });

        tvSaldo = view.findViewById(R.id.txt_valor);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        tipo = sharedPref.getString("userTipo", "");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.viagem_mais, null);
                ImageView comoFunciona = (ImageView) mView.findViewById(R.id.btnComoFunciona);
                ImageView comoFuncionaMenos = (ImageView) mView.findViewById(R.id.btnComoFuncionaMenos);

                Button maisOnibusComum = (Button) mView.findViewById(R.id.btnMaisOnibusComum);
                Button maisEstudante = (Button) mView.findViewById(R.id.btnMaisEstudante);
                Button maisIntegracaoComum = (Button) mView.findViewById(R.id.btnMaisIntegracaoComum);


                Button menosOnibusComum = (Button) mView.findViewById(R.id.btnMenosOnibusComum);
                Button menosIntegracaoComum = (Button) mView.findViewById(R.id.btnMenosIntegracaoComum);
                Button menosEstudante = (Button) mView.findViewById(R.id.btnMenosEstudante);

                if(tipo.equals("COMUM")){
                    maisOnibusComum.setVisibility(View.VISIBLE);
                    maisIntegracaoComum.setVisibility(View.VISIBLE);
                    menosOnibusComum .setVisibility(View.VISIBLE);
                    menosIntegracaoComum.setVisibility(View.VISIBLE);
                } else if(tipo.equals("ESTUDANTE")){
                    maisEstudante.setVisibility(View.VISIBLE);
                    menosEstudante.setVisibility(View.VISIBLE);
                }

                comoFunciona.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Adicione uma viagem extra sempre que fizer um trajeto diferente do que está definido em sua rotina.");

                        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create();
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                comoFuncionaMenos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Exclua uma viagem sempre que fizer um trajeto diferente do que está definido em sua rotina.");

                        builder.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.create();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                maisOnibusComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tipoGet = "2";
                        Toast.makeText(getContext(), "Viagem extra onibus Comum", Toast.LENGTH_SHORT).show();

                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );

                    }
                });
                maisIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Viagem extra integraçao Comum", Toast.LENGTH_SHORT).show();

                        tipoGet = "3";
                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );

                    }
                });

                maisEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Viagem extra onibus Estudante", Toast.LENGTH_SHORT).show();

                        tipoGet = "4";
                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );

                    }
                });

                menosOnibusComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tipoGet = "5";
                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );
                    }
                });

                menosIntegracaoComum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tipoGet = "6";
                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );
                    }
                });

                menosEstudante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tipoGet = "7";
                        GetSaldoRequest getSaldoRequest = new GetSaldoRequest(getContext());
                        getSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), tipoGet );
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        Button btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.com.consultai.get.GetSaldoRequest request = new br.com.consultai.get.GetSaldoRequest(getContext());
                request.execute();
            }
        });

        Button btnEditar = view.findViewById(R.id.btn_editar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditarActivity.class));
//                FragmentManager fragmentManager = getChildFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EditarFragment editarFragment = new EditarFragment();
//                fragmentTransaction.replace(R.id.fragment, editarFragment).commit();

//                Fragment editarFragment = new EditarFragment();
//                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                transaction.add(R.id.fragment, editarFragment).commit();

            }
        });
        return view;
    }


    public static void metodoPost(){
        PostSaldoRequest postSaldoRequest = new PostSaldoRequest(getApplicationContext());
        postSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), String.valueOf(saldoPost));
    }

    @Override
    public void onResume() {
        super.onResume();

        GetRotinaRequest rotina = new GetRotinaRequest(getContext());
        rotina.execute();

        tipoGet = "0";

        if(SALDO < 0){
            br.com.consultai.get.GetSaldoRequest request = new br.com.consultai.get.GetSaldoRequest(getContext());
            request.execute();
        }else {
            tvSaldo.setText("R$ " +SALDO);
        }

        txtNomeBilhete.setText(APELIDO);

        loadImages();
    }

    public static void loadImages(){
        for(int i = 0; i < DIAS_ATIVOS.length; i++){
            if(DIAS_ATIVOS[i] == i + 1){
                btnDias[i].setBackgroundResource(checkedImg[i]);
            }else{
                btnDias[i].setBackgroundResource(uncheckedImg[i]);
            }
        }
    }
}
