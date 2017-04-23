package com.farflights.appointments.appointments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Shannon on 24/03/2017.
 */
public class Search extends MainActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Search for business");

        //todo: Search

        TextView tv=(TextView)findViewById(R.id.sampleText);
        tv.setText("Book an Appointment");



    }




}
