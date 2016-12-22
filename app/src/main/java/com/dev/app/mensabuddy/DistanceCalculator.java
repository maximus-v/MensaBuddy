package com.dev.app.mensabuddy;

public class DistanceCalculator {

    private double distance=0;

    public DistanceCalculator (){
        distance=0;
    }

    public String calculateDistance (double lat1,double lat2,double long1,double long2){
        double Lat1=this.convert(lat1*(-1));
        double Lat2=this.convert(lat2*(-1));

        distance=Math.acos((Math.sin(Lat1)*Math.sin(Lat2))+(Math.cos(Lat1)*Math.cos(Lat2)*Math.cos(this.convert(long2-long1))));
        distance=distance*6371;
        return String.valueOf(distance);
    }

    private double convert(double wert){
        return wert*(2*3.14159265359/360);
    }
}
