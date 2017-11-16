package br.com.consultai.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.consultai.R;

/**
 * Created by leonardo.ribeiro on 16/11/2017.
 */


public class Tab_Ida extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_ida, container, false);

        return rootView;
    }
}

