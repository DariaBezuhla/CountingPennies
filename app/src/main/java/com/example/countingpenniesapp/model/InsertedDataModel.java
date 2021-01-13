package com.example.countingpenniesapp.model;

public class InsertedDataModel {

    private int id;
    private String spendingValue;
    private String spendingName;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpendingValue() {
        return spendingValue;
    }

    public void setSpendingValue(String spendingValue) {
        this.spendingValue = spendingValue;
    }

    public String getSpendingName() {
        return spendingName;
    }

    public void setSpendingName(String spendingName) {
        this.spendingName = spendingName;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

}
