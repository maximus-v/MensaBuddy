package com.dev.app.mensabuddy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by Max on 06.01.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mensabuddy.db";
    //UNIQUE-ID TABELLE
    public static final String ID_TABLE_NAME = "id_table";
    public static final String ID_COL = "ID";
    //MENSA-TABELLE
    public static final String MENSA_TABLE_NAME = "mensa_table";
    public static final String MENSA_NAME_COL = "Name";
    public static final String MENSA_COUNTER_COL = "Counter";
    //PROFIL-TABELLE
    public static final String PROFILE_TABLE_NAME = "profil_table";
    public static final String PROFIL_ID_COL = "ID";
    public static final String PROFIL_VORNAME_COL = "Vorname";
    public static final String PROFIL_NACHNAME_COL = "Nachname";
    public static final String PROFIL_FAKULTAET_COL = "Fakultaet";
    //VORSCHLAG-TABELLE
    public static final String VORSCHLAG_TABLE_NAME = "vorschlag_table";
    public static final String VORSCHLAG_ID_COL = "ID";
    public static final String VORSCHLAG_INITIATOR_COL = "Initiator_ID";
    public static final String VORSCHLAG_MENSA_COL = "Mensa";
    public static final String VORSCHLAG_STARTZEIT1_COL = "Startzeit1";
    public static final String VORSCHLAG_STARTZEIT2_COL = "Startzeit2"; //Ende - 30min
    public static final String VORSCHLAG_DATE_COL = "Datum";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ID_TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + MENSA_TABLE_NAME + " (" + MENSA_NAME_COL + " TEXT PRIMARY KEY, " + MENSA_COUNTER_COL + " INTEGER)");
        db.execSQL("CREATE TABLE " + PROFILE_TABLE_NAME + " (" + PROFIL_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFIL_VORNAME_COL + " TEXT, " +
                PROFIL_NACHNAME_COL + " TEXT, " + PROFIL_FAKULTAET_COL + " TEXT)");
        //Zeit in "HH:MM" Format, Datum in "YYYY-MM-DD" Format
        db.execSQL("CREATE TABLE " + VORSCHLAG_TABLE_NAME + " (" + VORSCHLAG_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VORSCHLAG_INITIATOR_COL + " INTEGER, " +
                VORSCHLAG_MENSA_COL + " TEXT, " + VORSCHLAG_STARTZEIT1_COL + " TEXT, " + VORSCHLAG_STARTZEIT2_COL + " TEXT, " + VORSCHLAG_DATE_COL + " TEXT)");

        //INSERT MENSEN
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('AlteMensa', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Zelt', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Siedepunkt', 0)");

        //INSERT DUMMY-PROFIL-DATEN
        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + " VALUES (1,'Bruce','Wayne','Wirtschaftswissenschaften')");
        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + " VALUES (2,'Tony','Stark','Elektrotechnik und Informationstechnik')");
        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + " VALUES (3,'Clark','Kent','Wirtschaftswissenschaften')");
        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + " VALUES (4,'Bruce','Banner','Mathematik und Naturwissenschaften')");
        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + " VALUES (5,'Wade','Wilson','Wirtschaftswissenschaften')");

        //INSERT DUMMY-VORSCHLÄGE
        db.execSQL("INSERT INTO " + VORSCHLAG_TABLE_NAME + " VALUES (1,1,'AlteMensa','11:00','11:30','2017-01-05')");
        db.execSQL("INSERT INTO " + VORSCHLAG_TABLE_NAME + " VALUES (2,2,'AlteMensa','11:00','11:30','2017-01-05')");
        db.execSQL("INSERT INTO " + VORSCHLAG_TABLE_NAME + " VALUES (3,3,'AlteMensa','11:30','11:30','2017-01-05')");
        db.execSQL("INSERT INTO " + VORSCHLAG_TABLE_NAME + " VALUES (4,4,'AlteMensa','11:00','11:30','2017-01-05')");
        db.execSQL("INSERT INTO " + VORSCHLAG_TABLE_NAME + " VALUES (5,5,'AlteMensa','12:00','13:30','2017-01-05')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ID_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MENSA_TABLE_NAME);
        onCreate(db);
    }

    //INSERT
    public boolean insertUniqueID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL, id);
        long result = db.insert(ID_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    //return: ID von angelegtem Datensatz
    public long insertProfile(String vorname, String nachname, String fakultaet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFIL_VORNAME_COL, vorname);
        contentValues.put(PROFIL_NACHNAME_COL, nachname);
        contentValues.put(PROFIL_FAKULTAET_COL, fakultaet);
        long result = db.insert(ID_TABLE_NAME, null, contentValues);
        return result;
    }

    public boolean insertVorschlag(int initiator, String mensa, String start1, String start2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String datum = getDatum();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VORSCHLAG_INITIATOR_COL, initiator);
        contentValues.put(VORSCHLAG_MENSA_COL, mensa);
        contentValues.put(VORSCHLAG_STARTZEIT1_COL, start1);
        contentValues.put(VORSCHLAG_STARTZEIT2_COL, start2);
        contentValues.put(VORSCHLAG_DATE_COL, datum);
        long result = db.insert(VORSCHLAG_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    //SELECT mensa, vorschlag, unique
    public Cursor getProfilesById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor result = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME + " WHERE ID = " + PROFIL_ID_COL, null);
        String[] cols = {PROFIL_VORNAME_COL, PROFIL_NACHNAME_COL, PROFIL_FAKULTAET_COL};
        Cursor result = db.query(PROFILE_TABLE_NAME, cols, "ID = " + id, null, null, null, null);
        return result;
    }

    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME, null);
        return result;
    }

    public Cursor getUniqueKey() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + ID_TABLE_NAME, null);
        return result;
    }

    //----------------------------------UPDATE------------------------------------------------------

    //Update Profildaten
    public boolean updateProfile(int id, String vorname, String nachname, String fakultaet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL, id);
        contentValues.put(PROFIL_VORNAME_COL, vorname);
        contentValues.put(PROFIL_NACHNAME_COL, nachname);
        contentValues.put(PROFIL_FAKULTAET_COL, fakultaet);
        db.update(PROFILE_TABLE_NAME, contentValues, "ID = ?", new String[] { Integer.toString(id) });
        return true;
    }

    //Update Datum der Vorschläge auf das aktuelle Datum
    public boolean updateDatum() {
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String datum = getDatum();

        ContentValues contentValues = new ContentValues();
        contentValues.put(VORSCHLAG_DATE_COL, datum);
        db.update(VORSCHLAG_TABLE_NAME, contentValues, null, null);

        return true;
    }

    //--------------------------------------Datum-Funkstion-----------------------------------------
    public String getDatum() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String datum = "";
        if (month < 10) {
            if (day < 10) {
                datum = Integer.toString(year) + "-0" + Integer.toString(month) + "-0" + Integer.toString(day);
            } else {
                datum = Integer.toString(year) + "-0" + Integer.toString(month) + "-" + Integer.toString(day);
            }
        } else {
            if (day < 10) {
                datum = Integer.toString(year) + "-" + Integer.toString(month) + "-0" + Integer.toString(day);
            } else {
                datum = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
            }
        }
        return datum;
    }
}
