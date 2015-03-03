package de.stamm_prm.georgslauf;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Marlon on 10.02.2015.
 */
public class QRReader extends Fragment{

    ImageButton code;
    TextView textView;
    boolean codeExists = false;
    Handler scanner;

    DialogFragment codeSharing;
    private Runnable scan=new Runnable() {
        @Override
        public void run() {
            IntentIntegrator.initiateScan(getActivity());
        }
    };


    public static QRReader newInstance(){
        return new QRReader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_qr_reader, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        textView=(TextView)getActivity().findViewById(R.id.resultTextView);
        textView.setVisibility(View.GONE);
        code=(ImageButton)getActivity().findViewById(R.id.imageButton);
        code.setVisibility(View.VISIBLE);
        scanner=new Handler();
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity)activity).onSectionAttached(2);
    }

    public void onCodeClick(View view){
        if(code.getVisibility()==View.GONE) {
            //TODO Code manuell benutzen?
            codeSharing = new CodeDialog();
            codeSharing.show(getFragmentManager(), "codeDialog");
        }else {
            code.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            scanner.post(scan);
        }

    }

    public void onButton2Click(View view) {
        int codeVisibility = code.getVisibility()==View.VISIBLE? View.GONE:View.VISIBLE;
        code.setVisibility(codeVisibility);
        textView.setVisibility(codeVisibility==View.VISIBLE?View.GONE:View.VISIBLE);
    }

    public void onButton1Click(View view) {
        Toast.makeText(getActivity(), "Knopf 1 macht dies", Toast.LENGTH_SHORT).show();
    }
}
