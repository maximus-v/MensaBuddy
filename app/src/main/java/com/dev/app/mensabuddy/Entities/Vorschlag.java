package com.dev.app.mensabuddy.Entities;

/**
 * Created by Max on 23.01.2017.
 */

public class Vorschlag {

    private int id;
    private String mensa;
    private int eigeneId;
    private String startzeit1;
    private String startzeit2;
    private String datum;
    private int andereId;
    private String name;
    private int conf1;
    private int conf2;
    private int prozent;
    private String zeit;

    public Vorschlag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensa() {
        return mensa;
    }

    public void setMensa(String mensa) {
        this.mensa = mensa;
    }

    public int getEigeneId() {
        return eigeneId;
    }

    public void setEigeneId(int eigeneId) {
        this.eigeneId = eigeneId;
    }

    public String getStartzeit1() {
        return startzeit1;
    }

    public void setStartzeit1(String startzeit1) {
        this.startzeit1 = startzeit1;
    }

    public String getStartzeit2() {
        return startzeit2;
    }

    public void setStartzeit2(String startzeit2) {
        this.startzeit2 = startzeit2;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getAndereId() {
        return andereId;
    }

    public void setAndereId(int andereId) {
        this.andereId = andereId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConf1() {
        return conf1;
    }

    public void setConf1(int conf1) {
        this.conf1 = conf1;
    }

    public int getConf2() {
        return conf2;
    }

    public void setConf2(int conf2) {
        this.conf2 = conf2;
    }

    public int getProzent() {
        return prozent;
    }

    public void setProzent(int prozent) {
        this.prozent = prozent;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }
}
