package br.com.consultai.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 16/11/2017.
 */

public class Tab_Volta extends Fragment {

    Button selec_dom, selec_seg, selec_ter, selec_qua, selec_qui, selec_sex, selec_sab;

    Boolean dom_ativo = false;
    Boolean seg_ativo = true;
    Boolean ter_ativo = true;
    Boolean qua_ativo = true;
    Boolean qui_ativo = true;
    Boolean sex_ativo = true;
    Boolean sab_ativo = false;

    public static final  String tarifa_comum = "3.80";
    public static final  String tarifa_integracao = "3.80";
    public static final  String tarifa_estudante = "1.90";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab_volta, container, false);

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
}