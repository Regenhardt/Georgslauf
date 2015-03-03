package de.stamm_prm.georgslauf;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Marlon on 10.02.2015.
 */
public class GoogleMapFragment extends Fragment {

    static LatLng[] postenArray;

    private GoogleMap googleMap=null;
    MapView mapView;
    LatLngBounds bounds;
    final int padding = 50;

    Marker[] markers;

    Handler handler;
    private Runnable postenLaden = new Runnable() {
        @Override
        public void run() {

            /* Jetzt durch operator
             postenArray[0]=new LatLng(48.161347, 11.580134);
             postenArray[1]=new LatLng(48.161112, 11.580046);
             postenArray[2]=new LatLng(48.161510, 11.579614);
             postenArray[3]=new LatLng(48.161824, 11.579520);
             postenArray[4]=new LatLng(48.161851, 11.579997);
             postenArray[5]=new LatLng(48.161871, 11.580327);
             postenArray[6]=new LatLng(48.161833, 11.580971);
             postenArray[7]=new LatLng(48.162293, 11.581266);
             postenArray[8]=new LatLng(48.162538, 11.582555);
             postenArray[9]=new LatLng(48.162189, 11.582868);
            postenArray[10]=new LatLng(48.161925, 11.582158);
            postenArray[11]=new LatLng(48.161522, 11.581855);
            postenArray[12]=new LatLng(48.161220, 11.580699);
            postenArray[13]=new LatLng(48.161150, 11.580412);
            */
            postenArray=MainActivity.operator.getPosten();
            markers=new Marker[postenArray.length];

/* Jetzt durch operator
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (int m =0;m<postenArray.length();m++) {
                builder.include(postenArray[m]);
            }
            final LatLngBounds bounds = builder.build();
            */
            bounds=MainActivity.operator.getInitialMapBounds();

            //Marker setzen
                //Zentrale
            markers[0]=googleMap.addMarker(new MarkerOptions().position(postenArray[0]).title
                    ("" + postenArray.length)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                //Posten
            for(int i = 1; i<14; i++)
                markers[i]=googleMap.addMarker(new MarkerOptions().position(postenArray[i]).title
                        ("Posten " +
                                "" + i).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                        .HUE_GREEN)));



            //Anfangsposition initialisieren
            try {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            }catch (IllegalStateException ise) {
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                    @Override
                    public void onMapLoaded() {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                                padding));
                    }
                });
            }
        }
    };

    public static GoogleMapFragment newInstance(){
        GoogleMapFragment retval =new GoogleMapFragment();
        return retval;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        handler=new Handler();
        View retval = inflater.inflate(R.layout.fragment_google_map, container, false);
        mapView=(MapView) retval.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if(mapView!=null){
            googleMap=mapView.getMap();

            MapsInitializer.initialize(getActivity());

            //Karte Einstellen
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setMyLocationEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            //LatLngBounds.Builder builder = new LatLngBounds.Builder();

            //TODO Koordinaten der Posten, später von Website laden
            handler.post(postenLaden);




            Log.e("Debug", "Initialized");

            //Test:
            /*
            Toast.makeText(getActivity(), "Kartentypen:",
                    Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    googleMap.setMapType(0);
                    Toast.makeText(getActivity(), "TYPE_NONE",
                            Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            googleMap.setMapType(1);
                            Toast.makeText(getActivity(), "TYPE_NORMAL",
                                    Toast.LENGTH_SHORT).show();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    googleMap.setMapType(2);
                                    Toast.makeText(getActivity(), "TYPE_SATELLITE",
                                            Toast.LENGTH_SHORT).show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            googleMap.setMapType(3);
                                            Toast.makeText(getActivity(), "TYPE_TERRAIN",
                                                    Toast.LENGTH_SHORT).show();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    googleMap.setMapType(4);
                                                    Toast.makeText(getActivity(), "TYPE_HYBRID",
                                                            Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(getActivity(), "Fertig",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }, 5000);
                                        }
                                    }, 5000);
                                }
                            }, 5000);
                        }
                    }, 5000);
                }
            }, 5000);
            */
        }


        return retval;

    }

    public void setMarkers(Marker[] markers) {
        this.markers = markers;
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity)activity).onSectionAttached(3);
    }


}
