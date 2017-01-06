package com.dev.app.mensabuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String VORSCHLAG_TABLE_NAME = "profil_table";
    public static final String VORSCHLAG_ID_COL = "ID";
    public static final String VORSCHLAG_INITIATOR_COL = "Initiator-ID";
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
        db.execSQL("CREATE TABLE " + ID_TABLE_NAME + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT)");
        db.execSQL("CREATE TABLE " + MENSA_TABLE_NAME + " (" + MENSA_NAME_COL + " TEXT PRIMARY KEY, " + MENSA_COUNTER_COL + " INTEGER)");
        //INSERT MENSEN
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ID_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MENSA_TABLE_NAME);
        onCreate(db);
    }

    //INSERT profil, vorschlag, unique
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

    //SELECT profil, mensa, vorschlag, unique
    public Cursor getAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME, null);
        return result;
    }

    //UPDATE
    public boolean updateProfile(int id, String activity, int counter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_COL, id);
        contentValues.put(ACTIVITY_COL, activity);
        contentValues.put(COUNTER_COL, counter);
        db.update(ACTIVITY_TABLE_NAME, contentValues, "ID = ?", new String[] { Integer.toString(id) });
        return true;
    }
}
