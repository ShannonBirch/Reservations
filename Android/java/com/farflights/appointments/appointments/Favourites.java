package com.farflights.appointments.appointments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;



/**
 * Created by Shannon on 20/02/2017.
 */

public class Favourites extends MainActivity {
    // Declare variables
    TextView textview;
    SharedPreferences sharedPrefs;


    ProgressDialog pDialog;
    // Insert image URL



    @Override
    public void onCreate(Bundle savedInstanceState) {//Todo login check
        super.onCreate(savedInstanceState);
        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);
        String URL = "http://www.appointments.shannonbirch.com/scripts/getFav.php?uid="+sharedPrefs.getString(KEY_USER_ID, null)+"&token="+sharedPrefs.getString(KEY_TOKEN, null);

        // Locate a TextView in your activity_main.xml layout
        textview = (TextView) findViewById(R.id.sampleText);
        // Execute DownloadAndParseXML AsyncTask
        setTitle("Favourites");
        new DownloadAndParseXML().execute(URL, "Favourites");


    }



}
