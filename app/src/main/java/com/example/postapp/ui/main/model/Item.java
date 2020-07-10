package com.example.postapp.ui.main.model;

public class Item {

    private int itemId;
    private String itemName;
    private String pictureLink;
    private String costNis;

    public Item(int itemId, String itemName, String pictureLink, String costNis) {
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
