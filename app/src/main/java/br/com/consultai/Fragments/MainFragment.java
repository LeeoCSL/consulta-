package br.com.consultai.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.CadastroCartaoActivity;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.serv.GetSaldoRequest;
import br.com.consultai.serv.PostSaldoRequest;
import br.com.consultai.utils.Utility;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainFragment extends Fragment {

    private static Context context;

    public static double SALDO = -1;
    public static String APELIDO;

    public static ProgressDialog dialog;

    public static TextView tvSaldo;

    public static TextView txtVlr;

    public static int ESTUDANTE;

    String tipoGet;

    Button selec_dom, selec_seg, selec_ter, selec_qua, selec_qui, selec_sex, selec_sab;

    Boolean dom_ativo = false;
    Boolean seg_ativo = true;
    Boolean ter_ativo = true;
    Boolean qua_ativo = true;
    Boolean qui_ativo = true;
    Boolean sex_ativo = true;
    Boolean sab_ativo = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tipoGet = "0";

        txtNomeBilhete = (TextView) view.findViewById(R.id.txt_nome_bilhete);


        context = getApplicationContext();

        btnExcluir = (Button) view.findViewById(R.id.btnExcluir);

        txtVlr = (TextView) view.findViewById(R.id.txtVlr);

        btnRecarga = (Button) view.findViewById(R.id.btnRecarga);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastroCartaoActivity.class));
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

        selec_dom = (Button) view.findViewById(R.id.selec_dom);
        selec_seg = (Button) view.findViewById(R.id.selec_seg);
        selec_ter = (Button) view.findViewById(R.id.selec_ter);
        selec_qua = (Button) view.findViewById(R.id.selec_qua);
        selec_qui = (Button) view.findViewById(R.id.selec_qui);
        selec_sex = (Button) view.findViewById(R.id.selec_sex);
        selec_sab = (Button) view.findViewById(R.id.selec_sab);

        tvSaldo = view.findViewById(R.id.txt_valor);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        tipo = sharedPref.getString("userTipo", "ESTUDANTE");

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



        PostSaldoRequest postSaldoRequest = new PostSaldoRequest(context);
        postSaldoRequest.execute(FirebaseAuth.getInstance().getCurrentUser().getUid(), String.valueOf(saldoPost));
    }

    @Override
    public void onResume() {
        super.onResume();
        tipoGet = "0";

        Log.i("resumeh", ""+SALDO);

        if(SALDO < 0){

        }else {
            tvSaldo.setText("R$ " +SALDO);
        }

        txtNomeBilhete.setText(APELIDO);
    }
}
