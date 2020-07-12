package com.example.postapp.ui.main.sqlite.sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ItemOpenHelper extends SQLiteOpenHelper {

    //constructor:
    public ItemOpenHelper(@Nullable Context context) {
        super(context, "MoviesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL to create your tables:
        sqLiteDatabase.execSQL("CREATE TABLE Movies(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "overview text, " +
                "poster_path text, " +
                "title text, " +
                "release_date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //drop the database and recreate it
        sqLiteDatabase.execSQL("DROP TABLE Movies");
        onCreate(sqLiteDatabase);
    }
}
