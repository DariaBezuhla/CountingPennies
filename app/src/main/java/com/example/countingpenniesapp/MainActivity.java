package com.example.countingpenniesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.example.countingpenniesapp.Adapter.SpendingsAdapter;
import com.example.countingpenniesapp.Utils.DataBaseHandler;
import com.example.countingpenniesapp.model.InsertedDataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DialogCloseListener {


    private RecyclerView spendingsRecyclerView;
    private SpendingsAdapter spendingsAdapter;
    private FloatingActionButton fab;
    private List<InsertedDataModel> spendingsList;
    private DataBaseHandler db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DataBaseHandler(this);
        db.openDatabase();

        spendingsList = new ArrayList<>();
        spendingsRecyclerView = findViewById(R.id.spendingsRecyclerView);
        spendingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        spendingsAdapter = new SpendingsAdapter(db,MainActivity.this);
        spendingsRecyclerView.setAdapter(spendingsAdapter);

        fab = findViewById(R.id.fab);

        //for swiping to right or left and deleting/editing
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper( new RecyclerItemTouchHelper(spendingsAdapter));
        itemTouchHelper.attachToRecyclerView(spendingsRecyclerView);

       spendingsList = db.getAllSpendings();
       Collections.reverse(spendingsList);
       spendingsAdapter.setSpendings(spendingsList);

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                AddNewSpending.newInstance().show(getSupportFragmentManager(), AddNewSpending.TAG);
           }
       });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        spendingsList = db.getAllSpendings();
        //so that the new task is on top of the stack
        Collections.reverse(spendingsList);
        spendingsAdapter.setSpendings(spendingsList);
        spendingsAdapter.notifyDataSetChanged();

    }
}