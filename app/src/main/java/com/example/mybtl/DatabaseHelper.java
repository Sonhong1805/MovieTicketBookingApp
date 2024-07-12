package com.example.mybtl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "MovieManage", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsers = "create table Users(" +
                "Id integer primary key autoincrement," +
                "Fullname text," +
                "Email text," +
                "Password text," +
                "PhoneNumber text" + ")";
        db.execSQL(sqlUsers);

        String sqlMovies = "create table Movies(" +
                "Id integer primary key," +
                "Name text," +
                "Image integer," +
                "Content text," +
                "Category text," +
                "Trailer integer," +
                "Premiere text," +
                "Price integer" + ")";
        db.execSQL(sqlMovies);

        // bổ sung sau ngày đặt + giờ đặt
        String sqlBills = "create table Bills(" +
                "Id integer primary key," +
                "MovieName text," +
                "MoviePremiere text," +
                "MoviePrice integer," +
                "SelectedChair text," +
                "SelectedFood text," +
                "MethodPayment text," +
                "Email text," +
                "TotalPrice integer" + ")";
        db.execSQL(sqlBills);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Movies");
        db.execSQL("DROP TABLE IF EXISTS Bills");
        onCreate(db);
    }
}
