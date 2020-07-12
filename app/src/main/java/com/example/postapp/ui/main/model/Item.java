package com.example.postapp.ui.main.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (indices = @Index(value = {"itemId"}, unique = true)) // - Table
public class Item {

    //Primary Key AutoIncrement
    @PrimaryKey(autoGenerate = true)

    //public for the room
    public int id;
    public int itemId;
    public String itemName;
    public String pictureLink;
    public String costNis;

    @Ignore // tell room to ignore this constructor
    public Item(int itemId, String itemName, String pictureLink, String costNis) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.pictureLink = pictureLink;
        this.costNis = costNis;
    }

    public Item(int id, int itemId, String itemName, String pictureLink, String costNis) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.pictureLink = pictureLink;
        this.costNis = costNis;
    }





    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCostNis() {
        return costNis;
    }

    public String getPictureLink() {
        return pictureLink;
    }
}
