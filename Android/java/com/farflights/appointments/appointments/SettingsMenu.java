package com.farflights.appointments.appointments;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Shannon on 20/02/2017.
 */

public class SettingsMenu extends MainActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Settings");

        TextView tv=(TextView)findViewById(R.id.sampleText);
        tv.setText("TODO SETTINGS MENU");

    }
}
