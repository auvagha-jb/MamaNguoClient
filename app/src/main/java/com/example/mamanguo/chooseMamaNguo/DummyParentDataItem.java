package com.example.mamanguo.chooseMamaNguo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <b></b>
 * <p>This class is used to </p>
 * Created by Rohit.
 */
class DummyParentDataItem implements Serializable {
    private String parentName;
    private float rating;
    private ArrayList<DummyChildDataItem> childDataItems;

    public DummyParentDataItem(String parentName, float rating, ArrayList<DummyChildDataItem> childDataItems) {
        this.parentName = parentName;
        this.rating = rating;
        this.childDataItems = childDataItems;
    }

    public String getParentName() {
        return parentName;
    }

    public float getRating() {
        return rating;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<DummyChildDataItem> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<DummyChildDataItem> childDataItems) {
        this.childDataItems = childDataItems;
    }
}
