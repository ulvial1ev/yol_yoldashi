package com.byulvi.yolyolda;

public class Trip {

    String fromloc,toloc,cartype,passengers,time;
    public Trip(){

    }

    public Trip(String fromloc, String toloc, String cartype, String passengers,String time) {
        this.fromloc = fromloc;
        this.toloc = toloc;
        this.cartype = cartype;
        this.passengers = passengers;
        this.time = time;
    }

    public String getFromloc() {
        return fromloc;
    }

    public void setFromloc(String fromloc) {
        this.fromloc = fromloc;
    }

    public String getToloc() {
        return toloc;
    }

    public void setToloc(String toloc) {
        this.toloc = toloc;
    }

    public String getCartype() {
        return cartype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }
}
