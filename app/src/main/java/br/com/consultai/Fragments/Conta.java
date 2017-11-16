package br.com.consultai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 14/11/2017.
 */

public class Conta extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_conta, null);


        return view;


    }
}
