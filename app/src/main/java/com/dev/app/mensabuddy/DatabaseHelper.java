package com.dev.app.mensabuddy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dev.app.mensabuddy.Entities.User;
import com.dev.app.mensabuddy.Entities.Vorschlag;

import java.util.Calendar;

/**
 * Created by Max on 06.01.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "mensabuddy.db";
    //MENSA-TABELLE
    public static final String MENSA_TABLE_NAME = "mensa_table";
    public static final String MENSA_NAME_COL = "name";
    public static final String MENSA_COUNTER_COL = "counter";
    //PROFIL-TABELLE
    public static final String PROFILE_TABLE_NAME = "profil_table";
    public static final String PROFIL_ID_COL = "id";
    public static final String PROFIL_VORNAME_COL = "vorname";
    public static final String PROFIL_NACHNAME_COL = "nachname";
    public static final String PROFIL_FAKULTAET_COL = "fakultaet";
    public static final String PROFIL_STUDIENGANG_COL = "studiengang";
    public static final String PROFIL_INTERESSE1_COL = "interesse1";
    public static final String PROFIL_INTERESSE2_COL = "interesse2";
    public static final String PROFIL_INTERESSE3_COL = "interesse3";
    public static final String PROFIL_TELEFON_COL = "telefon";
    public static final String PROFIL_TOKEN_COL = "token";
    //VORSCHLAG-TABELLE
    public static final String VORSCHLAG_TABLE_NAME = "vorschlag_table";
    public static final String VORSCHLAG_ID_COL = "matchId";
    public static final String VORSCHLAG_MENSA_COL = "mensa";
    public static final String VORSCHLAG_EIGENE_ID_COL = "eigene_id";
    public static final String VORSCHLAG_STARTZEIT1_COL = "startzeit1"; //eigene Zeit
    public static final String VORSCHLAG_STARTZEIT2_COL = "startzeit2"; //Ende - 30min
    public static final String VORSCHLAG_DATE_COL = "datum";
    public static final String VORSCHLAG_ANDERE_ID_COL = "andere_id";
    public static final String VORSCHLAG_NAME_COL = "name";
    public static final String VORSCHLAG_TELEFON_COL = "telefon";
    public static final String VORSCHLAG_CONF1_COL = "conf1";
    public static final String VORSCHLAG_CONF2_COL = "conf2";
    public static final String VORSCHLAG_PROZENT_COL = "prozent";
    public static final String VORSCHLAG_ZEIT_COL = "zeit"; //vereinbarte Zeit


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MENSA_TABLE_NAME + " (" + MENSA_NAME_COL + " TEXT PRIMARY KEY, " + MENSA_COUNTER_COL + " INTEGER)");

        db.execSQL("CREATE TABLE " + PROFILE_TABLE_NAME + " (" + PROFIL_ID_COL + " INTEGER PRIMARY KEY, " + PROFIL_VORNAME_COL + " TEXT, " +
                PROFIL_NACHNAME_COL + " TEXT, " + PROFIL_FAKULTAET_COL + " TEXT, " + PROFIL_STUDIENGANG_COL + " TEXT, " + PROFIL_INTERESSE1_COL +
                " TEXT, " + PROFIL_INTERESSE2_COL + " TEXT, " + PROFIL_INTERESSE3_COL + " TEXT, " + PROFIL_TELEFON_COL + " TEXT, " +
                PROFIL_TOKEN_COL + " TEXT)");

        //Zeit in "HH:MM:SS" Format, Datum in "YYYY-MM-DD" Format
        db.execSQL("CREATE TABLE " + VORSCHLAG_TABLE_NAME + " (" + VORSCHLAG_ID_COL + " INTEGER PRIMARY KEY, " + VORSCHLAG_EIGENE_ID_COL + " INTEGER, " +
                VORSCHLAG_MENSA_COL + " TEXT, " + VORSCHLAG_STARTZEIT1_COL + " INTEGER, " + VORSCHLAG_STARTZEIT2_COL + " INTEGER, " +
                VORSCHLAG_DATE_COL + " TEXT, " + VORSCHLAG_ANDERE_ID_COL + " INTEGER, " + VORSCHLAG_NAME_COL + " TEXT, " + VORSCHLAG_TELEFON_COL + " TEXT, " +
                VORSCHLAG_CONF1_COL + " INTEGER, " + VORSCHLAG_CONF2_COL + " TEXT, " + VORSCHLAG_PROZENT_COL + " TEXT, " + VORSCHLAG_ZEIT_COL + " TEXT" + ")");

        //INSERT MENSEN
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('AlteMensa', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Zeltmensa', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Siedepunkt', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Reichenbachstrasse', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Grillcube', 0)");
        db.execSQL("INSERT INTO " + MENSA_TABLE_NAME + " VALUES ('Uboot', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MENSA_TABLE_NAME);
        onCreate(db);
    }

    /*
    -----PROFIL-FUNKTIONEN-----
     */
    public boolean insertProfile(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFIL_ID_COL, user.getId());
        contentValues.put(PROFIL_VORNAME_COL, user.getVorname());
        contentValues.put(PROFIL_NACHNAME_COL, user.getNachname());
        contentValues.put(PROFIL_FAKULTAET_COL, user.getFakultaet());
        contentValues.put(PROFIL_STUDIENGANG_COL, user.getStudiengang());
        contentValues.put(PROFIL_INTERESSE1_COL, user.getInteresse1());
        contentValues.put(PROFIL_INTERESSE2_COL, user.getInteresse2());
        contentValues.put(PROFIL_INTERESSE3_COL, user.getInteresse3());
        contentValues.put(PROFIL_TELEFON_COL, user.getTelefon());
        contentValues.put(PROFIL_TOKEN_COL, user.getToken());
        long result = db.insert(PROFILE_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean updateProfile(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFIL_ID_COL, user.getId());
        contentValues.put(PROFIL_VORNAME_COL, user.getVorname());
        contentValues.put(PROFIL_NACHNAME_COL, user.getNachname());
        contentValues.put(PROFIL_FAKULTAET_COL, user.getFakultaet());
        contentValues.put(PROFIL_STUDIENGANG_COL, user.getStudiengang());
        contentValues.put(PROFIL_INTERESSE1_COL, user.getInteresse1());
        contentValues.put(PROFIL_INTERESSE2_COL, user.getInteresse2());
        contentValues.put(PROFIL_INTERESSE3_COL, user.getInteresse3());
        contentValues.put(PROFIL_TELEFON_COL, user.getTelefon());
        contentValues.put(PROFIL_TOKEN_COL, user.getToken());
        db.update(PROFILE_TABLE_NAME, contentValues, "id = ?", new String[] { Integer.toString(user.getId()) });
        return true;
    }

    public User getProfil() {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = new User();
        Cursor result = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME + " ORDER BY id DESC", null);

        if (result.moveToNext()) {
            user.setId(result.getInt(0));
            user.setVorname(result.getString(1));
            user.setNachname(result.getString(2));
            user.setFakultaet(result.getString(3));
            user.setStudiengang(result.getString(4));
            user.setInteresse1(result.getString(5));
            user.setInteresse2(result.getString(6));
            user.setInteresse3(result.getString(7));
            user.setTelefon(result.getString(8));
            user.setToken(result.getString(9));
        }

        return user;
    }

    public int getProfilId() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT id FROM " + PROFILE_TABLE_NAME, null);
        if (result.moveToNext()) {
            return result.getInt(0);
        }
        return 0;
    }

    /*
    -----MENSA-FUNKTIONEN-----
     */

    public boolean updateMensa(String mensa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int counterAlt = getMensaCounter(mensa);
        int counterNeu = counterAlt + 1;
        contentValues.put(MENSA_COUNTER_COL, counterNeu);
        db.update(MENSA_TABLE_NAME, contentValues, "name = ?", new String[] { mensa });
        return true;
    }

    public int getMensaCounter(String mensa) {
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;

        String[] cols = {MENSA_NAME_COL, MENSA_COUNTER_COL};
        Cursor result = db.query(MENSA_TABLE_NAME, cols, "name = " + mensa, null, null, null, null);

        if (result.moveToNext()) {
            counter = result.getInt(1);
        }

        return counter;
    }

    /*
    -----VORSCHLAG-FUNKTIONEN-----
     */

    public boolean insertVorschlag(Vorschlag vorschlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VORSCHLAG_ID_COL, vorschlag.getId());
        contentValues.put(VORSCHLAG_MENSA_COL, vorschlag.getMensa());
        contentValues.put(VORSCHLAG_EIGENE_ID_COL, vorschlag.getEigeneId());
        contentValues.put(VORSCHLAG_STARTZEIT1_COL, vorschlag.getStartzeit1());
        contentValues.put(VORSCHLAG_STARTZEIT2_COL, vorschlag.getStartzeit2());
        contentValues.put(VORSCHLAG_DATE_COL, vorschlag.getDatum());
        contentValues.put(VORSCHLAG_ANDERE_ID_COL, vorschlag.getAndereId());
        contentValues.put(VORSCHLAG_NAME_COL, vorschlag.getName());
        contentValues.put(VORSCHLAG_TELEFON_COL, vorschlag.getTelefon());
        contentValues.put(VORSCHLAG_CONF1_COL, vorschlag.getConf1());
        contentValues.put(VORSCHLAG_CONF2_COL, vorschlag.getConf2());
        contentValues.put(VORSCHLAG_PROZENT_COL, vorschlag.getProzent());
        contentValues.put(VORSCHLAG_ZEIT_COL, vorschlag.getProzent());
        long result = db.insert(VORSCHLAG_TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean updateVorschlag(Vorschlag vorschlag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VORSCHLAG_ID_COL, vorschlag.getId());
        contentValues.put(VORSCHLAG_MENSA_COL, vorschlag.getMensa());
        contentValues.put(VORSCHLAG_STARTZEIT1_COL, vorschlag.getStartzeit1());
        contentValues.put(VORSCHLAG_STARTZEIT2_COL, vorschlag.getStartzeit2());
        contentValues.put(VORSCHLAG_DATE_COL, vorschlag.getDatum());
        contentValues.put(VORSCHLAG_ANDERE_ID_COL, vorschlag.getAndereId());
        contentValues.put(VORSCHLAG_NAME_COL, vorschlag.getName());
        contentValues.put(VORSCHLAG_CONF1_COL, vorschlag.getConf1());
        contentValues.put(VORSCHLAG_CONF2_COL, vorschlag.getConf2());
        contentValues.put(VORSCHLAG_PROZENT_COL, vorschlag.getProzent());
        contentValues.put(VORSCHLAG_ZEIT_COL, vorschlag.getProzent());
        db.update(VORSCHLAG_TABLE_NAME, contentValues, "matchId = ?", new String[] { Integer.toString(vorschlag.getId()) });
        return true;
    }

    public Vorschlag getVorschlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        Vorschlag vorschlag = new Vorschlag();
        Cursor result = db.rawQuery("SELECT * FROM " + VORSCHLAG_TABLE_NAME, null);

        if (result.moveToNext()) {

            if (result.getInt(0) > 0) {
                Log.d(TAG, "getVorschlag: Result > 0");
                vorschlag.setId(result.getInt(0));
                vorschlag.setMensa(result.getString(1));
                vorschlag.setEigeneId(result.getInt(2));
                vorschlag.setStartzeit1(result.getString(3));
                vorschlag.setStartzeit2(result.getString(4));
                vorschlag.setDatum(result.getString(5));
                vorschlag.setAndereId(result.getInt(6));
                vorschlag.setName(result.getString(7));
                vorschlag.setTelefon(result.getString(8));
                vorschlag.setConf1(result.getInt(9));
                vorschlag.setConf2(result.getInt(10));
                vorschlag.setProzent(result.getInt(11));
                vorschlag.setZeit(result.getString(12));
            } else {
                Log.d(TAG, "getVorschlag: Result < 0");
                vorschlag = null;
            }
        }

        return vorschlag;
    }

}
