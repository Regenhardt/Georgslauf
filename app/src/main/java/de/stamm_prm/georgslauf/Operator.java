package de.stamm_prm.georgslauf;

import android.content.Context;
import android.os.Looper;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by Marlon on 01.03.2015.
 */
public class Operator extends Thread {
    public MapView mapView;
    public GoogleMap googleMap;
    public static Context context;

    private ArrayList<LatLng> postenKoordinaten;
    private LatLng[] postenkoordinatenArray;
    private ArrayList<String> postenNamen;
    private LatLngBounds initialMapBounds;
    public boolean newOp=true;

    public View[] infoWindows;
    public int[] IDs = {R.drawable.posten1,R.drawable.posten2,R.drawable.posten3,
            R.drawable.posten4,R.drawable.posten5,R.drawable.posten6,R.drawable.posten7,R.drawable.posten8,R.drawable.posten9,R.drawable.posten10,R.drawable.posten11,R.drawable.posten12,R.drawable.posten13,R.drawable.posten14,R.drawable.posten15,R.drawable.posten16,
            R.drawable.posten17};

    @Override
    public void run() {
        Looper.prepare();
        postenKoordinaten =new ArrayList<>();
        //TODO Koordinaten der Posten, später von Website laden
        postenKoordinaten.add(new LatLng(48.157421, 11.582899));    //Zentrale
        postenKoordinaten.add(new LatLng(48.159667, 11.593));       //Posten 1
        postenKoordinaten.add(new LatLng(48.159823,11.593945));     //Posten 2
        postenKoordinaten.add(new LatLng(48.161326,11.595808));    //Posten 3
        postenKoordinaten.add(new LatLng(48.160985,11.598580));    //Posten 4
        postenKoordinaten.add(new LatLng(48.156884,11.597366));    //Posten 5
        postenKoordinaten.add(new LatLng(48.154640,11.594268));    //Posten 6
        postenKoordinaten.add(new LatLng(48.150401,11.591482));    //Posten 7
        postenKoordinaten.add(new LatLng(48.148109,11.591321));    //Posten 8
        postenKoordinaten.add(new LatLng(48.146483,11.589995));    //Posten 9
        postenKoordinaten.add(new LatLng(48.144657,11.588054));    //Posten 10
        postenKoordinaten.add(new LatLng(48.145033,11.586612));    //Posten 11
        postenKoordinaten.add(new LatLng(48.145805,11.586060));    //Posten 12
        postenKoordinaten.add(new LatLng(48.149328,11.589102));    //Posten 13
        postenKoordinaten.add(new LatLng(48.152083,11.589228));    //Posten 14
        postenKoordinaten.add(new LatLng(48.153866,11.590134));    //Posten 15
        postenKoordinaten.add(new LatLng(48.156089,11.592193));    //Posten 16
        postenKoordinaten.add(new LatLng(48.157438,11.592676));    //Posten 17

        //TODO: Von Webspace:
/*
        try{
            //Direkter Link zur Liste, z.B. http://stamm-prm.de/posten.txt (oder .html um Codierung zu vermeiden?)
            URL url = new URL("http://stamm-prm.de/index.php?id=86");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String einPosten;
            while((einPosten=in.readLine())!=null){
                postenKoordinaten.add(new LatLng(Double.parseDouble(einPosten.substring(0,
                        einPosten.indexOf(","))),Double.parseDouble(einPosten.substring(einPosten
                        .indexOf(",")+1))));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        } */

        infoWindows=new View[postenKoordinaten.size()];

        postenkoordinatenArray= postenKoordinaten.toArray(new LatLng[postenKoordinaten.size()]);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int index=1;index< postenKoordinaten.size();index++){
            builder.include(postenKoordinaten.get(index));
        }
        initialMapBounds = builder.build();


        //Debug*******************
        MainActivity.sendToast=true;
        //**********************
    }

    public LatLng[] getPosten(){
        return  postenkoordinatenArray;
    }
    public LatLngBounds getInitialMapBounds() {
        return initialMapBounds;
    }

    public void buildInfoWindows(Context that){

    }

    public boolean isNew(){
        return newOp;
    }

}
