package com.example.countingpenniesapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.countingpenniesapp.Utils.DataBaseHandler;
import com.example.countingpenniesapp.model.InsertedDataModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddNewSpending extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newSpendingName;
    private EditText newSpendingValue;
    private Spinner categorySpinner;
    private String categoryName;
    private Button newSpendingSaveButton;
    private DataBaseHandler db;

    public static AddNewSpending newInstance() {
        return new AddNewSpending();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adding_value_layout, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newSpendingName = requireView().findViewById(R.id.spendingNameView);
        newSpendingValue = getView().findViewById(R.id.spendingAmountView);
        newSpendingSaveButton = getView().findViewById(R.id.addingValueButton);
        //drop down menu in addValue for category
        categorySpinner = requireView().findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.categories_types, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


        db = new DataBaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String spending = bundle.getString("spendingName");
            String spendingValue = bundle.getString("spendingValue");
            newSpendingName.setText(spending);
            newSpendingValue.setText(spendingValue);


            if (spending.length() > 0)
                newSpendingSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSecondary));
        }


        db = new DataBaseHandler(getActivity());
        db.openDatabase();


        newSpendingName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newSpendingSaveButton.setEnabled(false);
                    newSpendingSaveButton.setTextColor(Color.GRAY);
                } else {
                    newSpendingSaveButton.setEnabled(true);
                    newSpendingSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorSecondary));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        final boolean finalIsUpdated = isUpdate;
        // we trying to update existing task or create a new one
        newSpendingSaveButton.setOnClickListener(v -> {
            String name = newSpendingName.getText().toString();
            String value = newSpendingValue.getText().toString();

            if (finalIsUpdated) {
                db.updateSpendingName(bundle.getInt("id"), name);
                db.updateSpendingValue(bundle.getInt("id"), value);
                db.updateCategory(bundle.getInt("id"), categoryName);
            } else if (!name.isEmpty() && !value.isEmpty()) {
                //if update is false means i am trying to create a new entry
                InsertedDataModel newSpending = new InsertedDataModel();
                newSpending.setSpendingName(name);
                newSpending.setSpendingValue("€ " + value);
                newSpending.setCategory(categoryName);
                db.insertSpending(newSpending);

                //TODO: add DATE?
            }
            dismiss();
        });
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        //TODO: maybe transfer to the java package?
        //interface which will contain functions for db tasks suchs as refreshing and updatign etc. can be in JAVA package?
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
