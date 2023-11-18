package com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "item_db";
    private static final int DATABASE_VERSION = 1;

    // Define the table and column names
    protected static final String TABLE_ITEMS = "items";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_ITEM_NAME = "item_name";

    // SQL statement to create the items table
    private static final String CREATE_TABLE_ITEMS =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM_NAME + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database tables
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if necessary
    }
}