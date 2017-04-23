package com.farflights.appointments.appointments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Shannon on 02/04/2017.
 */

public class Logout extends Activity {

    static final String BASEURL = "http://www.appointments.shannonbirch.com/auth/logout.php";
    static final String KEY_USER_ID = "UserIDKey";
    static final String KEY_TOKEN = "loginTokenKey";
    static final String KEY_DETAILS = "UserDetails";

    SharedPreferences sharedPrefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);


        try {
            String data = URLEncoder.encode("userID", "UTF-8")
                    + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_USER_ID, null), "UTF-8");           //Todo Currently using HARD CODED UID NEED CHANGE

            data += "&" + URLEncoder.encode("token", "UTF-8")               //Todo Add support for user tokens
                    + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_TOKEN, null), "UTF-8");


            try {
                URL url = new URL(BASEURL);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                int currApiV = Build.VERSION.SDK_INT;
                if (currApiV >= Build.VERSION_CODES.GINGERBREAD) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Set thread policy to permit all so that OutputStreamWriter wr works
                    StrictMode.setThreadPolicy(policy);
                }//Todo find out the default thread policy and revert it after we're done with this

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;


                int i = 0;
                if((line = reader.readLine()) != null) {


                    if(line.equals("Success")){
                        Log.d("sql", reader.readLine());

                        SharedPreferences.Editor editor = sharedPrefs.edit();

                        //Log.e("Uh oh", sharedPrefs.getString(KEY_USER_ID, null));
                        editor.clear();
                        editor.commit();



                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);

                        reader.close();

                    }else{
                        Log.e("Not Success", line);
                        while((line = reader.readLine()) != null) {
                            Log.e("Not Success", line);
                        }
                    }

                }else{
                    Log.e("Not echoing", "Anything");

                }

                reader.close();

            } catch (Exception e) { //Post fails for some reason
                e.printStackTrace();
            }


         }catch(Exception ex){
            ex.printStackTrace();

        }





    }



}
