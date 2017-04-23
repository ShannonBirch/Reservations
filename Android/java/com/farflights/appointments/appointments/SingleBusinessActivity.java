package com.farflights.appointments.appointments;

import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;




/**
 * Created by Shannon on 09/03/2017.
 */

public class SingleBusinessActivity extends MainActivity {

    Business businessObj;
    Button favButton;
    final static Calendar calendarObj = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_business);
        Intent intent = this.getIntent();
        businessObj =  (Business) intent.getSerializableExtra("businessFrom");
        makeNav();




        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblAddress = (TextView) findViewById(R.id.num_and_line1_book);
        ImageView pic = (ImageView) findViewById(R.id.picture);
        TextView description = (TextView) findViewById(R.id.description);
        favButton = (Button) findViewById(R.id.single_business_fav);


        lblName.setText(businessObj.getName());
        lblAddress.setText(businessObj.getNumandLine1());
        Picasso.with(this).load(businessObj.getPic()).resize(180, 180).into(pic);
        description.setText(businessObj.getDescription());


        if(businessObj.isFavourited()){
            favButton.setText(getResources().getString(R.string.favouriteButtonT));
        }else{
            favButton.setText(getResources().getString(R.string.favouriteButtonF));
        }

    }




    protected void openBooking(View v){
        //Start Book activity and pass through the business object
        Intent intent = new Intent(this, Book.class);
        intent.putExtra("Bus", businessObj);
        Log.e("Put bus in", businessObj.getId());
        startActivity(intent);

    }

    protected void changeFavourite(View v)throws UnsupportedEncodingException, MalformedURLException{

        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);
        URL url;
        if(businessObj.isFavourited()){
            //Remove Favourite
            url = new URL("http://www.appointments.shannonbirch.com/scripts/removeFav.php");
        }else{
            //AddFavourite
            url = new URL("http://www.appointments.shannonbirch.com/scripts/makeFav.php");
        }

            String data = URLEncoder.encode("userID", "UTF-8")
                    + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_USER_ID, null), "UTF-8");

            data += "&" + URLEncoder.encode("token", "UTF-8")
                    + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_TOKEN, null), "UTF-8");

            data += "&" + URLEncoder.encode("bid", "UTF-8")
                + "=" + URLEncoder.encode(businessObj.getId(), "UTF-8");


        try {
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


            if ((line = reader.readLine()) != null) {
                if (line.equals("Success")) {


                    if(businessObj.isFavourited()){
                        //Remove Favourite
                        favButton.setText(getResources().getString(R.string.favouriteButtonF));
                        businessObj.setFavourited(false);
                        Log.d("Success", "Removed favourite");
                    }else{
                        //AddFavourite
                        favButton.setText(getResources().getString(R.string.favouriteButtonT));
                        businessObj.setFavourited(true);
                        Log.d("Success", "Created favourite");
                    }

                } else if(line.equals("Not Logged In")) {
                    logout();
                }else{
                    Log.e("Error",line);
                    while((line = reader.readLine()) != null) {
                        Log.e("Error", line);
                    }
                }

            } else {
                Log.e("Not echoing", "Anything");

            }

            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }


    }







}
