package br.com.consultai.Tabs;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import br.com.consultai.R;
import br.com.consultai.model.Cartao;
import br.com.consultai.serv.PostRotinaRequest;

/**
 * Created by leonardo.ribeiro on 16/11/2017.
 */


public class Tab_Ida extends Fragment {

    Button selec_dom, selec_seg, selec_ter, selec_qua, selec_qui, selec_sex, selec_sab;

    Boolean dom_ativo = false;
    Boolean seg_ativo = true;
    Boolean ter_ativo = true;
    Boolean qua_ativo = true;
    Boolean qui_ativo = true;
    Boolean sex_ativo = true;
    Boolean sab_ativo = false;

    String id_usuario;

    ImageView tp;

    public String weekday;
    public String valor;

    int hh, mm;
    int ss =00;

    String hora = "hh:mm:ss";

    Boolean estudante = false;

    public static final  String tarifa_comum = "3.80";
    public static final  String tarifa_integracao = "6.80";
    public static final  String tarifa_estudante = "1.90";
    public static final  String integracao_estudante = "3,80";
    public static final  String tipo = "0"; //ida


    RadioButton rb_onibus, rb_integracao;

    Button btnSalvar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ida, container, false);

        btnSalvar = (Button) rootView.findViewById(R.id.btnSalvar);


        id_usuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rb_onibus.isChecked()){
                    valor = tarifa_comum;
                }
                else if(rb_integracao.isChecked()){
                    valor = tarifa_integracao;
                }

                if (dom_ativo){
                    weekday = "0";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (seg_ativo){
                    weekday = "1";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (ter_ativo){
                    weekday = "2";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (qua_ativo){
                    weekday = "3";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (qui_ativo){
                    weekday = "4";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (sex_ativo){
                    weekday = "5";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }
                if (sab_ativo){
                    weekday = "6";
                    Toast.makeText(getContext(),"dia"+ weekday + "valor" + valor + "hora" + hora, Toast.LENGTH_SHORT).show();
                    PostRotinaRequest rotina = new PostRotinaRequest(getContext());
                    rotina.execute(id_usuario, hora, valor, weekday, tipo);
                }


            }
        });

        tp = (ImageView) rootView.findViewById(R.id.tp);

        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialog();
                Toast.makeText(getContext(), hora , Toast.LENGTH_SHORT).show();
            }
        });



        rb_onibus = (RadioButton) rootView.findViewById(R.id.rb_onibus);
        rb_integracao = (RadioButton) rootView.findViewById(R.id.rb_integracao);

        selec_dom = (Button) rootView.findViewById(R.id.selec_dom);
        selec_seg = (Button) rootView.findViewById(R.id.selec_seg);
        selec_ter = (Button) rootView.findViewById(R.id.selec_ter);
        selec_qua = (Button) rootView.findViewById(R.id.selec_qua);
        selec_qui = (Button) rootView.findViewById(R.id.selec_qui);
        selec_sex = (Button) rootView.findViewById(R.id.selec_sex);
        selec_sab = (Button) rootView.findViewById(R.id.selec_sab);




        selec_dom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dom_ativo == false){
                    selec_dom.setBackgroundResource(R.drawable.dom);
                    dom_ativo = true;
                }
                else if (dom_ativo == true){
                    selec_dom.setBackgroundResource(R.drawable.dom2);
                    dom_ativo = false;
                }

            }
        });

        selec_seg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seg_ativo == false){
                    selec_seg.setBackgroundResource(R.drawable.seg);
                    seg_ativo = true;
                }
                else if (seg_ativo == true){
                    selec_seg.setBackgroundResource(R.drawable.seg2);
                    seg_ativo = false;
                }

            }
        });

        selec_ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ter_ativo == false){
                    selec_ter.setBackgroundResource(R.drawable.ter);
                    ter_ativo = true;
                }
                else if (ter_ativo == true){
                    selec_ter.setBackgroundResource(R.drawable.ter2);
                    ter_ativo = false;
                }

            }
        });

        selec_qua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qua_ativo == false){
                    selec_qua.setBackgroundResource(R.drawable.qua);
                    qua_ativo = true;
                }
                else if (qua_ativo == true){
                    selec_qua.setBackgroundResource(R.drawable.qua2);
                    qua_ativo = false;
                }

            }
        });

        selec_qui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qui_ativo == false){
                    selec_qui.setBackgroundResource(R.drawable.qua);
                    qui_ativo = true;
                }
                else if (qui_ativo == true){
                    selec_qui.setBackgroundResource(R.drawable.qua2);
                    qui_ativo = false;
                }

            }
        });

        selec_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sex_ativo == false){
                    selec_sex.setBackgroundResource(R.drawable.seg);
                    sex_ativo = true;
                }
                else if (sex_ativo == true){
                    selec_sex.setBackgroundResource(R.drawable.seg2);
                    sex_ativo = false;
                }

            }
        });

        selec_sab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sab_ativo == false){
                    selec_sab.setBackgroundResource(R.drawable.seg);
                    sab_ativo = true;
                }
                else if (sab_ativo == true){
                    selec_sab.setBackgroundResource(R.drawable.seg2);
                    sab_ativo = false;
                }

            }
        });

        return rootView;
    }

    public void TimeDialog(){

        final Calendar c= Calendar.getInstance();
        hh=c.get(Calendar.HOUR_OF_DAY);
        mm=c.get(Calendar.MINUTE);

        TimePickerDialog time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
               String hr;
               String mn;

                if(horas < 10){
                    hr = "0"+String.valueOf(horas);
                }
                else{
                    hr = String.valueOf(horas);
                }
                if(minutos < 10){
                    mn = "0"+String.valueOf(minutos);
                }else{
                    mn = String.valueOf(minutos);
                }

                hora = hr+":"+mn+":"+"00";

            }
        }, hh,mm,true);
        time.show();
//        Log.v("time", hora);
    }


}

