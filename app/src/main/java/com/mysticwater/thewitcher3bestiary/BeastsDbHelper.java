package com.mysticwater.thewitcher3bestiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.mysticwater.thewitcher3bestiary.BeastsContract.*;

/**
 * Created by James on 01/08/2015.
 */
public class BeastsDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BeastEntry.TABLE_NAME + " (" +
                    BeastEntry._ID + " INTEGER PRIMARY KEY," +
                    BeastEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    BeastEntry.COLUMN_NAME_BEAST + TEXT_TYPE + COMMA_SEP +
                    BeastEntry.COLUMN_NAME_VULNERABILITIES + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + BeastEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Beasts.db";

    public BeastsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}