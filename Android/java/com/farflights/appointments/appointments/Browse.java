package com.farflights.appointments.appointments;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Shannon on 03/04/2017.
 */

public class Browse extends MainActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        if(loggedinCheck()) {
            setTitle("Browse Businesses");
            String URL = "http://appointments.shannonbirch.com/scripts/browse.php?uid="+sharedPrefs.getString(KEY_USER_ID, null)+"&token="+sharedPrefs.getString(KEY_TOKEN, null);

            new DownloadAndParseXML().execute(URL, "Browse");
        }else{//If not logged in
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }


}
