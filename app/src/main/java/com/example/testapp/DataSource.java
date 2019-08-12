package com.example.testapp;

import java.util.ArrayList;

public class DataSource {
    private ArrayList<Item> listItem;

    private Boolean event = false;

    public DataSource() {
        listItem = new ArrayList<>();
    }

    // Method generates dataset for testing
    public ArrayList<Item> getTestDataset(){
        for (int i = 0; i <= 15; i++){
            listItem.add(new Item("Item " + i, true));
        }
        return listItem;
    }

    public void addItem(Item sample){
        listItem.add(sample);
        event = true;
    }

    public void removeItem(int index){
        listItem.remove(index);
        event = true;
    }


}
