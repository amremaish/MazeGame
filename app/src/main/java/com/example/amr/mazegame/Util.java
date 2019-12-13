package com.example.amr.mazegame;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amr.mazegame.Activities.ActivityLevel;

/**
 * Created by Amr on 01/02/2018.
 */
public class Util {


    public static void OpenDialog(final AppCompatActivity con, String Msg, final boolean finish) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
        alertDialogBuilder.setMessage(Msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(finish)
                            ActivityLevel.ActivityLevel.finish();
                        else {
                            Intent intent = ActivityLevel.ActivityLevel.getIntent();
                            ActivityLevel.ActivityLevel.finish();
                            ActivityLevel.ActivityLevel.startActivity(intent);
                            ActivityLevel.ActivityLevel.recreate();
                        }
                    }
                });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
