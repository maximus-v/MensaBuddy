package com.dev.app.mensabuddy;

import com.dev.app.mensabuddy.Entities.Mensa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DistanceCalculator {

    private double distance=0;
    private HashMap<String, Double> canteens = new HashMap<>();
    String[] ergebnis = new String[6];

    public DistanceCalculator (){

        canteens.put("Alte MensaLat", 51.027199);
        canteens.put("Alte MensaLng", 13.726550);
        canteens.put("ZeltschlösschenLat", 51.031318);
        canteens.put("ZeltschlösschenLng", 13.728419);
        canteens.put("SiedepunktLat", 51.029140);
        canteens.put("SiedepunktLng", 13.738530);
        canteens.put("UBootLat", 51.030100);
        canteens.put("UBootLng", 13.729319);
        canteens.put("GrillcubeLat", 51.028558);
        canteens.put("GrillcubeLng", 13.728754);
        canteens.put("ReichenbachstrasseLat", 51.034214);
        canteens.put("ReichenbachstrasseLng", 13.734010);
        canteens.put("UBootLat", 51.030256);
        canteens.put("UBootLng", 13.729224);
    }

    public double calculateDistance (double lat1,double lat2,double long1,double long2){
        double Lat1=this.convert(lat1*(-1));
        double Lat2=this.convert(lat2*(-1));

        distance=Math.acos((Math.sin(Lat1)*Math.sin(Lat2))+(Math.cos(Lat1)*Math.cos(Lat2)*Math.cos(this.convert(long2-long1))));
        distance=distance*6371;
        return distance;
    }

    private double convert(double wert)
    {
        return wert*(2*3.14159265359/360);
    }

    private String[] sortCanteens(Double lat, Double lng){
        List result = new ArrayList();
        Mensa alteMensa = new Mensa("Alte Mensa");
        Mensa siedepunkt = new Mensa("Siedepunkt");
        Mensa zelt = new Mensa("Zeltschlösschen");
        Mensa grill = new Mensa("Grillcube");
        Mensa reichenbachstrasse = new Mensa("Reichenbachstrasse");
        Mensa uBoot = new Mensa("UBoot");

        alteMensa.setDistance(this.calculateDistance(lat, canteens.get("Alte MensaLat"), lng, canteens.get("Alte MensaLng")));
        siedepunkt.setDistance(this.calculateDistance(lat, canteens.get("SiedepunktLat"), lng, canteens.get("SiedepunktLng")));
        zelt.setDistance(this.calculateDistance(lat, canteens.get("ZeltschlösschenLat"), lng, canteens.get("ZeltschlösschenLng")));
        grill.setDistance(this.calculateDistance(lat, canteens.get("GrillcubeLat"), lng, canteens.get("GrillcubeLng")));
        reichenbachstrasse.setDistance(this.calculateDistance(lat, canteens.get("ReichenbachstrasseLat"), lng, canteens.get("ReichenbachstrasseLng")));
        uBoot.setDistance(this.calculateDistance(lat, canteens.get("UBootLat"), lng, canteens.get("UBootLng")));
        result.add(0, alteMensa);
        result.add(1, siedepunkt);
        result.add(2, zelt);
        result.add(3, grill);
        result.add(4, reichenbachstrasse);
        result.add(5, uBoot);
        Collections.sort(result);
        ergebnis[0]=result.get(0).toString();
        ergebnis[1]=result.get(1).toString();
        ergebnis[2]=result.get(2).toString();
        ergebnis[3]=result.get(3).toString();
        ergebnis[4]=result.get(4).toString();
        ergebnis[5]=result.get(5).toString();
        return ergebnis;
    }

    public String getNextMensa(Double lat, Double lng){
        this.sortCanteens(lat, lng);
        return ergebnis[0];

    }

    public String[] getSortedCanteens(Double lat, Double lng){
        return this.sortCanteens(lat, lng);
    }

}