package com.example.mybtl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MovieDAO {
    public static ArrayList<Movie> getAll(Context context) {
        ArrayList<Movie> ds = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from Movies", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int image = cs.getInt(2);
            String content = cs.getString(3);
            String category = cs.getString(4);
            int trailer = cs.getInt(5);
            String time = cs.getString(6);
            int price = cs.getInt(7);
            Movie movie = new Movie(id, name, image, content, category, trailer, time, price);
            ds.add(movie);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return ds;
    }
}
