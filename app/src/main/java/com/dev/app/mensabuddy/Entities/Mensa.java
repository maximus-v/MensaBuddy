package com.dev.app.mensabuddy.Entities;

/**
 * Created by chris on 07.01.2017.
 */

public class Mensa implements Comparable<Mensa> {

    private String name;
    private Double distance;

    public Mensa(String name){
        this.name=name;
    }

    @Override
    public int compareTo(Mensa canteen) {
        if (this.distance<canteen.getDistance()){
            return -1;
        } else if (this.distance==canteen.getDistance()){
            return 0;
        } else return 1;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public void setDistance(Double distance){
        this.distance=distance;
    }

    public Double getDistance(){
        return distance;
    }
}
