package com.example.tablayout.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.tablayout.Database.DatabaseContract.KataColumns.DESCRIPTION;
import static com.example.tablayout.Database.DatabaseContract.KataColumns.WORD;
import static com.example.tablayout.Database.DatabaseContract.TABLE_INDONESIA;
import static com.example.tablayout.Database.DatabaseContract.TABLE_BETAWI;

import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_dictionary";
    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE_INDO = "create table " + TABLE_INDONESIA +
            " (" + _ID + " integer primary key autoincrement, " + WORD + " text not null, " +
            DESCRIPTION + " text not null);";

    private static final String CREATE_TABLE_BETAWI = "create table " + TABLE_BETAWI +
            " (" + _ID + " integer primary key autoincrement, " + WORD + " text not null, " +
            DESCRIPTION + " text not null);";

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO);
        db.execSQL(CREATE_TABLE_BETAWI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDONESIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BETAWI);
        onCreate(db);

    }
}
