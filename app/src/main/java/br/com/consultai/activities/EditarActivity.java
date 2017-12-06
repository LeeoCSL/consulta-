package br.com.consultai.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import br.com.consultai.Fragments.EditarFragment;
import br.com.consultai.Fragments.MainFragment;
import br.com.consultai.Giroscopio;
import br.com.consultai.R;
import br.com.consultai.model.Rotina;
import br.com.consultai.post.RotinaPostRequest;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditarActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;


    public static Rotina ROTINA_IDA;
    public static Rotina ROTINA_VOLTA;

    protected Button[] btnDias = new Button[7];

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);




        initializeCheckeds();
        initializeUncheckeds();
        initializeButtons();

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

        mClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(1);
            }
        });

        mGroupIda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_onibus_trilho_ida:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor1 = 1.90;
                        }else{
                            mValor1 = 3.80;
                        }
                        break;
                    case R.id.rb_integracao_ida:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor1 = 3.80;
                        }else{
                            mValor1 = 6.80;
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
                            mValor2 = 1.90;
                        }else{
                            mValor2 = 3.80;
                        }
                        break;
                    case R.id.rb_integracao_volta:
                        if(MainFragment.ESTUDANTE == 1){
                            mValor2 = 3.80;
                        }else{
                            mValor2 = 6.80;
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

        Log.i("HORA_ID", ROTINA_IDA.getHora());

        mHoraRotina1 = ROTINA_IDA.getHora().substring(0,2) + "h" +
                ROTINA_IDA.getHora().substring(3, 5) + "min";
        mHoraSelecionada1.setText(mHoraRotina1);

        double valorIda = ROTINA_IDA.getValor();

        if(valorIda == 1.90 || valorIda == 3.80){
            mOnibusTrilhoIda.setChecked(true);
        }else{
            mIntegracaoIda.setChecked(true);
        }

        //////////////////////////////////////
        mHoraRotina2 = ROTINA_VOLTA.getHora().substring(0,2) + "h" +
                ROTINA_VOLTA.getHora().substring(3, 5) + "min";
        mHoraSelecionada2.setText(mHoraRotina2);

        double valorVolta = ROTINA_VOLTA.getValor();

        if(valorVolta == 1.90 || valorVolta == 3.80){
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


                    Bundle bundle = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle.putString("velocidade_digitacao", null);
                    bundle.putString("velocidade_clique", null);
                    bundle.putString("posicao_clique", null);
                    bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle.putString("id_celular", null);
                    mFirebaseAnalytics.logEvent("selecao_hora_ida", bundle);
                    giro.cancel(true);


                }else{
                    mHoraRotina2 = hr+ ":" +mn+ ":00";
                    mHoraSelecionada2.setText(hr+"h" +mn+ "min");

                    Giroscopio giro = new Giroscopio(EditarActivity.this);
                    giro.execute();


                    Bundle bundle = new Bundle();
                    bundle.putString("giroscopio", Giroscopio.gyro);
                    bundle.putString("velocidade_digitacao", null);
                    bundle.putString("velocidade_clique", null);
                    bundle.putString("posicao_clique", null);
                    bundle.putString("id_usuario", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    bundle.putString("id_celular", null);
                    mFirebaseAnalytics.logEvent("selecao_hora_volta", bundle);
                    giro.cancel(true);
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

            Rotina rotinaVolta = new Rotina();
            rotinaVolta.setIdRotina(ROTINA_VOLTA.getIdRotina());
            rotinaVolta.setHora(mHoraRotina2);
            rotinaVolta.setDays(diasAtivosCod);
            rotinaVolta.setValor(mValor2);
            rotinaVolta.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaVolta.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaVolta.setTipo(1);

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


            Rotina rotinaVolta = new Rotina();
            rotinaVolta.setHora(mHoraRotina2);
            rotinaVolta.setDays(diasAtivosCod);
            rotinaVolta.setValor(mValor2);
            rotinaVolta.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rotinaVolta.setLoginToken(LoginActivity.LOGIN_TOKEN);
            rotinaVolta.setTipo(1);

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
}