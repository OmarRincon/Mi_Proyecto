package com.example.omar.mi_proyecto.BancoDatos;

/**
 * Created by Omar on 23/01/2016.
 */
public class PuntoActividad {
    private String inicio,fin,nombreActividad,pasos="";

    public PuntoActividad(){

    }

    public PuntoActividad(String inicio, String fin, String nombreActividad, String pasos) {
        this.inicio = inicio;
        this.fin = fin;
        this.nombreActividad = nombreActividad;
        this.pasos = pasos;
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
}
