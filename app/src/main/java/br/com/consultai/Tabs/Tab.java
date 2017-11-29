package br.com.consultai.Tabs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import br.com.consultai.MainActivity;
import br.com.consultai.R;
import br.com.consultai.get.GetRotinaRequest;
import br.com.consultai.model.Rotina;
import br.com.consultai.post.RotinaPostRequest;

/**
 * Created by renan.boni on 23/11/2017.
 */

public class Tab extends Fragment {

    public static Rotina ROTINA_IDA;
    public static Rotina ROTINA_VOLTA;

    protected boolean[] diasAtivos = new boolean[7];
    protected int[] diasAtivosCod = new int[7];

    protected int[] checkedImg = new int[7];
    protected int[] uncheckedImg = new int[7];

    protected Button[] btnDias = new Button[7];

    public static final double TARIFA_COMUM = 3.8;
    public static final double TARIFA_INTEGRACAO = 6.80;
    public static final double TARIFA_ESTUDANTE = 1.90;
    public static final double INTEGRACAO_ESTUDANTE = 3.80;

    public static final  String TIPO = "0"; //ida

    protected int hh, mm, ss = 0;
    protected String hora;
    protected Boolean estudante = false;

    protected RadioButton rb_onibus, rb_integracao;

    protected Button btnSalvar;

    Button btnCancelar;

    protected ImageView tp;

    protected String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ROTINA_IDA == null || ROTINA_VOLTA == null){
            GetRotinaRequest request = new GetRotinaRequest(getContext());
            request.execute();
        }
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

    protected void initializeButtons(View rootView){
        btnCancelar = rootView.findViewById(R.id.btnCancelar);
        btnDias[0] = rootView.findViewById(R.id.selec_dom);
        btnDias[1] = rootView.findViewById(R.id.selec_seg);
        btnDias[2] = rootView.findViewById(R.id.selec_ter);
        btnDias[3] = rootView.findViewById(R.id.selec_qua);
        btnDias[4] = rootView.findViewById(R.id.selec_qui);
        btnDias[5] = rootView.findViewById(R.id.selec_sex);
        btnDias[6] = rootView.findViewById(R.id.selec_sab);
    }

    protected void toggleImg(int day){
        if(!diasAtivos[day]){
            diasAtivos[day] = true;
            diasAtivosCod[day] = (day + 1);
            btnDias[day].setBackgroundResource(checkedImg[day]);
        }else{
            diasAtivos[day] = !diasAtivos[day];
            diasAtivosCod[day] = 0;
            btnDias[day].setBackgroundResource(uncheckedImg[day]);
        }
        RotinaPostRequest.INDEXES[day] = 1;
    }

    protected void TimeDialog(){

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

                hora = hr+ ":" +mn+ ":00";

            }
        }, hh,mm,true);
        time.show();
    }
}
