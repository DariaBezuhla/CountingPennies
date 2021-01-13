package com.example.countingpenniesapp.SpendingsAdapter;
import com.example.countingpenniesapp.model.InsertedDataModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SpendingsAdapterTest {

    private SpendingsAdapter adapter;



    @Test(expected = NullPointerException.class)
    public void setSpendingsTest() {
        SpendingsAdapter adapter = new SpendingsAdapter(null, null);
        InsertedDataModel item = new InsertedDataModel();
        item.setId(1);
        item.setSpendingValue("100");
        item.setSpendingName("My test expense");
        item.setCategory("Expense");

        List<InsertedDataModel> spendingsList = new ArrayList<>();
        spendingsList.add(item);


        adapter.setSpendings(spendingsList);
        assertEquals(spendingsList, adapter.spendingsList);
    }



}