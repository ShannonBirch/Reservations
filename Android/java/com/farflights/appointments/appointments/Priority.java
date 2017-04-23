package com.farflights.appointments.appointments;

import java.io.Serializable;

/**
 * Created by Shannon on 24/03/2017.
 */

public class Priority implements Serializable{
    String name;
    Double cost;

    public String toString(){
        return name+"\t\t\u20ac"+cost.toString();
    }

    Priority(String inName, double inCost){
        this.name = inName;
        this.cost = inCost;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
