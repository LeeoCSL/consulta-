package br.com.consultai.Tabs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
                Rotina rotina = new Rotina();

                rotina.setHora(hora);
                rotina.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
                rotina.setFlag(0);
                rotina.setTipo(1);
                rotina.setDays(diasAtivosCod);
                rotina.setLoginToken(LoginActivity.LOGIN_TOKEN);

                if(ContaFragment.estudante == 0){
                    if(mRadioGroup.getCheckedRadioButtonId() == R.id.rb_onibus){
                        rotina.setValor(Tab.TARIFA_COMUM);
                    }else{
                        rotina.setValor(Tab.TARIFA_INTEGRACAO);
                    }
                }else{
                    if(mRadioGroup.getCheckedRadioButtonId() == R.id.rb_onibus){
                        rotina.setValor(Tab.TARIFA_ESTUDANTE);
                    }else{
                        rotina.setValor(Tab.TARIFA_COMUM);
                    }
                }

                RotinaPostRequest request = new RotinaPostRequest(getContext());
                request.execute(rotina);

                getActivity().finish();
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
                Toast.makeText(getContext(), hora , Toast.LENGTH_SHORT).show();
            }
        });


        for(int i = 0; i < btnDias.length; i++){
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

