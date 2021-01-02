package com.example.countingpenniesapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.countingpenniesapp.model.InsertedDataModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


 public class DataBaseHandler extends SQLiteOpenHelper {

     private static final int VERSION = 2;
     private static final String NAME = "PenniesDatabase";
     private static final String SPENDINGS_TABLE = "spendings";
     private static final String COLUMN_ID = "id";
     private static final String COLUMN_SPENDING_VALUE = "spendingValue";
     private static final String COLUMN_SPENDING_NAME = "spendingName";
     private static final String COLUMN_CATEGORY = "category";

     //TODO: ADD DATE IF YOU HAVE TIME

     private SQLiteDatabase db;

     public DataBaseHandler(Context context) {
         super(context, NAME, null, VERSION);
     }

     public void onCreate(SQLiteDatabase db) {

     String sql = "CREATE TABLE " + SPENDINGS_TABLE +
             " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
             " " + COLUMN_SPENDING_NAME +  " TEXT," +
             " " + COLUMN_SPENDING_VALUE + " TEXT," +
             " " + COLUMN_CATEGORY + " TEXT" + ");";
         db.execSQL(sql);
 }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop the older tables
        db.execSQL("DROP TABLE IF EXISTS " + SPENDINGS_TABLE);
        //CREATES TABLES AGAIN
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertSpending(InsertedDataModel spending) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SPENDING_NAME, spending.getSpendingName());
        contentValues.put(COLUMN_SPENDING_VALUE, spending.getSpendingValue());
        contentValues.put(COLUMN_CATEGORY, spending.getCategory());
        //contentValues.put(DATE.toString(), spending.getDate().toString());
        db.insert(SPENDINGS_TABLE, null, contentValues);

    }

    public List<InsertedDataModel> getAllSpendings() {
        List<InsertedDataModel> spendingsList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();

        try {
            cursor = db.query(true, SPENDINGS_TABLE, null, null, null,
                    null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        InsertedDataModel spending = new InsertedDataModel();
                        spending.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                        spending.setSpendingName(cursor.getString(cursor.getColumnIndex(COLUMN_SPENDING_NAME)));
                        spending.setSpendingValue(cursor.getString(cursor.getColumnIndex(COLUMN_SPENDING_VALUE)));
                        spending.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                        //TODO: ADD DATE IF YOU HAVE TIME
                        //spending.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                        spendingsList.add(spending);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return spendingsList;
    }

    public void updateSpendingName(int id, String spendingName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SPENDING_NAME, spendingName);
        db.update(SPENDINGS_TABLE, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateSpendingValue(int id, String spendingValue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SPENDING_VALUE, spendingValue);
        db.update(SPENDINGS_TABLE, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateCategory(int id, String category) {
         ContentValues contentValues = new ContentValues();
         contentValues.put(COLUMN_CATEGORY, category);
         db.update(SPENDINGS_TABLE, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteSpending(int id) {
        db.delete(SPENDINGS_TABLE, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

}
