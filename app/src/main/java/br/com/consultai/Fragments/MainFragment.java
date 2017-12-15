
package br.com.consultai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.auth.FirebaseAuth;

import br.com.consultai.R;
import br.com.consultai.activities.EditarActivity;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.get.GetCartaoRequest;
import br.com.consultai.get.GetRotinaRequest;
import br.com.consultai.get.GetSaldoRequest;
import br.com.consultai.model.Usuario;
import br.com.consultai.post.PostExcluirRotinaRequest;
import br.com.consultai.post.PostSaldoRequest;
import br.com.consultai.utils.Utility;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainFragment extends Fragment {

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

    Button btnExcluir;

    private double valor = 0;

    private boolean btnNormalSelecionado = false;
    private boolean btnIntegracaoSelecionado = false;
    private boolean btnEstudanteSelecionado = false;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        txtNomeBilhete = (TextView) view.findViewById(R.id.txt_nome_bilhete);
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

        btnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Digite o valor da recarga");
                builder.setCancelable(false);

                final CurrencyEditText input = new CurrencyEditText(getContext(), null);
                builder.setView(input);

                GetSaldoRequest request = new GetSaldoRequest(getContext());
                request.execute();

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double value = Utility.stringToFloat(input.getText().toString().trim());
                        double saldo = SALDO + value;

                        PostSaldoRequest post = new PostSaldoRequest(getContext());
                        post.execute(saldo);
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
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

        Button btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetSaldoRequest request = new GetSaldoRequest(getContext());
                request.execute();
            }
        });

        Button btnEditar = view.findViewById(R.id.btn_editar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditarActivity.class));
            }
        });
        return view;
    }

    private void createCustomDialog(final boolean adicionar){
        LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = null;

        if(ESTUDANTE == 0){
            dialoglayout = inflater.inflate(R.layout.adicionar_viagem_normal, null);

            final Button btnNormal = dialoglayout.findViewById(R.id.btn_normal);
            final Button btnIntegracao = dialoglayout.findViewById(R.id.btn_integracao);
            final TextView txtViagem = dialoglayout.findViewById(R.id.txt_viagem);

            if(adicionar){
                txtViagem.setText("Adicionar viagem");
            }else{
                txtViagem.setText("Excluir viagem");
            }

            btnNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnNormal.setBackgroundColor(Color.parseColor("#00bcd4"));
                    btnNormal.setTextColor(Color.WHITE);

                    btnIntegracao.setBackgroundResource(R.drawable.selector_card_background);
                    btnIntegracao.setTextColor(Color.BLACK);

                    valor = 3.8;
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

                    valor = 3.0;
                    btnNormalSelecionado = false;
                    btnIntegracaoSelecionado = true;
                }
            });
        }else{
            dialoglayout = inflater.inflate(R.layout.adicionar_viagem_estudante, null);

            TextView txtViagem = dialoglayout.findViewById(R.id.txt_viagem);

            final Button btnEstudante = dialoglayout.findViewById(R.id.btn_estudante);

            if(adicionar){
                txtViagem.setText("Adicionar viagem");
            }else{
                txtViagem.setText("Excluir viagem");
            }

            btnEstudante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(btnEstudanteSelecionado){
                        btnEstudante.setBackgroundResource(R.drawable.selector_card_background);
                        btnEstudante.setTextColor(Color.BLACK);

                        valor = 1.9;
                    }else{
                        btnEstudante.setBackgroundColor(Color.parseColor("#00bcd4"));
                        btnEstudante.setTextColor(Color.WHITE);

                        valor = 1.9;
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

                if(ESTUDANTE == 1){
                    if(!btnEstudanteSelecionado){
                        createAlert();
                        return;
                    }else{
                        if(adicionar){
                            double novoSalvo = SALDO - valor;

                            PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                            saldoRequest.execute(novoSalvo);
                        }else{
                            double novoSalvo = SALDO + valor;

                            PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                            saldoRequest.execute(novoSalvo);
                        }
                    }
                }else {
                    if(!btnNormalSelecionado && !btnIntegracaoSelecionado){
                        createAlert();
                        return;
                    }
                    if(adicionar){
                        double novoSalvo = SALDO - valor;

                        PostSaldoRequest saldoRequest = new PostSaldoRequest(getContext());
                        saldoRequest.execute(novoSalvo);
                    }else{
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

    private void createAlert(){
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
            tvSaldo.setText("R$ " + String.format( "%.2f", SALDO ).replace('.',','));
        }


        txtNomeBilhete.setText(APELIDO);

        loadImages();
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


}
