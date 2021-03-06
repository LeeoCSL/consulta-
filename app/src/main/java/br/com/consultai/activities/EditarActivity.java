package br.com.consultai.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.consultai.Acc;
import br.com.consultai.fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.R;
import br.com.consultai.model.Rotina;
import br.com.consultai.post.RotinaPostRequest;
import br.com.consultai.utils.CalcHora;
import br.com.consultai.utils.UtilTempoDigitacao;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditarActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public static Rotina ROTINA_IDA;
    public static Rotina ROTINA_VOLTA;

    protected Button[] btnDias = new Button[7];
    public static String coords;

    protected boolean[] diasAtivos = new boolean[7];
    protected int[] diasAtivosCod = new int[7];

    protected int[] checkedImg = new int[7];
    protected int[] uncheckedImg = new int[7];

    protected int hh, mm, ss = 0;
    protected String hora;

    @BindView(R.id.tp_1)
    ImageView mClock1;

    @BindView(R.id.tp_2)
    ImageView mClock2;

    // ROTINA 1 VARIAVEIS
    @BindView(R.id.hora_selecionada_1)
    TextView mHoraSelecionada1;

    @BindView(R.id.rgroup_ida)
    RadioGroup mGroupIda;

    @BindView(R.id.rb_onibus_trilho_ida)
    RadioButton mOnibusTrilhoIda;

    @BindView(R.id.rb_integracao_ida)
    RadioButton mIntegracaoIda;

    private String mHoraRotina1;
    private double mValor1;

    // ROTINA 2 VARIAVEIS
    @BindView(R.id.hora_selecionada_2)
    TextView mHoraSelecionada2;

    @BindView(R.id.rgroup_volta)
    RadioGroup mGroupVolta;

    private String mHoraRotina2;
    private double mValor2;

    @BindView(R.id.rb_onibus_trilho_volta)
    RadioButton mOnibusTrilhoVolta;

    @BindView(R.id.rb_integracao_volta)
    RadioButton mIntegracaoVolta;

    public static String tempoCliqueIda, tempoCliqueVolta, tempoCliqueRotina;

    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initializeCheckeds();
        initializeUncheckeds();
        initializeButtons();

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }

                tempoCliqueRotina = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
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

        mClock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(0);
            }
        });

        mClock1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }
                tempoCliqueIda = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });



        mClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(1);
            }
        });

        mClock2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    UtilTempoDigitacao.inicioTempo();
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    UtilTempoDigitacao.fimTempo();

                }
                tempoCliqueVolta = String.valueOf(UtilTempoDigitacao.dtfs);
                return false;
            }
        });

        mGroupIda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_onibus_trilho_ida:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor1 = 2.00;
                        }else{
                            mValor1 = 4.00;
                        }
                        break;
                    case R.id.rb_integracao_ida:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor1 = 4.00;
                        }else{
                            mValor1 = 6.96;
                        }
                        break;
                }
            }
        });

        mGroupVolta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_onibus_trilho_volta:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor2 = 2.00;
                        }else{
                            mValor2 = 4.00;
                        }
                        break;
                    case R.id.rb_integracao_volta:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor2 = 4.00;
                        }else{
                            mValor2 = 6.96;
                        }
                        break;
                }
            }
        });

        if(ROTINA_IDA != null && ROTINA_VOLTA != null){
            loadRotinas();
        }
    }

    private void loadRotinas(){
        int[] diasUso = ROTINA_IDA.getDays();
        diasAtivosCod = ROTINA_IDA.getDays();

        for(int i = 0; i < diasUso.length; i++){
            if(diasUso[i] != 0){
                btnDias[i].setBackgroundResource(checkedImg[i]);
            }else{
                btnDias[i].setBackgroundResource(uncheckedImg[i]);
            }
        }

        mHoraRotina1 = ROTINA_IDA.getHora().substring(0,2) + ":" +
                ROTINA_IDA.getHora().substring(3, 5) + ":00";
        mHoraSelecionada1.setText(mHoraRotina1);

        double valorIda = ROTINA_IDA.getValor();

        if(valorIda == 2.00 || valorIda == 4.00){
            mOnibusTrilhoIda.setChecked(true);
        }else{
            mIntegracaoIda.setChecked(true);
        }

        //////////////////////////////////////
        mHoraRotina2 = ROTINA_VOLTA.getHora().substring(0,2) + ":" +
                ROTINA_VOLTA.getHora().substring(3, 5) + ":00";
        mHoraSelecionada2.setText(mHoraRotina2);

        double valorVolta = ROTINA_VOLTA.getValor();

        if(valorVolta == 2.00 || valorVolta == 4.00){
            mOnibusTrilhoVolta.setChecked(true);
        }else{
            mIntegracaoVolta.setChecked(true);
        }
    }

    protected void initializeCheckeds(){
        String mDrawableName = "checked";

        for(int i = 0; i < 7; i++){
            checkedImg[i] = getResources().getIdentifier(mDrawableName + i , "drawable", getPackageName());
        }
    }

    protected void initializeUncheckeds(){
        String mDrawableName = "unchecked";

        for(int i = 0; i < 7; i++){
            uncheckedImg[i] = getResources().getIdentifier(mDrawableName + i , "drawable", getPackageName());
        }
    }

    protected void initializeButtons(){
        btnDias[0] = (Button) findViewById(R.id.selec_dom);
        btnDias[1] = (Button) findViewById(R.id.selec_seg);
        btnDias[2] = (Button) findViewById(R.id.selec_ter);
        btnDias[3] = (Button) findViewById(R.id.selec_qua);
        btnDias[4] = (Button) findViewById(R.id.selec_qui);
        btnDias[5] = (Button) findViewById(R.id.selec_sex);
        btnDias[6] = (Button) findViewById(R.id.selec_sab);
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
    }

    protected void showTimeDialog(final int tipoRotina){

        final Calendar c= Calendar.getInstance();

        hh=c.get(Calendar.HOUR_OF_DAY);
        mm=c.get(Calendar.MINUTE);

        TimePickerDialog time = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
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



                if(tipoRotina == 0){
                    mHoraRotina1 = hr+ ":" +mn+ ":00";
                    mHoraSelecionada1.setText(hr+"h" +mn+ "min");


                    Giroscopio giro = new Giroscopio(EditarActivity.this);
                    giro.execute();
                    Acc acc = new Acc(EditarActivity.this);
                    acc.execute();

                    Bundle bundle = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle.putString("acelerometro", Acc.Acc);
                    bundle.putString("velocidade_clique", tempoCliqueIda);
                    bundle.putString("posicao_clique", EditarActivity.coords);
                    bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle.putString("id_celular", Build.SERIAL);
                    mFirebaseAnalytics.logEvent("selecao_hora_ida", bundle);
                    giro.cancel(true);
                    acc.cancel(true);


                }else{
                    mHoraRotina2 = hr+ ":" +mn+ ":00";
                    mHoraSelecionada2.setText(hr+"h" +mn+ "min");

                    Giroscopio giro = new Giroscopio(EditarActivity.this);
                    giro.execute();
                    Acc acc = new Acc(EditarActivity.this);
                    acc.execute();
                    Bundle bundle = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle.putString("acelerometro", Acc.Acc);
                    bundle.putString("velocidade_clique", tempoCliqueVolta);
                    bundle.putString("posicao_clique", EditarActivity.coords);
                    bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle.putString("id_celular", Build.SERIAL);
                    mFirebaseAnalytics.logEvent("selecao_hora_volta", bundle);
                    giro.cancel(true);
                    acc.cancel(true);

                }
            }
        }, hh,mm,true);
        time.show();
    }

    public void save(View view){
        validateTextFromEditText();
    }

    private void validateTextFromEditText(){

        int count = 0;
        for(int i = 0; i < diasAtivosCod.length; i++){
            count += diasAtivosCod[i];
        }
        if(count == 0){
            createAlertDialog("Por favor, escolha pelo menos um dia de uso.");
            return;
        }

        if(mHoraRotina1 == null){
            createAlertDialog("Por favor, escolha o horário da sua viagem de ida.");
            return;
        }

        if(mHoraRotina2 == null){
            createAlertDialog("Por favor, escolha o horário da sua viagem de volta.");
            return;
        }

        if(mGroupIda.getCheckedRadioButtonId() == -1){
            createAlertDialog("Selecione o meio utilizado na ida.");
            return;
        }

        if(mGroupVolta.getCheckedRadioButtonId() == -1){
            createAlertDialog("Selecione o meio utilizado na volta.");
            return;
        }

        if(ROTINA_IDA != null && ROTINA_VOLTA != null){
            Rotina rotinaIda = new Rotina();
            rotinaIda.setIdRotina(ROTINA_IDA.getIdRotina());
            rotinaIda.setHora(mHoraRotina1);
            rotinaIda.setDays(diasAtivosCod);
            rotinaIda.setValor(mValor1);
            rotinaIda.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaIda.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaIda.setTipo(0);

            CalcHora.inicioTempo();

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            Date dt;
            try {
                dt = dateFormat.parse(mHoraRotina1);


                Log.v("hora", String.valueOf(dt));

                if(CalcHora.horaAtual.compareTo(dt) > 0){
                    rotinaIda.setFlag(1);
                }
                if(CalcHora.horaAtual.compareTo(dt) == 0){
                    rotinaIda.setFlag(0);
                }
                if(CalcHora.horaAtual.compareTo(dt) < 0){
                    rotinaIda.setFlag(0);
                }

            } catch(ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



//            Log.v("rotina", rotinaIda.getIdRotina()+"/"+rotinaIda.getHora()+"/" +  rotinaIda.getDays()+"/"  +  rotinaIda.getValor()
//                    +"/" + rotinaIda.getIdUsuario()+"/"  +  rotinaIda.getLoginToken()+"/"  + rotinaIda.getTipo());

            Rotina rotinaVolta = new Rotina();
            rotinaVolta.setIdRotina(ROTINA_VOLTA.getIdRotina());
            rotinaVolta.setHora(mHoraRotina2);
            rotinaVolta.setDays(diasAtivosCod);
            rotinaVolta.setValor(mValor2);
            rotinaVolta.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaVolta.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaVolta.setTipo(1);

            CalcHora.inicioTempo();

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
            Date dt2;
            try {
                dt2 = dateFormat2.parse(mHoraRotina2);


                Log.v("hora", String.valueOf(dt2));

                if(CalcHora.horaAtual.compareTo(dt2) > 0){
                    rotinaIda.setFlag(1);
                }
                if(CalcHora.horaAtual.compareTo(dt2) == 0){
                    rotinaIda.setFlag(0);
                }
                if(CalcHora.horaAtual.compareTo(dt2) < 0){
                    rotinaIda.setFlag(0);
                }

            } catch(ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            Log.v("rotina", rotinaVolta.getIdRotina()+"/" +rotinaVolta.getHora()+"/" +  rotinaVolta.getDays()+"/"  +  rotinaVolta.getValor()
//                    +"/" + rotinaVolta.getIdUsuario()+"/"  +  rotinaVolta.getLoginToken()+"/"  + rotinaVolta.getTipo());

            RotinaPostRequest request = new RotinaPostRequest(this);
            request.execute(rotinaIda, rotinaVolta);
        }else{
            Rotina rotinaIda = new Rotina();
            rotinaIda.setHora(mHoraRotina1);
            rotinaIda.setDays(diasAtivosCod);
            rotinaIda.setValor(mValor1);
            rotinaIda.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaIda.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaIda.setTipo(0);

            CalcHora.inicioTempo();

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            Date dt;
            try {
                dt = dateFormat.parse(mHoraRotina1);


                Log.v("hora", String.valueOf(dt));

                if(CalcHora.horaAtual.compareTo(dt) > 0){
                    rotinaIda.setFlag(1);
                }
                if(CalcHora.horaAtual.compareTo(dt) == 0){
                    rotinaIda.setFlag(0);
                }
                if(CalcHora.horaAtual.compareTo(dt) < 0){
                    rotinaIda.setFlag(0);
                }

            } catch(ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Rotina rotinaVolta = new Rotina();
            rotinaVolta.setHora(mHoraRotina2);
            rotinaVolta.setDays(diasAtivosCod);
            rotinaVolta.setValor(mValor2);
            rotinaVolta.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaVolta.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaVolta.setTipo(1);

            CalcHora.inicioTempo();

            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
            Date dt2;
            try {
                dt2 = dateFormat2.parse(mHoraRotina2);


                Log.v("hora", String.valueOf(dt2));

                if(CalcHora.horaAtual.compareTo(dt2) > 0){
                    rotinaIda.setFlag(1);
                }
                if(CalcHora.horaAtual.compareTo(dt2) == 0){
                    rotinaIda.setFlag(0);
                }
                if(CalcHora.horaAtual.compareTo(dt2) < 0){
                    rotinaIda.setFlag(0);
                }

            } catch(ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            RotinaPostRequest request = new RotinaPostRequest(this);
            request.execute(rotinaIda, rotinaVolta);
        }
    }

    private void createAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.create();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void back(View view){
        finish();
    }

    @Override
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