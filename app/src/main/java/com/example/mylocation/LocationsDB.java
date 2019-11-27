package com.example.mylocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class LocationsDB extends SQLiteOpenHelper {
    //Database version
    private static final int   DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "locations.db";

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
        db.delete(TABLE_NAME,"",null );
    }

    public  void deleteLocation(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_Id + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public Cursor getItemID(String Longitude, String Latitude ){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_Id + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_Longitude+ " = '" +Longitude + "'" + "AND" +COLUMN_Latitude+ "= '"+ Latitude+">";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateData(int id,String Longitude, String Latitude){
        SQLiteDatabase sqLiteDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UpdateAt,"");
        contentValues.put(COLUMN_Longitude,Longitude);
        contentValues.put(COLUMN_Latitude,Latitude);
        sqLiteDB.update(TABLE_NAME,contentValues,"id = ?", new String[]{String.valueOf(id)});
    }
}