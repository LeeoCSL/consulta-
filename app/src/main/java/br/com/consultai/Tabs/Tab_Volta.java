package br.com.consultai.Tabs;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import br.com.consultai.Fragments.ContaFragment;
import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.activities.LoginActivity;
import br.com.consultai.model.Rotina;
import br.com.consultai.post.RotinaPostRequest;

public class Tab_Volta extends Tab {

    private RadioGroup mRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_volta, container, false);

        initializeButtons(rootView);

        btnSalvar = (Button) rootView.findViewById(R.id.btnSalvar);
        rb_onibus = (RadioButton) rootView.findViewById(R.id.rb_onibus);
        rb_integracao = (RadioButton) rootView.findViewById(R.id.rb_integracao);
        tp = (ImageView) rootView.findViewById(R.id.tp);

        mRadioGroup = (RadioGroup) rootView.findViewById(R.id.rgroup);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initializeCheckeds();
        initializeUncheckeds();

        btnSalvar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (hora == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Você não cadastrou a hora da rotina de ida.")
                            .setPositiveButton("Cadastrar hora", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create();
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    if (diasAtivosCod[0] == 0 && diasAtivosCod[1] == 0 && diasAtivosCod[2] == 0 && diasAtivosCod[3] == 0 &&
                            diasAtivosCod[4] == 0 && diasAtivosCod[5] == 0 && diasAtivosCod[6] == 0) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                        builder2.setMessage("Você não cadastrou os dias da rotina de ida.")
                                .setPositiveButton("Cadastrar dias", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder2.create();
                        AlertDialog dialog2 = builder2.create();
                        dialog2.show();

                        if (!rb_onibus.isChecked() && !rb_integracao.isChecked()) {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(getContext());
                            builder3.setMessage("Você não cadastrou o tipo de viagem da rotina de ida.")
                                    .setPositiveButton("Cadastrar tipo", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            builder3.create();
                            AlertDialog dialog3 = builder3.create();
                            dialog3.show();


                        }

                    }


                } else {
                    Rotina rotina = new Rotina();

                    rotina.setHora(hora);
                    rotina.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    rotina.setFlag(0);
                    rotina.setTipo(1);
                    rotina.setDays(diasAtivosCod);
                    rotina.setLoginToken(LoginActivity.LOGIN_TOKEN);

                    if (ContaFragment.estudante == 0) {
                        if (mRadioGroup.getCheckedRadioButtonId() == R.id.rb_onibus) {
                            rotina.setValor(Tab.TARIFA_COMUM);
                        } else {
                            rotina.setValor(Tab.TARIFA_INTEGRACAO);
                        }
                    } else {
                        if (mRadioGroup.getCheckedRadioButtonId() == R.id.rb_onibus) {
                            rotina.setValor(Tab.TARIFA_ESTUDANTE);
                        } else {
                            rotina.setValor(Tab.TARIFA_COMUM);
                        }
                    }

                    RotinaPostRequest request = new RotinaPostRequest(getActivity());
                    request.execute(rotina);
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog();
                Toast.makeText(getContext(), hora, Toast.LENGTH_SHORT).show();
            }
        });


        for (int i = 0; i < btnDias.length; i++) {
            final int tmp = i;
            btnDias[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleImg(tmp);
                }
            });
        }
    }
}

