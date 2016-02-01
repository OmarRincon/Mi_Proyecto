package com.example.omar.mi_proyecto;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.omar.mi_proyecto.Adapters.ViewPagerAdapter;
import com.example.omar.mi_proyecto.BancoDatos.AcumuladorPasos;
import com.example.omar.mi_proyecto.BancoDatos.PuntoActividad;
import com.example.omar.mi_proyecto.Plotter.Grafico;
import com.example.omar.mi_proyecto.fragmentos.DetallesFragment;
import com.example.omar.mi_proyecto.fragmentos.GraficoFragment;
import com.example.omar.mi_proyecto.fragmentos.PasosFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getTimeInstance;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BasicHistoryApi";
    private static final int REQUEST_OAUTH = 1;
    private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    //private int pasos;
    private GoogleApiClient mClient = null;
    private TextView txt;
    private boolean conectado=false;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_pasos,
            R.drawable.ic_grafico,
            R.drawable.ic_action_news};
    PuntoActividad pa=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case (0):
                        break;
                    case (1):

                        break;
                    case (2):

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });


        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        buildFitnessClient();
    }

    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.  What to do?
                                // Look at some data!!
                                //conectado = true;
                                new InsertAndVerifyDataTask().execute();
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                conectado=false;
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.i(TAG, "Google Play services connection failed. Cause: " +
                                result.toString());
                    }
                })
                .build();
    }

    private class InsertAndVerifyDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Begin by creating the query.
            DataReadRequest readRequest = queryFitnessData();
            AcumuladorPasos.borrarTodo();
                // [START read_dataset]
                // Invoke the History API to fetch the data with the query and await the result of
                // the read request.
                DataReadResult dataReadResult =
                        Fitness.HistoryApi.readData(mClient, readRequest).await(1, TimeUnit.MINUTES);
                // [END read_dataset]

                // For the sake of the sample, we'll print the data so we can see what we just added.
                // In general, logging fitness information should be avoided for privacy reasons.
                printData(dataReadResult);
            //AcumuladorPasos.subirred();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //mostramos los pasos
            if ((TextView)findViewById(R.id.pasos)!=null) {
                TextView tp = (TextView) findViewById(R.id.pasos);
                tp.setText(Integer.toString(AcumuladorPasos.pasosTotales()));
            }
            //no mostrar los pasos hasta que haya terminado de trabajar el hilo secundario
            AcumuladorPasos.subirred();

            Calendar calendario = Calendar.getInstance();
            double  hora = calendario.get(Calendar.HOUR_OF_DAY)+(calendario.get(Calendar.MINUTE)/(double)60);
            int pasos = AcumuladorPasos.pasosTotales();
            XYPlot plot = (XYPlot) findViewById(R.id.plot);
            // create a couple arrays of y-values to plot:
            Grafico g = new Grafico(plot,getBaseContext());
            if (hora<=1.52) {
                g.establecerDominio(0, 1.5);
            }else{
                g.establecerDominio(hora-1.5,hora);
            }
            g.intervalosx(0.25);

            /*if(pasos<=1000){
                g.establecerRango(0, 1000);
            }else{
                g.establecerRango(pasos-2000,pasos+2000);
            }*/
            g.establecerRango(0, 500);
            g.intervalosy(50);
            g.dibujar();
        }
    }

    private DataReadRequest queryFitnessData() {
        // [START build_read_data_request]
        // Setting a start and end date using a range of 1 week before this moment.
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //cal.set(2016,0,26,23,59,59);
        long endTime = cal.getTimeInMillis();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
        //cal.set(2016,0,26,0,0,0);
        long startTime = cal.getTimeInMillis();
        java.text.DateFormat dateFormat = getDateInstance();
        Log.i(TAG, "Range Start: " + dateFormat.format(startTime));
        Log.i(TAG, "Range End: " + dateFormat.format(endTime));

        DataReadRequest readRequest = new DataReadRequest.Builder()
                // The data request can specify multiple data types to return, effectively
                // combining multiple data queries into one call.
                // In this example, it's very unlikely that the request is for several hundred
                // datapoints each consisting of a few steps and a timestamp.  The more likely
                // scenario is wanting to see how many steps were walked per day, for 7 days.
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                        .aggregate(DataType.TYPE_ACTIVITY_SEGMENT,DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                        // Analogous to a "Group By" in SQL, defines how data should be aggregated.
                        // bucketByTime allows for a time span, whereas bucketBySession would allow
                        // bucketing by "sessions", which would need to be defined in code.
                //.bucketByTime(5, TimeUnit.MINUTES)
                .bucketByActivitySegment(3, TimeUnit.MINUTES)
                //.bucketByActivityType(5,TimeUnit.MINUTES)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        // [END build_read_
        return readRequest;
    }

    private void printData(DataReadResult dataReadResult) {
        // [START parse_read_data_result]
        // If the DataReadRequest object specified aggregated data, dataReadResult will be returned
        // as buckets containing DataSets, instead of just DataSets.
        if (dataReadResult.getBuckets().size() > 0) {
            Log.i(TAG, "Number of returned buckets of DataSets is: "
                    + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                pa = new PuntoActividad("0","0","0","0","0","0");
                for (DataSet dataSet : dataSets) {
                    dumpDataSet(dataSet);
                }
                Log.i(TAG, pa.getPasos() + "," + pa.getNombreActividad() + "," + pa.getDuracion() + "," + pa.getDistancia() + ","+pa.getInicio()+","+pa.getFin());
                AcumuladorPasos.add(pa);
            }
        } else if (dataReadResult.getDataSets().size() > 0) {
            Log.i(TAG, "Number of returned DataSets is: "
                    + dataReadResult.getDataSets().size());
            pa = new PuntoActividad();
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                dumpDataSet(dataSet);
            }
            Log.i(TAG, pa.getPasos() + "," + pa.getNombreActividad() + "," + pa.getDuracion() + "," + pa.getDistancia() + ","+pa.getInicio()+","+pa.getFin());
            AcumuladorPasos.add(pa);
        }

        // [END parse_read_data_result]
    }

    // [START parse_dataset]
    /*
    private void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        java.text.DateFormat dateFormat = getTimeInstance();
        //nuevo
        PuntoActividad pa = new PuntoActividad();
        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.i(TAG, "Data point: ");
            Log.i(TAG, "\tType: bucket"+i+":" + dp.getDataType().getName());
            //pa.setInicio(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            //pa.setFin(dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                //pasos = pasos + dp.getValue(field).asInt() ;
                //pa.setNombreActividad(field.getName());
                //pa.setPasos(dp.getValue(field).toString());
                Log.i(TAG, "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
            //AcumuladorPasos.add(pa);
        }
    }*/

    private void dumpDataSet(DataSet dataSet) {
        Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
        java.text.DateFormat dateFormat = getTimeInstance();
        for (DataPoint dp : dataSet.getDataPoints()) {

            Log.i(TAG, "Data point: ");
            Log.i(TAG, "\tType: bucket: " + dp.getDataType().getName());
            if (dp.getDataType().getName().equalsIgnoreCase("com.google.activity.summary")){
                pa.setInicio(dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                pa.setFin(dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
                Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
            }

            for(Field field : dp.getDataType().getFields()) {
                if (field.getName().equalsIgnoreCase("steps")){
                    pa.setPasos(dp.getValue(field).toString());
                    Log.i(TAG, "\tField: " + field.getName() +
                            " Value: " + dp.getValue(field));
                }if (field.getName().equalsIgnoreCase("distance")){
                    pa.setDistancia(dp.getValue(field).toString());
                    Log.i(TAG, "\tField: " + field.getName() +
                            " Value: " + dp.getValue(field));
                }
                if (field.getName().equalsIgnoreCase("activity")){
                    pa.setNombreActividad(dp.getValue(field).toString());
                    Log.i(TAG, "\tField: " + field.getName() +
                            " Value: " + dp.getValue(field));
                }
                if (field.getName().equalsIgnoreCase("duration")){
                    pa.setDuracion(dp.getValue(field).toString());
                    Log.i(TAG, "\tField: " + field.getName() +
                            " Value: " + dp.getValue(field));
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        //ViewPagerAdapter
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PasosFragment(), "PASOS");
        adapter.addFragment(new GraficoFragment(), "GRAFICO");
        adapter.addFragment(new DetallesFragment(), "DETALLES");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

}
