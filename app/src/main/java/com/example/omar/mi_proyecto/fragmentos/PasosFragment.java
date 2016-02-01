package com.example.omar.mi_proyecto.fragmentos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omar.mi_proyecto.BancoDatos.AcumuladorPasos;
import com.example.omar.mi_proyecto.R;

public class PasosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    public PasosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_pasos, container, false);
        TextView tp = (TextView) rootview.findViewById(R.id.pasos);
        if(AcumuladorPasos.pasosTotales()>0) {
            tp.setText(Integer.toString(AcumuladorPasos.pasosTotales()));
        }
        return rootview;
    }



}
