package br.com.consultai.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.Acc;
import br.com.consultai.Giroscopio;
import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.get.GetCartaoRequest;
import br.com.consultai.get.GetRotinaRequest;
import br.com.consultai.get.GetSaldoRequest;
import br.com.consultai.model.Usuario;
import br.com.consultai.post.PostExcluirRotinaRequest;
import br.com.consultai.post.PostSaldoRequest;
import br.com.consultai.utils.Constants;
import br.com.consultai.utils.UtilTempoDigitacao;
import br.com.consultai.utils.Utility;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainFragment extends Fragment {
    public static String coords;
    public static double SALDO = -1;
    public static String APELIDO;

    public static int[] DIAS_ATIVOS = new int[7];

    private static int[] checkedImg = new int[7];
    private static int[] uncheckedImg = new int[7];

    private static Button[] btnDias = new Button[7];

    public static TextView tvSaldo;
    public static TextView txtVlr;
    public static int ESTUDANTE;

    public static TextView txtNomeBilhete;
    public static int tipo;
    Button btnRecarga;
    public static String tempoRecarga;
    Button btnExcluir;
    private FirebaseAnalytics mFirebaseAnalytics;
    private double valor = 0;

    private boolean btnNormalSelecionado = false;
    private boolean btnIntegracaoSelecionado = false;
    private boolean btnEstudanteSelecionado = false;

    Button btn_limpar;

    String apelido;

    public static String tempoCliqueExcluir, tempoCliqueExtra,tempoCliqueRecarga, tempoCliqueEditar;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        initializeCheckeds();
        initializeUncheckeds();



    }

    protected void initializeCheckeds() {
        String mDrawableName = "checked";

        for (int i = 0; i < 7; i++) {
            checkedImg[i] = getResources().getIdentifier(mDrawableName + i, "drawable", getContext().getPackageName());
        }
    }

    protected void initializeUncheckeds() {
        String mDrawableName = "unchecked";

        for (int i = 0; i < 7; i++) {
            uncheckedImg[i] = getResources().getIdentifier(mDrawableName + i, "drawable", getContext().getPackageName());
        }
    }

    private void initializeButtons(View rootView) {
        btnDias[0] = rootView.findViewById(R.id.selec_dom);
        btnDias[1] = rootView.findViewById(R.id.selec_seg);
        btnDias[2] = rootView.findViewById(R.id.selec_ter);
        btnDias[3] = rootView.findViewById(R.id.selec_qua);
        btnDias[4] = rootView.findViewById(R.id.selec_qui);
        btnDias[5] = rootView.findViewById(R.id.selec_sex);
        btnDias[6] = rootView.findViewById(R.id.selec_sab);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initializeButtons(view);



        btn_limpar = (Button) view.findViewById(R.id.btn_limpar);

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Deseja realmente limpar seu saldo de " + SALDO + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double saldoLimpo = 0;
                        PostSaldoRequest post = new PostSaldoRequest(getContext());
                        post.execute(saldoLimpo);
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

        txtNomeBilhete = (TextView) view.findViewById(R.id.txt_nome_bilhete);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        apelido = sharedPref.getString("apelido", null);
        txtNomeBilhete.setText(apelido);

        btnExcluir = (Button) view.findViewById(R.id.btnExcluir);
        txtVlr = (TextView) view.findViewById(R.id.txtVlr);
        btnRecarga = (Button) view.findViewById(R.id.btnRecarga);

        GetRotinaRequest rotina = new GetRotinaRequest(getContext());
        rotina.execute();

        GetCartaoRequest cartaoRequest = new GetCartaoRequest(getContext());
        cartaoRequest.execute();

        GetSaldoRequest request = new GetSaldoRequest(getContext());
        request.execute();

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final Usuario user = new Usuario();
                user.setIdUsuario(userID);
                user.setLoginToken(LoginActivity.LOGIN_TOKEN);

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

        btnExcluir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }

                tempoCliqueExcluir = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });

        btnRecarga.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }
                tempoCliqueRecarga = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });





        btnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Digite o valor da recarga");
                builder.setCancelable(false);

                final CurrencyEditText input = new CurrencyEditText(getContext(), null);
                builder.setView(input);

                input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            UtilTempoDigitacao.inicioTempo();

                        } else {
                            UtilTempoDigitacao.fimTempo();
                        }

                        tempoRecarga = String.valueOf(UtilTempoDigitacao.dtfs);


                    }

                });

                GetSaldoRequest request = new GetSaldoRequest(getContext());
                request.execute();

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double value = Utility.stringToFloat(input.getText().toString().trim());
//                        Toast.makeText(getContext(), String.valueOf(SALDO) + "/ "+ String.valueOf(value), Toast.LENGTH_SHORT).show();
                        double saldo = SALDO + value;
//                        Toast.makeText(getContext(), String.valueOf(saldo), Toast.LENGTH_SHORT).show();
                        double limite = 999.999;
                        if(saldo <= limite) {
                            PostSaldoRequest post = new PostSaldoRequest(getContext());
                            post.execute(saldo);
                        }
                        else if (saldo > limite){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("O valor excedeu o limite de 999,99. Recarga nao realizada.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        tvSaldo = view.findViewById(R.id.txt_valor);

        Button fab = (Button) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.viagem_extra, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                TextView txtAddViagem = dialoglayout.findViewById(R.id.txt_add_viagem);
                TextView txtExcluirViagem = dialoglayout.findViewById(R.id.txt_excluir_viagem);

                txtAddViagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createCustomDialog(true);
                    }
                });

                txtExcluirViagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createCustomDialog(false);
                    }
                });

                builder.setView(dialoglayout);
                builder.show();
            }
        });

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }

                tempoCliqueExtra = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });



        Button btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSaldoRequest request = new GetSaldoRequest(getContext());
                request.execute();
            }
        });

        Button btnEditar;
        btnEditar = view.findViewById(R.id.btn_editar);

        btnEditar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }

                tempoCliqueEditar = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditarActivity.class));
            }
        });
        return view;
    }

    private void createCustomDialog(final boolean adicionar) {
        LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = null;

        if (ESTUDANTE == 0) {
            dialoglayout = inflater.inflate(R.layout.adicionar_viagem_normal, null);

            final Button btnNormal = dialoglayout.findViewById(R.id.btn_normal);
            final Button btnIntegracao = dialoglayout.findViewById(R.id.btn_integracao);
            final TextView txtViagem = dialoglayout.findViewById(R.id.txt_viagem);

            if (adicionar) {
                txtViagem.setText("Adicionar viagem");
            } else {
                txtViagem.setText("Excluir viagem");
            }

            btnNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnNormal.setBackgroundColor(Color.parseColor("#00bcd4"));
                    btnNormal.setTextColor(Color.WHITE);

                    btnIntegracao.setBackgroundResource(R.drawable.selector_card_background);
                    btnIntegracao.setTextColor(Color.BLACK);

                    valor = Constants.COMUM;
                    btnNormalSelecionado = true;
                    btnIntegracaoSelecionado = false;
                }
            });

            btnIntegracao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnNormal.setBackgroundResource(R.drawable.selector_card_background);
                    btnNormal.setTextColor(Color.BLACK);

                    btnIntegracao.setBackgroundColor(Color.parseColor("#00bcd4"));
                    btnIntegracao.setTextColor(Color.WHITE);

                    valor = Constants.COMUM_INTEGRACAO;
                    btnNormalSelecionado = false;
                    btnIntegracaoSelecionado = true;
                }
            });
        } else {
            dialoglayout = inflater.inflate(R.layout.adicionar_viagem_estudante, null);

            TextView txtViagem = dialoglayout.findViewById(R.id.txt_viagem);

            final Button btnEstudante = dialoglayout.findViewById(R.id.btn_estudante);

            if (adicionar) {
                txtViagem.setText("Adicionar viagem");
            } else {
                txtViagem.setText("Excluir viagem");
            }

            btnEstudante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnEstudanteSelecionado) {
                        btnEstudante.setBackgroundResource(R.drawable.selector_card_background);
                        btnEstudante.setTextColor(Color.BLACK);

                        valor = Constants.ESTUDANTE_COMUM;
                    } else {
                        btnEstudante.setBackgroundColor(Color.parseColor("#00bcd4"));
                        btnEstudante.setTextColor(Color.WHITE);

                        valor = Constants.ESTUDANTE_COMUM;
                    }
                    btnEstudanteSelecionado = !btnEstudanteSelecionado;
                }
            });
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setPositiveButton("Pronto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                GetSaldoRequest request = new GetSaldoRequest(getContext());
                request.execute();

                if (ESTUDANTE == 1) {
                    if (!btnEstudanteSelecionado) {
                        createAlert();
                        return;
                    } else {
                        if (adicionar) {
                            double novoSalvo = SALDO - valor;

                            Giroscopio giro = new Giroscopio(getContext());
                            giro.execute();
                            Acc acc = new Acc(getContext());
                            acc.execute();


                            Bundle bundle = new Bundle();
                            bundle.putString("giroscopio", Giroscopio.gyro);
                            bundle.putString("acelerometro", Acc.Acc);
                            bundle.putString("velocidade_digitacao", MainFragment.tempoRecarga);
                            bundle.putString("velocidade_clique", null);
                            bundle.putString("posicao_clique", MainActivity.coords);
                            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            bundle.putString("id_celular", Build.SERIAL);
                            mFirebaseAnalytics.logEvent("adicionar_viagem_" + valor, bundle);
                            giro.cancel(true);
                            acc.cancel(true);

                            PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                            saldoRequest.execute(novoSalvo);
                        } else {
                            double novoSalvo = SALDO + valor;

                            Giroscopio giro = new Giroscopio(getContext());
                            giro.execute();
                            Acc acc = new Acc(getContext());
                            acc.execute();

                            Bundle bundle = new Bundle();
                            bundle.putString("giroscopio", Giroscopio.gyro);
                            bundle.putString("acelerometro", Acc.Acc);
                            bundle.putString("velocidade_digitacao", MainFragment.tempoRecarga);
//            bundle.putString("velocidade_clique", null);
                            bundle.putString("posicao_clique", MainActivity.coords);
                            bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            bundle.putString("id_celular", Build.SERIAL);
                            mFirebaseAnalytics.logEvent("excluir_viagem_" + valor, bundle);
                            giro.cancel(true);
                            acc.cancel(true);

                            PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                            saldoRequest.execute(novoSalvo);
                        }
                    }
                } else {
                    if (!btnNormalSelecionado && !btnIntegracaoSelecionado) {
                        createAlert();
                        return;
                    }
                    if (adicionar) {
                        double novoSalvo = SALDO - valor;

                        PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                        saldoRequest.execute(novoSalvo);
                    } else {
                        double novoSalvo = SALDO + valor;

                        PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                        saldoRequest.execute(novoSalvo);
                    }
                }
                btnIntegracaoSelecionado = false;
                btnNormalSelecionado = false;
                btnEstudanteSelecionado = false;
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(dialoglayout);
        builder.show();
    }

    private void createAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Ops!");
        builder1.setMessage("Por favor, selecione um valor.");
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder1.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        int count = 0;
        for (int i = 0; i < DIAS_ATIVOS.length; i++) {
            count += DIAS_ATIVOS[i];
        }

        if (SALDO < 0) {
            GetSaldoRequest request = new GetSaldoRequest(getContext());
            request.execute();
        } else {
            tvSaldo.setText("R$ " + String.format("%.2f", SALDO).replace('.', ','));
        }

        txtNomeBilhete.setText(apelido);


        loadImages();

        GetRotinaRequest request = new GetRotinaRequest(getContext());
        request.execute();
    }



    public static void loadImages() {
        for (int i = 0; i < DIAS_ATIVOS.length; i++) {
            if (DIAS_ATIVOS[i] == i + 1) {
                btnDias[i].setBackgroundResource(checkedImg[i]);
            } else {
                btnDias[i].setBackgroundResource(uncheckedImg[i]);
            }
        }
    }


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