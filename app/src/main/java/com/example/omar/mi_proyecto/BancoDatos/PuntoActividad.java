package com.example.omar.mi_proyecto.BancoDatos;

/**
 * Created by Omar on 23/01/2016.
 */
public class PuntoActividad {
    private String inicio,fin,nombreActividad,pasos,duracion,distancia="";

    public PuntoActividad(){

    }

    public PuntoActividad(String inicio, String fin, String nombreActividad, String pasos, String duracion, String distancia) {
        this.inicio = inicio;
        this.fin = fin;
        this.nombreActividad = nombreActividad;
        this.pasos = pasos;
        this.duracion = duracion;
        this.distancia = distancia;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = devolverSegundos(duracion);
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String devolverSegundos(String milisegundos){
        String resultado="";
        int numero= Integer.parseInt(milisegundos);
        final int choras=3600000;
        final int cminutos = 60000;
        final int csegundos = 1000;
        int horas = numero/choras;
        int minutos = numero%choras/cminutos;
        int segundos = (numero%choras)%cminutos/csegundos;
        if (horas>0)
            resultado = horas+"h ";
        if (minutos>0)
            resultado = resultado + minutos+"min ";
        if (segundos>0)
            resultado = resultado +segundos+"seg";
        return resultado;
    }
}
