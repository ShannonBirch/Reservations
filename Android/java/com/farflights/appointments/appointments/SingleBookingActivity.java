package com.farflights.appointments.appointments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Shannon on 28/03/2017.
 */

public class SingleBookingActivity extends MainActivity {
    Booking bookingObj;
    Button cancelDismissBttn;
    String data;
    URL url;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_booking);

        Intent intent = this.getIntent();
        bookingObj = (Booking) intent.getSerializableExtra("bookingFrom");

        makeNav();

        TextView lblName = (TextView) findViewById(R.id.business_name_book_l);
        TextView lblAddress = (TextView) findViewById(R.id.num_and_line1_book);
        TextView priority = (TextView) findViewById(R.id.priority);
        ImageView pic = (ImageView) findViewById(R.id.picture);
        TextView dateTime = (TextView) findViewById(R.id.date_time);
        cancelDismissBttn = (Button) findViewById(R.id.cancel_dismiss__button);


        lblName.setText(bookingObj.getBusinessObj().getName());
        lblAddress.setText(bookingObj.getBusinessObj().getNumandLine1());
        priority.setText(bookingObj.getBusinessObj().getPriorityNameByLevel(bookingObj.getPriorityLevel()));
        Picasso.with(this).load(bookingObj.getBusinessObj().getPic()).resize(180, 180).into(pic);
        dateTime.setText(bookingObj.getDate());

        if(bookingObj.isBumped()){
            //Dismiss button as the booking has already been cancelled
            cancelDismissBttn.setText(getResources().getString(R.string.dismissButton));

        }else{
            //Cancel button as the booking is still in place
            cancelDismissBttn.setText(getResources().getString(R.string.cancelButton));
        }


    }


    protected void cancelDismissBooking(View v) throws UnsupportedEncodingException, MalformedURLException{

        SharedPreferences sharedPrefs;
        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);


        data = URLEncoder.encode("userID", "UTF-8")
                + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_USER_ID, null), "UTF-8");

        data += "&" + URLEncoder.encode("token", "UTF-8")
                + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_TOKEN, null), "UTF-8");

        data += "&" + URLEncoder.encode("resID", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(bookingObj.getReservationID()), "UTF-8");



        if(bookingObj.isBumped()){
            //Dismiss
            url = new URL("http://www.appointments.shannonbirch.com/scripts/dismiss.php");

            urlPost();
        }else{
            //Cancel

            url = new URL("http://www.appointments.shannonbirch.com/scripts/cancel.php");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to cancel this reservation?").setTitle("Cancel?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.dismiss();
                    urlPost();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.dismiss();
                }
            });



            AlertDialog dialog = builder.create();
            dialog.show();



        }



    }

    protected void editBooking(){
        //Todo Edit button
    }


    private void urlPost() {
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


                    if (bookingObj.isBumped()) {
                        //Dismissed
                        Log.d("Success", "Dismissed");


                    } else {
                        //Cancelled
                        Log.d("Success", "Cancelled");
                    }
                    Intent intent = new Intent(this, Upcoming.class);
                    startActivity(intent);


                } else if (line.equals("Not Logged In")) {
                    logout();
                } else {
                    Log.e("Error", line);
                    while ((line = reader.readLine()) != null) {
                        Log.e("Error", line);
                    }
                }

            } else {
                Log.e("Not echoing", "Anything");
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
