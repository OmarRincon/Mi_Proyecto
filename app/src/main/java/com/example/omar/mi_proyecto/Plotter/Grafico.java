package com.example.omar.mi_proyecto.Plotter;

import android.content.Context;
import android.graphics.Color;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.omar.mi_proyecto.BancoDatos.AcumuladorPasos;
import com.example.omar.mi_proyecto.R;

/**
 * Created by Omar on 25/01/2016.
 */
public class Grafico {

    private XYPlot plot;
    private Context context;

    public Grafico(XYPlot plot,Context context) {
        this.plot = plot;
        this.context = context;
        plot.clear();
    }

    public void establecerRango(double iniciox,double finx){
        plot.setRangeBoundaries(iniciox, finx, BoundaryMode.FIXED);
    }

    public void establecerDominio(double inicioy,double finy){
        plot.setDomainBoundaries(inicioy, finy, BoundaryMode.FIXED);
    }

    public void intervalosx(double num){
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, num);
    }

    public void intervalosy(double num){
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, num);
    }

    public void dibujar(){
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.BLUE);
        plot.getGraphWidget().getDomainGridLinePaint().setColor(Color.rgb(255, 0, 0));
        plot.getGraphWidget().getRangeGridLinePaint().setColor(Color.rgb(255, 0, 0));
        XYSeries series = new SimpleXYSeries(AcumuladorPasos.puntosxy(),SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED,"pasos/dia");
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(context,
                R.xml.line_point_formatter_with_labels);
        plot.addSeries(series, series1Format);
        plot.redraw();
    }


}
