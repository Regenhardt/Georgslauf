package de.stamm_prm.georgslauf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Marlon on 19.02.2015.
 */
public class CodeDialog extends DialogFragment {


    AlertDialog.Builder builder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Irgendwas hier");
        builder.setNegativeButton("Knopf 1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.onButton1Click(getView());
            }
        });
        builder.setPositiveButton("Knopf 2", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.onButton2Click(getView());
            }
        });

        return builder.create();
    }
}
