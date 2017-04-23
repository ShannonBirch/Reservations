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
 * Created by Shannon on 22/04/2017.
 */

public class Register extends Login {

    EditText pass2, fname, lname, email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        user = (EditText) findViewById(R.id.register_user_text);
        pass = (EditText) findViewById(R.id.register_pass1_text);
        pass2 =(EditText) findViewById(R.id.register_pass2_text);
        fname  =(EditText) findViewById(R.id.register_fname_text);
        lname =(EditText) findViewById(R.id.register_lname_text);
        email =(EditText) findViewById(R.id.register_email_text);


    }

    protected void registerAccount(View v)throws UnsupportedEncodingException{
        String BASEURL = "http://www.appointments.shannonbirch.com/auth/register.php";
        CoordinatorLayout layout = (CoordinatorLayout)findViewById(R.id.register_layout);

        if (!(user.getText().toString().equals(""))
                &&!(pass.getText().toString().equals(""))
                &&!(pass2.getText().toString().equals(""))
                &&!(fname.getText().toString().equals(""))
                &&!(lname.getText().toString().equals(""))
                &&!(email.getText().toString().equals(""))) {

            if(pass.getText().toString().equals(pass2.getText().toString())) {
                String data = URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(user.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("pass1", "UTF-8")
                        + "=" + URLEncoder.encode(pass.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("pass2", "UTF-8")
                        + "=" + URLEncoder.encode(pass2.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("fname", "UTF-8")
                        + "=" + URLEncoder.encode(fname.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("lname", "UTF-8")
                        + "=" + URLEncoder.encode(lname.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(email.getText().toString(), "UTF-8");


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
                    StringBuilder outputString = new StringBuilder();
                    String errorCode = null;


                    if ((errorCode = reader.readLine()) != null) {

                        if (errorCode.equals("Success")) {


                            login(getCurrentFocus());


                        }else if(errorCode.equals("Username already exists")){
                            Snackbar userNameExistsSnack = Snackbar.make(layout, "This Username has already been taken", Snackbar.LENGTH_LONG);
                            userNameExistsSnack.show();
                        }
                        else {
                            Log.e("Error", errorCode);
                        }


                    }
                    reader.close();


                } catch (Exception e) { //Post fails for some reason
                    e.printStackTrace();
                }
            }else{//Passwords are different
                Snackbar differingPasswordsSnack = Snackbar.make(layout, "Passwords must be the same", Snackbar.LENGTH_SHORT);
                differingPasswordsSnack.show();
            }


        }else{

            Snackbar noCredWarning = Snackbar.make(layout, "Missing Values", Snackbar.LENGTH_SHORT);
            noCredWarning.show();


        }


    }

    protected void loginReturn(View v){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }



}
