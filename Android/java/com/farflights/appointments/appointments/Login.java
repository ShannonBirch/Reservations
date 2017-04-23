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
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by Shannon on 02/04/2017.
 */

public class Login extends Activity{

    EditText user, pass;
    static final String BASEURL = "http://www.appointments.shannonbirch.com/auth/login.php";
    static final String KEY_USER_ID = "UserIDKey";
    static final String KEY_TOKEN = "loginTokenKey";
    static final String KEY_DETAILS = "UserDetails";
    static final String KEY_FNAME = "FirstName";
    static final String KEY_LNAME = "LastName";
    static final String KEY_EMAIL = "email";
    String fbaseToken;

    SharedPreferences sharedPrefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        user = (EditText) findViewById(R.id.login_user_text);
        pass = (EditText) findViewById(R.id.login_pass_text);

    }

    protected void login(View v) throws UnsupportedEncodingException {
        Firebase f1 = new Firebase();
        f1.onTokenRefresh();
        if (!(user.getText().toString().equals(""))&&!(pass.getText().toString().equals(""))) {


            String data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(user.getText().toString(), "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8")
                    + "=" + URLEncoder.encode(pass.getText().toString(), "UTF-8");

            data += "&" + URLEncoder.encode("fbaseToken", "UTF-8")
                    + "=" + URLEncoder.encode(fbaseToken, "UTF-8");

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
                String line = null;
                String errorCode="Never Made It", userID=null, loginToken=null, fname=null, lname=null, email=null;

                int i = 0;
                while ((line = reader.readLine()) != null) {
                    switch (i){
                        case 0:
                            errorCode=line;
                            break;
                        case 1:
                            userID=line;
                            break;
                        case 2:
                            loginToken=line;
                            break;
                        case 3:
                            fname=line;
                            break;
                        case 4:
                            lname=line;
                            break;
                        case 5:
                            email=line;
                            break;
                    }

                    i++;
                }
                reader.close();

                if(errorCode.equals("Success")){
                    sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(KEY_USER_ID, userID);
                    editor.putString(KEY_TOKEN, loginToken);
                    editor.putString(KEY_FNAME, fname);
                    editor.putString(KEY_LNAME, lname);
                    editor.putString(KEY_EMAIL, email);

                    editor.commit();

                    Log.d("Logged prefs", "Ek Yeah");

                    //setContentView(R.layout.activity_main);
                    Intent intent = new Intent(this, Upcoming.class);
                    startActivity(intent);
                }else{
                    Log.e("Oh shit", errorCode);
                }



            } catch (Exception e) { //Post fails for some reason
                e.printStackTrace();
            }


        }else{
            CoordinatorLayout layout = (CoordinatorLayout)findViewById(R.id.login_layout);
            Snackbar noCredWarning = Snackbar.make(layout, "Missing Values", Snackbar.LENGTH_SHORT);
            noCredWarning.show();


        }

    }


    private class Firebase extends FirebaseInstanceIdService{

        @Override
        public void onTokenRefresh(){
            // Get updated InstanceID token.
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(refreshedToken);
        }

        protected void sendRegistrationToServer(String token){
            fbaseToken=token;
            //Log.d("Fbase token", "is \t\t"+token);

        }

    }


    protected void register(View v){

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }








}
