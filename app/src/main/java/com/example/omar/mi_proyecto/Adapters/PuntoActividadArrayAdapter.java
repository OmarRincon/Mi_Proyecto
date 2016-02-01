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

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.omar.mi_proyecto.R.drawable.ic_action_news;
import static com.example.omar.mi_proyecto.R.drawable.ic_car;
import static com.example.omar.mi_proyecto.R.drawable.ic_pasos;

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
        TextView actividad = (TextView) listItemView.findViewById(R.id.actividad);
        TextView inicio = (TextView) listItemView.findViewById(R.id.inicio);
        TextView pasos = (TextView) listItemView.findViewById(R.id.pasos);
        TextView distancia = (TextView) listItemView.findViewById(R.id.distancia);
        TextView duracion = (TextView) listItemView.findViewById(R.id.duracion);
        CircleImageView ic_actividad = (CircleImageView) listItemView.findViewById(R.id.category);
        CircleImageView ipasos = (CircleImageView) listItemView.findViewById(R.id.ipasos);
        CircleImageView idistancia = (CircleImageView) listItemView.findViewById(R.id.idistancia);
        CircleImageView iduracion = (CircleImageView) listItemView.findViewById(R.id.iduracion);


        //Obteniendo instancia de la Tarea en la posición actual
        PuntoActividad item = getItem(position);
        //aqui es donde vamos a gestionar que tipo de actividad mostrar dependiento de el numero que salga
        //------------------------------------------------------------------------------------------------
        if (item.getNombreActividad().equalsIgnoreCase("0")){
            //vehiculo
            ic_actividad.setImageResource(R.drawable.ic_car);
            actividad.setText("Conducir");
            pasos.setText(" 0");

        }else if (item.getNombreActividad().equalsIgnoreCase("1")){
            //ciclismo
            ic_actividad.setImageResource(R.drawable.ic_bike);
            actividad.setText("Bicicleta");
            pasos.setText(" 0");
        }
        else if (item.getNombreActividad().equalsIgnoreCase("2")||item.getNombreActividad().equalsIgnoreCase("7") ){
            //caminar
            ic_actividad.setImageResource(R.drawable.ic_person_walking);
            actividad.setText("Andar");
            pasos.setText(" "+ item.getPasos());

        } else if (item.getNombreActividad().equalsIgnoreCase("8")){
            //correr
            ic_actividad.setImageResource(R.drawable.ic_running);
            actividad.setText("Correr");
            pasos.setText(" "+item.getPasos());
        }

        iduracion.setImageResource(R.drawable.ic_reloj);
        ipasos.setImageResource(R.drawable.ic_pisadas);
        duracion.setText(" " + item.getDuracion());
        idistancia.setImageResource(R.drawable.ic_kilometros);
        distancia.setText(" "+item.getDistancia());
        inicio.setText(" "+item.getInicio());

        //------------------------------------------------------------------------------------------------


        //Devolver al ListView la fila creada
        return listItemView;
    }
}
