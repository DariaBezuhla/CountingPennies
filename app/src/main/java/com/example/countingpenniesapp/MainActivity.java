package com.example.countingpenniesapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingpenniesapp.SpendingsAdapter.SpendingsAdapter;
import com.example.countingpenniesapp.Utils.DataBaseHandler;
import com.example.countingpenniesapp.model.InsertedDataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements DialogCloseListener {


    private RecyclerView spendingsRecyclerView;
    private SpendingsAdapter spendingsAdapter;
    private FloatingActionButton fab;
    public List<InsertedDataModel> spendingsList;
    private DataBaseHandler db;
    private TextView currentBalance;
    Locale locale = Locale.ENGLISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DataBaseHandler(this);
        db.openDatabase();

        spendingsList = new ArrayList<>();
        spendingsRecyclerView = findViewById(R.id.spendingsRecyclerView);
        spendingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        spendingsAdapter = new SpendingsAdapter(db, MainActivity.this);
        spendingsRecyclerView.setAdapter(spendingsAdapter);

        fab = findViewById(R.id.fab);

        //for swiping to right or left and deleting/editing
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(spendingsAdapter));
        itemTouchHelper.attachToRecyclerView(spendingsRecyclerView);

        spendingsList = db.getAllSpendings();
        Collections.reverse(spendingsList);
        spendingsAdapter.setSpendings(spendingsList);

        //for updating existing balance when the app is first started
        currentBalance = findViewById(R.id.currentBalance);
        setCurrentBalance(currentBalance);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewSpending.newInstance().show(getSupportFragmentManager(), AddNewSpending.TAG);
            }
        });


    }

    public double getTotalSum() {
        List<InsertedDataModel> spendings;
        spendings = db.getAllSpendings();
        double total = 0;

        for (int i=0; i<spendings.size(); i++) {
            if (spendings.get(i).getCategory().equals("Expense")) {
                total = total - Double.parseDouble(spendings.get(i).getSpendingValue());
            } else {
                total = total + Double.parseDouble(spendings.get(i).getSpendingValue());
            }

        }

        return total;
    }

    public void setTotalSum(double total) {
        currentBalance = findViewById(R.id.currentBalance);
        currentBalance.setText(String.format(locale, "%.2f", total) );
    }

    public TextView setCurrentBalance(TextView currentBalanceCount) {
        if (spendingsList.size() > 0) {
            for (int i = 0; i < spendingsList.size(); i++) {
                String previousBalance = (String) currentBalanceCount.getText();
                String updatedBalance;
                if (spendingsList.get(i).getCategory().equals("Expense")) {
                    updatedBalance = String.format(locale, "%.2f", Double.parseDouble(previousBalance) - Double.parseDouble(spendingsList.get(i).getSpendingValue()));
                } else {
                    updatedBalance = String.format(locale, "%.2f", Double.parseDouble(previousBalance) + Double.parseDouble(spendingsList.get(i).getSpendingValue()));
                }
                currentBalanceCount.setText(updatedBalance);

            }
            return currentBalanceCount;
        }
        return currentBalanceCount;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        spendingsList = db.getAllSpendings();
        spendingsAdapter.setSpendings(spendingsList);
        Collections.reverse(spendingsList);
        this.setTotalSum(this.getTotalSum());
        spendingsAdapter.notifyDataSetChanged();

    }
}