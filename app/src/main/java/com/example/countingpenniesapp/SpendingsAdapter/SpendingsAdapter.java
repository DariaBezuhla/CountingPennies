package com.example.countingpenniesapp.SpendingsAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countingpenniesapp.AddNewSpending;
import com.example.countingpenniesapp.MainActivity;
import com.example.countingpenniesapp.R;
import com.example.countingpenniesapp.Utils.DataBaseHandler;
import com.example.countingpenniesapp.model.InsertedDataModel;
import java.util.List;

public class SpendingsAdapter extends RecyclerView.Adapter<SpendingsAdapter.ViewHolder> {

    private List<InsertedDataModel> spendingsList;
    private MainActivity activity;
    private DataBaseHandler db;
    DialogInterface dialog;

    public SpendingsAdapter(DataBaseHandler db, MainActivity activity) {
        this.activity = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entered_value_layout, parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();

        final InsertedDataModel item = spendingsList.get(position);
        holder.spendingName.setText(item.getSpendingName());
        holder.spendingValue.setText(item.getSpendingValue());
    }

    public int getItemCount() {
        return spendingsList.size();
    }

    public Context getContext() { return activity; }

    public void setSpendings(List<InsertedDataModel> spendingsList){
        this.spendingsList = spendingsList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        InsertedDataModel item = spendingsList.get(position);
        db.deleteSpending(item.getId());
        spendingsList.remove(position);
        notifyItemRemoved(position);
        activity.handleDialogClose(dialog);
    }

    public void editItem(int position) {
        InsertedDataModel item = spendingsList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("spendingName", item.getSpendingName());
        bundle.putString("spendingValue", item.getSpendingValue());
        AddNewSpending fragment = new AddNewSpending();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewSpending.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView spendingValue;
        TextView spendingName;

        ViewHolder(View view){
            super(view);
            spendingValue = view.findViewById(R.id.valueOfSpending);
            spendingName = view.findViewById(R.id.nameOfSpending);
        }
    }

}



