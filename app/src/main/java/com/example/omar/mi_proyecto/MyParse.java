package com.example.omar.mi_proyecto;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Omar on 22/01/2016.
 */
public class MyParse extends Application {
    private final String APPID ="ohGe5NCSShiJYNeAAFwezBNYB7vQii8wyqnE21LY" ;
    private final String CLIENTKEY = "00gfPorhPo6HfdXPNhvvBSnvEFgBEWf8cLp6dKWZ";

    @Override
    public void onCreate() {
        super.onCreate();
        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,APPID,CLIENTKEY);
    }
}
