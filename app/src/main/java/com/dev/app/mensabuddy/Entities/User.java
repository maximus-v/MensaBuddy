package com.dev.app.mensabuddy.Entities;

/**
 * Created by Max on 23.01.2017.
 */

public class User {

    private int id;
    private String vorname;
    private String nachname;
    private String fakultaet;
    private String studiengang;
    private String interesse1;
    private String interesse2;
    private String interesse3;
    private String telefon;
    private String token;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getFakultaet() {
        return fakultaet;
    }

    public void setFakultaet(String fakultaet) {
        this.fakultaet = fakultaet;
    }

    public String getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(String studiengang) {
        this.studiengang = studiengang;
    }

    public String getInteresse1() {
        return interesse1;
    }

    public void setInteresse1(String interesse1) {
        this.interesse1 = interesse1;
    }

    public String getInteresse2() {
        return interesse2;
    }

    public void setInteresse2(String interesse2) {
        this.interesse2 = interesse2;
    }

    public String getInteresse3() {
        return interesse3;
    }

    public void setInteresse3(String interesse3) {
        this.interesse3 = interesse3;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
