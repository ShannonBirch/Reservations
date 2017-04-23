package com.farflights.appointments.appointments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Shannon on 13/02/2017.
 */

public class Upcoming extends MainActivity{
    SharedPreferences sharedPrefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);



        if(loggedinCheck()){//Logged in so all G

            setTitle("Upcoming Events");
            String URL = "http://appointments.shannonbirch.com/scripts/getBookings.php?uid="+sharedPrefs.getString(KEY_USER_ID, null)+"&token="+sharedPrefs.getString(KEY_TOKEN, null);
            Log.e("Url", "Wat"+sharedPrefs.getString(KEY_TOKEN, null));
            new DownloadAndParseXML().execute(URL, "Upcoming");

        }else{//If not logged in
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }





    }




}
