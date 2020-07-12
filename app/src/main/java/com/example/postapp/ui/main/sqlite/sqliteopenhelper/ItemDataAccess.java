package com.example.postapp.ui.main.sqlite.sqliteopenhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.postapp.ui.main.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDataAccess {
    public static final String TABLE = "Items";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String PICTURE_LINK = "picture_link";
    public static final String ITEM_ID = "item_id";
    ItemOpenHelper db;

    private static ItemDataAccess shared;

    public static ItemDataAccess getInstance(Context context){
        if (shared == null){
            shared = new ItemDataAccess(context);
        }
        return shared;
    }

    private ItemDataAccess(Context context) {

        db = new ItemOpenHelper(context);
    }

    public void add(Item item){
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,item.getItemName());
        values.put(PRICE,item.getCostNis());
        values.put(PICTURE_LINK,item.getPictureLink());

        database.insertWithOnConflict(TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<Item> getAll(){
        SQLiteDatabase database = db.getReadableDatabase();
        List<Item>movies = new ArrayList<>();
        //SELECT * FROM Movies:
        try( Cursor cursor = database.query(TABLE,null,null,null,null,null,null)) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(NAME));
                String price = cursor.getString(cursor.getColumnIndex(PRICE));
                String picture = cursor.getString(cursor.getColumnIndex(PICTURE_LINK));
                int itemId = cursor.getInt(cursor.getColumnIndex(ITEM_ID));

                movies.add(new Item(id,itemId, name,picture,price));
            }
        }

        return movies;
    }

    public void update(Item item){
        SQLiteDatabase database = db.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,item.id);
        values.put(NAME,item.itemName);
        values.put(PRICE,item.costNis);
        values.put(PICTURE_LINK,item.pictureLink);
        values.put(ITEM_ID,item.itemId);


        database.update(TABLE,values,ID + "=?",new String[]{String.valueOf(item.id)});
    }

    public void delete(Item item){
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete(TABLE,ID + "=?",new String[]{String.valueOf(item.id)});
    }
}
