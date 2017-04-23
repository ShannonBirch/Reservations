package com.farflights.appointments.appointments;

import java.io.Serializable;

/**
 * Created by Shannon on 27/03/2017.
 */

public class Booking implements Serializable{

    String date;
    String time;
    int priorityLevel;
    int ReservationID;
    boolean bumped;

    Business businessObj;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getReservationID() {
        return ReservationID;
    }

    public void setReservationID(int reservationID) {
        ReservationID = reservationID;
    }

    public Business getBusinessObj() {
        return businessObj;
    }

    public void setBusinessObj(Business businessObj) {
        this.businessObj = businessObj;
    }

    public boolean isBumped() {
        return bumped;
    }

    public void setBumped(boolean bumped) {
        this.bumped = bumped;
    }








}
