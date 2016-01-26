package com.example.omar.mi_proyecto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar.mi_proyecto.BancoDatos.PuntoActividad;
import com.example.omar.mi_proyecto.R;

import java.util.List;

/**
 * Created by Omar on 23/01/2016.
 */
public class PuntoActividadArrayAdapter extends ArrayAdapter<PuntoActividad> {

    public PuntoActividadArrayAdapter(Context context, List<PuntoActividad> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(
                    R.layout.listado,
                    parent,
                    false);
        }

        //Obteniendo instancias de los elementos
        TextView actividad_pasos = (TextView) listItemView.findViewById(R.id.numPasos);
        TextView horario = (TextView) listItemView.findViewById(R.id.horario);


        //Obteniendo instancia de la Tarea en la posición actual
        PuntoActividad item = getItem(position);

        actividad_pasos.setText(item.getNombreActividad()+" "+ item.getPasos());
        horario.setText(item.getInicio()+"  "+item.getFin());

        //Devolver al ListView la fila creada
        return listItemView;
    }
}
