package com.example.postapp.ui.main.sqlite.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.postapp.ui.main.model.Item;

@Database(entities = {Item.class},version = 2)
public abstract class ItemsDatabase extends RoomDatabase {

    //an abstract method that returns the Dao
    public abstract ItemDao movieDao();
}
