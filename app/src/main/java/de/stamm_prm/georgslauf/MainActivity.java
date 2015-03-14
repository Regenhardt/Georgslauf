package de.stamm_prm.georgslauf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static Operator operator=null;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private GoogleMapFragment googleMapFragment=null;
    private GoogleMapOptions mapStandards;
    private LatLng homeCoords;
    private CameraPosition homeCam;
    private Fragment fragment;
    private static QRReader reader;
    public static boolean sendToast=false;
    private Handler toaster;

    TextView qrTextView;
    Button qrButton1, qrButton2;

    public static Context context;

    Runnable check = new Runnable() {
        @Override
        public void run() {
            if (sendToast) {
                //if(operator.isNew()) {
                //    LinearLayout dummyView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout
                //                    .info_window_dummy,
                //            null);
                //    dummyView.setOrientation(LinearLayout.VERTICAL);
                //    LinearLayout newView;
                //    TextView dummyTitle = new TextView(context);
                //    ImageView dummyImage = new ImageView(context);
                //    for (int i = 0; i < operator.IDs.length; i++) {
                //        Log.e("Debug", String.valueOf(i)+" laden...");
                //        newView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout
                //                        .info_window_dummy,
                //                null);
                //        newView.setOrientation(LinearLayout.VERTICAL);
                //        dummyTitle.setText("Posten " + String.valueOf(i + 1));
                //        dummyImage.setImageResource(operator.IDs[i]);
                //        newView.addView(dummyTitle);
                //        newView.addView(dummyImage);
                //        operator.infoWindows[i] = newView;
                //    }
                //
                //}
                Toast.makeText(getBaseContext(), "Geladen", Toast.LENGTH_SHORT).show();
            }else toaster.postDelayed(check, 30);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toaster=new Handler();
        if(operator==null) {
            operator = new Operator();
            operator.start();
        }else if (operator.getState() != Thread.State.TERMINATED) {
            operator.start();
            operator.newOp=false;
        }
        Operator.context=getApplicationContext();

        //Debug*************
        toaster.post(check);
        //******************

        setContentView(R.layout.activity_main);

        operator.mapView=(MapView)findViewById(R.id.map);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch (position){
            case 0:
                fragment=WelcomeFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                break;
            case 1:
                reader=QRReader.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, reader)
                        .commit();
                qrTextView=(TextView)findViewById(R.id.resultTextView);
                qrButton1=(Button)findViewById(R.id.button1);
                qrButton2=(Button)findViewById(R.id.button2);
                break;
            case 2:
                //if(googleMapFragment==null)
                    googleMapFragment=GoogleMapFragment.newInstance();
                Log.e("Debug", "Instance created");
                //
                fragmentManager.beginTransaction()
                        .replace(R.id.container, googleMapFragment)
                        .commit();
                Log.e("Debug", "Replaced");
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ImpressumFragment.newInstance())
                        .commit();
                break;
            //default:

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    public void onCodeClick(View view) {
        reader.onCodeClick(view);
    }

    public static void onButton2Click(View view) {
        reader.onButton2Click(view);
    }

    public static void onButton1Click(View view) {
        reader.onButton1Click(view);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            reader.textView.setText(scanResult.getContents());
            reader.textView.setVisibility(View.VISIBLE);
            reader.textView.setClickable(true);
            reader.codeExists=true;
        }else {
            reader.textView.setText("Nada");
            Toast.makeText(this, "Fail",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        operator.interrupt();
    }

}
