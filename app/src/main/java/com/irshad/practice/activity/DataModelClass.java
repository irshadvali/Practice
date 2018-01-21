package com.irshad.practice.activity;

/**
 * Created by irshadvali on 21/01/18.
 */

public class DataModelClass {
    String id;
    String name;

    public DataModelClass() {
    }

    public DataModelClass(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
