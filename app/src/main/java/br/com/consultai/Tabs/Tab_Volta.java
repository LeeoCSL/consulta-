package br.com.consultai.Tabs;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import br.com.consultai.R;

public class Tab_Volta extends Tab {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ida, container, false);

        initializeCheckeds();
        initializeUncheckeds();
        initializeButtons(rootView);

        btnSalvar = (Button) rootView.findViewById(R.id.btnSalvar);
        rb_onibus = (RadioButton) rootView.findViewById(R.id.rb_onibus);
        rb_integracao = (RadioButton) rootView.findViewById(R.id.rb_integracao);
        tp = (ImageView) rootView.findViewById(R.id.tp);

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

        return rootView;
    }


    public void onResume(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ida, container, false);
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }
}

