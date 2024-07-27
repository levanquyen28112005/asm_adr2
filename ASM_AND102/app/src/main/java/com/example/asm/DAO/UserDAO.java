package com.example.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.data.DBHelper;
import com.example.asm.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final DBHelper dbHelper;
    Context context;

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public List<User> GetAllListUser(){
        List<User> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM USERACCOUNT", null);

            if(cursor.getCount() > 0){
                cursor.moveToFirst();

                do {
                    list.add(new User(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3)));
                }while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.endTransaction();
        }

        return list;
    }

    public boolean AddUser(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("fullname", user.getFullname());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("USERACCOUNT", null, contentValues);

        return check != -1;
    }
    public boolean UpdateUser(User user, int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("fullname", user.getFullname());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("USERACCOUNT", contentValues, "id=?", new String[]{String.valueOf(id)});

        return check != -1;
    }


    public boolean DeleteUser(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("USERACCOUNT", "id=?", new String[]{String.valueOf(id)});
        return check != -1;
    }
}
