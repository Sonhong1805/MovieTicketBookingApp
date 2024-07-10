package com.example.mybtl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDAO {
    // chức năng đăng ký
    public static boolean register(Context context,String fullname, String email, String password, String phoneNumber){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Fullname", fullname);
        values.put("Email", email);
        values.put("Password", password);
        values.put("PhoneNumber", phoneNumber);
        long row = db.insert("Users", null, values);
        return (row > 0);
    }

    // kiểm tra email tồn tại chưa
    public static boolean checkEmail(Context context,String email){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where Email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    //kiểm tra email và password có hợp lệ không
    public static boolean checkEmailPassword(Context context,String email, String password){
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users where Email = ? and Password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    //lấy thông tin chi tiết của user
    public static User getDetails(Context context, String email) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE Email = ?", new String[]{email});
        if (cursor != null) cursor.moveToFirst();
        User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        return user;
    }

}
