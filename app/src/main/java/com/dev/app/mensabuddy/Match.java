package com.dev.app.mensabuddy;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;

/**
 * Created by chris on 09.01.2017.
 */

public class Match {

    int UserId;
    String Mensa;
    int Zeit1;
    DatabaseHelper myDb;
    HashMap<String, String> MatchList = new HashMap<>();

    public Match(Context context){
        myDb = new DatabaseHelper(context);
    }


    //gives UserID of first possible match
    public int FindFastMatch(int UserId, String Mensa, int Zeit1){
        this.UserId=UserId;
        this.Mensa=Mensa;
        int i=0;
        this.Zeit1=Zeit1;

        Cursor Matches;
        int MatchIndex=-1;
        Matches = myDb.getTimeMatches(Zeit1, Mensa);
        Matches.moveToFirst();
        do{
            MatchList.put(Matches.getInt(0) + "", Matches.getInt(1) +"");
            MatchIndex=Matches.getInt(1);
        } while (Matches.moveToNext());

        return Integer.parseInt(MatchList.get(MatchIndex));
    }

    //gives UserIDs of all possible matches (by time and canteen)
    public int[] FindAllMatches(){
        return null;
    }

    //gives UserID of best Match (by time, canteen and interests)
    public int FindBestMatch(){
        return 0;
    }
}
