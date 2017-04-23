package com.farflights.appointments.appointments;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Shannon on 10/03/2017.
 */

public class Business implements Serializable{

    private String id;
    private String name;
    private String pic;
    private Double rating;
    private String description;
    private int maxRev;
    private boolean favourited;
    private String type;
    private Address addr = new Address();
    private ArrayList<Priority> priorities = new ArrayList<>();


    public Business(){}


    public Business(String id){
        this.id = id;
    }
    

    //<editor-fold desc="Basic Get Sets">
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxRev() {
        return maxRev;
    }

    public void setMaxRev(int maxRev) {
        this.maxRev = maxRev;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddr() {
        return addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }
    //</editor-fold>

    public void setAddrNumber(String num){
        addr.setNumber(num);
    }
    public String getAddrNumber(){
        return addr.getNumber();
    }

    public void setAddrLine1(String line1){
        addr.setLine1(line1);
    }

    public String getNumandLine1(){
        return (addr.getNumber()+" "+addr.getLine1());
    }

    public void addPriority(String name, double cost){
        priorities.add(new Priority(name, cost));
    }

    public ArrayList<Priority> getPriorities() {
        return priorities;
    }


    public String getPriorityNameByLevel(int level){

       return priorities.get(level).getName();
    }

    public boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

}
