package com.example.omar.mi_proyecto.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.omar.mi_proyecto.Adapters.PuntoActividadArrayAdapter;
import com.example.omar.mi_proyecto.BancoDatos.AcumuladorPasos;
import com.example.omar.mi_proyecto.BancoDatos.PuntoActividad;
import com.example.omar.mi_proyecto.R;
import com.google.android.gms.fitness.data.DataSource;


public class DetallesFragment extends Fragment {
    View rootView;

    public DetallesFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_detalles, container, false);
        //Instancia del ListView
        ListView lista = (ListView) rootView.findViewById(R.id.listado);

        //Inicializar el adaptador con la fuente de datos
        ArrayAdapter adaptador = new PuntoActividadArrayAdapter(
                getContext(),
                AcumuladorPasos.getLista());

        //Relacionando la lista con el adaptador
        lista.setAdapter(adaptador);
        return rootView;
    }


}
