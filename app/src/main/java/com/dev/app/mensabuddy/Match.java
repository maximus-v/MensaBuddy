package com.dev.app.mensabuddy;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chris on 09.01.2017.
 */

public class Match {

    int UserID;
    String Mensa;
    int Zeit1;
    DatabaseHelper myDb;
    HashMap<String, String> MatchList = new HashMap<>();

    public Match(Context context){
        myDb = new DatabaseHelper(context);
    }


    //gives UserID of first possible match
    public int findFastMatch(int UserId, String Mensa, int Zeit1){
        this.UserID=UserId;
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
    public List<Integer> findAllMatches(String Mensa, int Zeit1){
        this.Zeit1=Zeit1;
        this.Mensa=Mensa;
        List result = new ArrayList<Integer>();
        Cursor Matches;
        int i=0;
        Matches = myDb.getTimeMatches(Zeit1, Mensa);
        Matches.moveToFirst();
        do{
            result.add(i, Matches.getInt(1));
            i++;
        } while (Matches.moveToNext());


        return result;
    }

    //gives UserID of best Match (by time, canteen and interests)
    public int findBestMatch(int UserID, String Mensa, int Zeit1){
        this.UserID=UserID;
        Cursor Initiator = myDb.getProfilesById(UserID);
        String InitiatorFak = Initiator.getString(3);


        List Matches = new ArrayList<Integer>();
        Matches = this.findAllMatches(Mensa, Zeit1);
        ArrayList<Cursor> User = new ArrayList<>();
        for (int i=0; i<Matches.size(); i++){
            User.add(i, myDb.getProfilesById((Integer) Matches.get(i)));
        }
        int result=-1;
        for (int i=0; i<User.size();i++){
            if (User.get(i).getString(3).equals(InitiatorFak)){
                result = User.get(i).getInt(0);
            }
        }
        return result;
    }
}
