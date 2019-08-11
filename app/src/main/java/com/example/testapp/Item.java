package com.example.testapp;

public class Item {
    private String var_name;
    private Boolean var_checked;

    public Item() {

    }

    public Item(String name, Boolean checked) {
        var_name = name;
        var_checked = checked;
    }

    //Getters
    public String getName() {
        return var_name;
    }

    //Setters
    public void setName(String name) {
        var_name = name;
    }

    public Boolean getChecked() {
        return var_checked;
    }

    public void setChecked(Boolean checked) {
        var_checked = checked;
    }
}
