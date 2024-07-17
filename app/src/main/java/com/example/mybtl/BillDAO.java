package com.example.mybtl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BillDAO {
    public static boolean insert(Context context,int idMovie, String movieName, String moviePremiere,int moviePrice,String dateOrder,String timeOrder, String selectedChair, String selectedFood,String methodPayment, String email, int totalPrice){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int idBill = generateRandomIdBill();
        values.put("IdBill", idBill);
        values.put("IdMovie", idMovie);
        values.put("MovieName", movieName);
        values.put("MoviePremiere", moviePremiere);
        values.put("MoviePrice", moviePrice);
        values.put("DateOrder", dateOrder);
        values.put("TimeOrder", timeOrder);
        values.put("SelectedChair", selectedChair);
        values.put("SelectedFood", selectedFood);
        values.put("MethodPayment", methodPayment);
        values.put("Email", email);
        values.put("TotalPrice", totalPrice);
        long row = db.insert("Bills", null, values);
        return (row > 0);
    }

    public static int generateRandomIdBill() {
        Random random = new Random();
        int randomIdBill = 1000000 + random.nextInt(9000000);
        return randomIdBill;
    }

    public static List<String> getSelectedChairs(Context context, int idMovie, String dateOrder, String timeOrder) {
        List<String> selectedChairs = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = {"SelectedChair"};
        String selection = "IdMovie = ? AND DateOrder = ? AND TimeOrder = ?";
        String[] selectionArgs = {String.valueOf(idMovie), dateOrder, timeOrder};
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

    public static boolean isChairSelected(Context context, int idMovie, String dateOrder, String timeOrder, int chairNumber) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selection = "IdMovie = ? AND DateOrder = ? AND TimeOrder = ? AND SelectedChair LIKE ?";
        String[] selectionArgs = { String.valueOf(idMovie), dateOrder, timeOrder, "%" + chairNumber + "%" };

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
        Cursor cs = db.rawQuery("SELECT * FROM Bills WHERE Email LIKE ? ORDER BY Id DESC", new String[]{email});
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            int idBill = cs.getInt(1);
            int idMovie = cs.getInt(2);
            String movieName = cs.getString(3);
            String moviePremiere = cs.getString(4);
            int moviePrice = cs.getInt(5);
            String dateOrder = cs.getString(6);
            String timeOrder = cs.getString(7);
            String selectedChair = cs.getString(8);
            String selectedFood = cs.getString(9);
            String methodPayment = cs.getString(10);
            int totalPrice = cs.getInt(12);
            Bill bill = new Bill(id,idBill,idMovie, movieName, moviePremiere,moviePrice,dateOrder,timeOrder,selectedChair,selectedFood,methodPayment,email,totalPrice);
            ds.add(bill);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return ds;
    }
}
