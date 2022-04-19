package com.example.hokageappdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HokageDatabaseHelper extends SQLiteOpenHelper {

    //This is the database
    private SQLiteDatabase db;
    //This is the database version
    private static final int DB_VERSION = 1;
    //This is the database name
    private static final String DB_NAME = "HokageDb";
    //This is the database table (1)
    private static final String NOTE_TABLE = "Hokages";

    //These are the column names
    private static final String NAME = "Name";
    private static final String DESCRIPTION = "Description";

    //Creating your table
    private static final String CREATE_HOKAGE_TABLE_QUERY = "CREATE TABLE HOKAGE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "NAME TEXT, "
            + "DESCRIPTION TEXT, "
            + "IMAGE_RESOURCE_ID INTEGER);";

    //This statement is if the Table does not exists
    private static final String DROP_HOKAGE_TABLE_QUERY = "DROP TABLE IF EXISTS " + NOTE_TABLE;


    public HokageDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HOKAGE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        db.execSQL(DROP_HOKAGE_TABLE_QUERY);

        onCreate(db);
    }

    //open database:
    public void openDatabase()
    {
        db = this.getWritableDatabase();
    }
}

