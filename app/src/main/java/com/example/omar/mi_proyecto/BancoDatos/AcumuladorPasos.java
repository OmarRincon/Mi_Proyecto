package com.example.omar.mi_proyecto.BancoDatos;

import com.parse.ParseObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 22/01/2016.
 */
public final class AcumuladorPasos {
    static List<PuntoActividad> detallesActividad= new ArrayList<PuntoActividad>();



    public AcumuladorPasos() {

    }

    public static void add(PuntoActividad pa){
        detallesActividad.add(pa);
    }

    public static void borrarTodo(){
        detallesActividad.clear();
    }

    public static int pasosTotales(){
        int total=0;
        for (PuntoActividad pa: detallesActividad){
            total = total + Integer.parseInt(pa.getPasos());
        }
        return total;
    }

    public static List<PuntoActividad> getLista(){
        return detallesActividad;
    }

    //si quiero diagrama xy
    /*public static Double[] ejex(){
        Double [] x = new Double[detallesActividad.size()*2];
        int cont=-1;
        for (PuntoActividad pa: detallesActividad){
            cont++;
            x[cont]= convertirHora(pa.getInicio());
            cont++;
            x[cont]= convertirHora(pa.getFin());
        }
      return x;
    }

    public static Integer[] ejey(){
        Integer [] y = new Integer[detallesActividad.size()*2];
        int cont=-1;
        int valor = 0;
        for (PuntoActividad pa: detallesActividad){
            cont++;
            if (cont==0 || cont %2 ==0){
                y[cont]=valor;
            }
            cont++;
            if(cont%2!=0){
               valor =y[cont-1]+Integer.parseInt(pa.getPasos());
                y[cont]=valor;
            }
        }
        return y;
    }*/

    //para diagrama de barras
    public static Double[] ejex(){
        Double [] x = new Double[detallesActividad.size()*4];
        int cont=-1;
        for (PuntoActividad pa: detallesActividad){
            cont++;
            x[cont]= convertirHora(pa.getInicio());
            cont++;
            x[cont]= convertirHora(pa.getInicio());
            cont++;
            x[cont]= convertirHora(pa.getFin());
            cont++;
            x[cont]= convertirHora(pa.getFin());
        }
        return x;
    }

    public static Integer[] ejey(){
        Integer [] y = new Integer[detallesActividad.size()*4];
        int cont=-1;
        Integer valor = 0;
        for (PuntoActividad pa: detallesActividad){
            cont++;
            y[cont]=0;
            cont++;
            y[cont]=Integer.parseInt(pa.getPasos());
            cont++;
            y[cont]=Integer.parseInt(pa.getPasos());
            cont++;
            y[cont]=0;
        }
        return y;
    }

    public static List<Number> puntosxy(){
        List<Number> listadoxy = new ArrayList<>();
        Double[] ejex = ejex();
        Integer[] ejey = ejey();
        for (int i=0;i<detallesActividad.size()*4;i++){
            listadoxy.add(ejex[i]);
            listadoxy.add(ejey[i]);
        }
        return listadoxy;
    }

    public static double convertirHora(String horaCompleta){
        //variables a partir de la cadena
        String cadena = horaCompleta;
        String separador =":";
        String[] arreglo = cadena.split(separador);
        double valor = Integer.parseInt(arreglo[0])+ (Integer.parseInt(arreglo[1]) / (double)60);
        String cad = String.valueOf(valor);
        if (cadena.length()== 8){
            cad = cad.substring(0, 4);
        }else{
            cad = cad.substring(0, 3);
        }
        valor = Double.parseDouble(cad);
        return valor;

    }

    public static void subirred(){
        ParseObject datepoint;
        /*
        for(PuntoActividad pa: detallesActividad){
            datepoint = new ParseObject("PuntoActividad");
            datepoint.put("inicio",pa.getInicio());
            datepoint.put("fin",pa.getFin());
            datepoint.put("Actividad",pa.getNombreActividad());
            datepoint.put("pasos",pa.getPasos());
            datepoint.saveInBackground();
        }*/
    }
    public static boolean actividadNueva(){
        return true;
    }


}
