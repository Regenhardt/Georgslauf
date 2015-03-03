package de.stamm_prm.georgslauf;

import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

/**
 * Created by Marlon on 01.03.2015.
 */
public class Operator extends Thread {
    private ArrayList<LatLng> postenKoordinaten;
    private LatLng[] postenkoordinatenArray;
    private ArrayList<String> postenNamen;
    private LatLngBounds initialMapBounds;
    @Override
    public void run() {
        Looper.prepare();
        postenKoordinaten =new ArrayList<>();
        //Posten laden, später von Webspace
        postenKoordinaten.add(new LatLng(48.161347, 11.580134));
        postenKoordinaten.add(new LatLng(48.161112, 11.580046));
        postenKoordinaten.add(new LatLng(48.161510, 11.579614));
        postenKoordinaten.add(new LatLng(48.161824, 11.579520));
        postenKoordinaten.add(new LatLng(48.161851, 11.579997));
        postenKoordinaten.add(new LatLng(48.161871, 11.580327));
        postenKoordinaten.add(new LatLng(48.161833, 11.580971));
        postenKoordinaten.add(new LatLng(48.162293, 11.581266));
        postenKoordinaten.add(new LatLng(48.162538, 11.582555));
        postenKoordinaten.add(new LatLng(48.162189, 11.582868));
        postenKoordinaten.add(new LatLng(48.161925, 11.582158));
        postenKoordinaten.add(new LatLng(48.161522, 11.581855));
        postenKoordinaten.add(new LatLng(48.161220, 11.580699));
        postenKoordinaten.add(new LatLng(48.161150, 11.580412));

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

        postenkoordinatenArray=postenKoordinaten.toArray(new LatLng[0]);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int index=0;index< postenKoordinaten.size();index++){
            builder.include(postenKoordinaten.get(index));
        }
        initialMapBounds = builder.build();

        MainActivity.sendToast=true;

    }

    public LatLng[] getPosten(){
        return  postenkoordinatenArray;
    }
    public LatLngBounds getInitialMapBounds() {
        return initialMapBounds;
    }



}
