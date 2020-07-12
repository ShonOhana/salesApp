package com.example.postapp.ui.main.sqlite.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import com.example.postapp.ui.main.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    //methods for data access:

    @Query("SELECT * FROM item")
    List<Item> getAllMovies();

    @PrimaryKey
    @Query("SELECT * FROM item WHERE itemName LIKE :name")
    List<Item>getByTitle(String name);

    @Query("SELECT * FROM item WHERE id LIKE :id")
    Item getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Item i);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item...i);


    @Update
    void update(Item i);

    @Delete
    void delete(Item i);
    @Delete
    void deleteAll(Item...i);
}
