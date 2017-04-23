package com.farflights.appointments.appointments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shannon on 13/02/2017.
 */

public class Book  extends MainActivity{


    static final String BASEURL = "http://www.appointments.shannonbirch.com/scripts/bookReservation.php";
    static TextView dateTextView;
    static TextView timeTextView;
    Spinner spin;

    private static int day;
    private static int month;
    private static int year;
    private static int hour;
    private static int minute;

    final static Calendar calendarObj = Calendar.getInstance();
    private Business businessObj;
    private SharedPreferences sharedPrefs;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        setTitle("Book an Appointment");
        makeNav();
        sharedPrefs = getSharedPreferences(KEY_DETAILS, Context.MODE_PRIVATE);


        Intent intent = this.getIntent();
        businessObj = (Business) intent.getSerializableExtra("Bus");



        TextView nameText = (TextView) findViewById(R.id.business_name_book_l);
        TextView numLine1Text = (TextView) findViewById(R.id.num_and_line1_book);

        nameText.setText(businessObj.getName());
        numLine1Text.setText(businessObj.getNumandLine1());

        dateTextView =  (TextView) findViewById(R.id.date);
        timeTextView =  (TextView) findViewById(R.id.time);

        //Set Date and time text views to the current date and time
        dateTextView.setText(String.valueOf(calendarObj.get(Calendar.DATE))+"/"+String.valueOf(calendarObj.get(Calendar.MONTH))+"/"+String.valueOf(calendarObj.get(Calendar.YEAR)));
        timeTextView.setText(String.valueOf(calendarObj.get(Calendar.HOUR_OF_DAY)+":"+calendarObj.get(Calendar.MINUTE)));

        spin =  (Spinner) findViewById(R.id.prioritySpinner);

        ArrayAdapter priorityAdapter = new ArrayAdapter(this, R.layout.priority_spinner, businessObj.getPriorities());
        spin.setAdapter(priorityAdapter);



    }//End of onCreate


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current dateTextView as the default dateTextView in the picker

            int year = calendarObj.get(Calendar.YEAR);
            int month = calendarObj.get(Calendar.MONTH);
            int day = calendarObj.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int inYear, int inMonth, int inDay) {
            //Sets the text in the dateTextView textview to the users chosen dateTextView
            dateTextView.setText(inDay+"/"+inMonth+"/"+inYear);
            year=inYear; month=inMonth; day=inDay;

        }
    }//End of DatePickerFragment


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current timeTextView as the default values for the picker
            int hour = calendarObj.get(Calendar.HOUR_OF_DAY);
            int minute = calendarObj.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            TimePickerDialog timePickDialog = new TimePickerDialog(getContext(), this, hour, minute, false);
            //Todo limit times and change from clock to scroll
            //timePickDialog.
            return timePickDialog;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int inMinute) {
            // Do something with the timeTextView chosen by the user
            timeTextView.setText(hourOfDay+":"+inMinute);
            hour = hourOfDay; minute = inMinute;
        }
    }//End of TimePicker Fragment


    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }



                //Todo Don't allow users to book without selecting a time
                //Todo Update database to allow log in tokens
                //Todo lots of shit

                //todo: Book appointment code




    protected void createBooking(View v) throws UnsupportedEncodingException{//Todo prevent double bookings
        //Here the booking will be created

        //Defines values for post
        String data = URLEncoder.encode("uid", "UTF-8")
                + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_USER_ID, null), "UTF-8");           //Todo Currently using HARD CODED UID NEED CHANGE

        data += "&" + URLEncoder.encode("userToken", "UTF-8")               //Todo Add support for user tokens
                + "=" + URLEncoder.encode(sharedPrefs.getString(KEY_TOKEN, null), "UTF-8");

        data += "&" +  URLEncoder.encode("bid", "UTF-8")
                + "=" + URLEncoder.encode(businessObj.getId(), "UTF-8");

        data += "&" + URLEncoder.encode("year", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(year), "UTF-8");

        data += "&" + URLEncoder.encode("month", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(month), "UTF-8");

        data += "&" + URLEncoder.encode("day", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(day), "UTF-8");

        data += "&" + URLEncoder.encode("hour", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(hour), "UTF-8");

        data += "&" + URLEncoder.encode("minute", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(minute), "UTF-8");

        data += "&" + URLEncoder.encode("priority", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(spin.getSelectedItemPosition()), "UTF-8");


        try
        {
            URL url = new URL(BASEURL);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            int currApiV = Build.VERSION.SDK_INT;
            if(currApiV >= Build.VERSION_CODES.GINGERBREAD){
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Set thread policy to permit all so that OutputStreamWriter wr works
                StrictMode.setThreadPolicy(policy);
            }//Todo find out the default thread policy and revert it after we're done with this

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;

            Booking passingInBookingObj = new Booking();
            passingInBookingObj.setBusinessObj(businessObj);
            passingInBookingObj.setDate(year+"-"+month+"-"+day+" "+hour+":"+minute+":00");
            passingInBookingObj.setPriorityLevel(spin.getSelectedItemPosition());

            int i = 0;
            CoordinatorLayout coordLayout = (CoordinatorLayout)findViewById(R.id.coordlayoutbook);
            if((line=reader.readLine())!=null){

                if(line.equals("Success")){

                    int bookingNumber=Integer.parseInt(reader.readLine());
                    passingInBookingObj.setReservationID(bookingNumber);

                    reader.close();

                    //Start SingleBooking activity and pass through a Booking object
                    Intent intent = new Intent(this, SingleBookingActivity.class);
                    intent.putExtra("bookingFrom", passingInBookingObj);
                    Log.d("Success made booking", line);
                    startActivity(intent);

                }else if(line.equals("Fully Booked")) {
                    Snackbar fullUpSnack = Snackbar.make(coordLayout, "There's no tables left at this price point for this time, try another time, or another pricePoint", Snackbar.LENGTH_INDEFINITE);
                    fullUpSnack.show();

                }else {
                    Log.e("Error", line);
                    String errorC;
                    while((errorC = reader.readLine())!=null){
                        Log.e("Error ", errorC);
                    }
                    Snackbar errorSnack = Snackbar.make(coordLayout, "There Was an Error Please Try again", Snackbar.LENGTH_LONG);
                    errorSnack.show();
                }


            }

            reader.close();





        }catch(Exception e){ //Post fails for some reason
            e.printStackTrace();
        }

    }//End of createBooking

}
