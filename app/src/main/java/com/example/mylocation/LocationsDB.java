package com.example.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper {
    //Database version
    private static final int   DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "Locations.db";

    //Table name
    private static final String TABLE_NAME = "visited_locations";

    private static final String COLUMN_Id = "id";
    private static final String COLUMN_CreateAt = "CreateAt";
    private static final String COLUMN_UpdateAt = "UpdateAt";
    private static final String COLUMN_Longitude = "Longitude";
    private static final String COLUMN_Latitude = "Latitude";



    public LocationsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CreateAt TEXT,UpdateAt TEXT,Longitude REAL,Latitude REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    //insert data to database.
    public void insertData(double Longitude, double Latitude){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CreateAt, "");
        contentValues.put(COLUMN_UpdateAt,"");
        contentValues.put(COLUMN_Longitude,Longitude);
        contentValues.put(COLUMN_Latitude,Latitude);
        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        long result=  sqLiteDB.insert(TABLE_NAME, null,contentValues);
        sqLiteDB.close();
    }

    //get all data from database.
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }
    //delete all data from database.
    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"",null);
    }

    public void updateData(){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME ,contentValues, "_id"+COLUMN_Id,null);
    }
    public void deleteRecordID(int id){
        String query = "DELETE FROM "+ TABLE_NAME +" WHERE " + COLUMN_Id +" = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
}