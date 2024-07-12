package com.example.mybtl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BillDAO {
    public static ArrayList<Bill> getAll(Context context) {
        ArrayList<Bill> ds = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from Bills", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String movieName = cs.getString(1);
            String moviePremiere = cs.getString(2);
            int moviePrice = cs.getInt(3);
            String selectedChair = cs.getString(4);
            String selectedFood = cs.getString(5);
            String methodPayment = cs.getString(6);
            String email = cs.getString(7);
            int totalPrice = cs.getInt(8);
            Bill bill = new Bill(id, movieName, moviePremiere,moviePrice,selectedChair,selectedFood,methodPayment,email,totalPrice);
            ds.add(bill);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return ds;
    }
    public static boolean insert(Context context, String movieName, String moviePremiere,int moviePrice, String selectedChair, String selectedFood,String methodPayment, String email, int totalPrice){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int id = generateRandomId(10);
        values.put("Id", id);
        values.put("MovieName", movieName);
        values.put("MoviePremiere", moviePremiere);
        values.put("MoviePrice", moviePrice);
        values.put("SelectedChair", selectedChair);
        values.put("SelectedFood", selectedFood);
        values.put("MethodPayment", methodPayment);
        values.put("Email", email);
        values.put("TotalPrice", totalPrice);
        long row = db.insert("Bills", null, values);
        return (row > 0);
    }

    private static int generateRandomId(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public static List<String> getSelectedChairsByMovie(Context context, String movieName) {
        List<String> selectedChairs = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {"SelectedChair"};
        String selection = "MovieName = ?";
        String[] selectionArgs = {movieName};
        Cursor cursor = db.query("Bills", columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String selectedChair = cursor.getString(cursor.getColumnIndexOrThrow("SelectedChair"));
                selectedChairs.add(selectedChair);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return selectedChairs;
    }

    public static boolean isChairSelected(Context context,String movieName, int chairNumber) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selection = "MovieName = ? AND SelectedChair LIKE ?";
        String[] selectionArgs = { movieName, "%" + chairNumber + "%" };

        Cursor cursor = null;
        try {
            cursor = db.query("Bills", null, selection, selectionArgs, null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public static ArrayList<Bill> filterByEmail(Context context,String email) {
        ArrayList<Bill> ds = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM Bills WHERE Email LIKE ?", new String[]{email});
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String movieName = cs.getString(1);
            String moviePremiere = cs.getString(2);
            int moviePrice = cs.getInt(3);
            String selectedChair = cs.getString(4);
            String selectedFood = cs.getString(5);
            String methodPayment = cs.getString(6);
            int totalPrice = cs.getInt(8);
            Bill bill = new Bill(id, movieName, moviePremiere,moviePrice,selectedChair,selectedFood,methodPayment,email,totalPrice);
            ds.add(bill);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return ds;
    }
}
