package com.example.asm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ASM.db2";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("CHECK", "CHECK");

        String CREATE_TBL_USER = "CREATE TABLE USERACCOUNT (id INTEGER  PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, fullname TEXT)";
        String CREATE_TBL_PRODUCT = "CREATE TABLE PRODUCT (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, cost INTEGER, quantity INTEGER)";


        db.execSQL(CREATE_TBL_USER);
        db.execSQL(CREATE_TBL_PRODUCT);


        String INSERT_DATA_USER = "INSERT INTO USERACCOUNT VALUES (1, 'user1', 'user1', 'user1'), (2, 'user1', 'user1', 'user1')";
        String INSERT_DATA_PRODUCT = "INSERT INTO PRODUCT VALUES (1, 'Sản Phẩm 1', 10000, 14)," +
                "(2, 'Sản Phẩm 2', 14200, 29)," +
                "(3, 'Sản Phẩm 3', 30000, 6)," +
                "(4, 'Sản Phẩm 4', 100000, 5)," +
                "(5, 'Sản Phẩm 5', 23700, 1)," +
                "(6, 'Sản Phẩm 6', 1000000, 9)";

        db.execSQL(INSERT_DATA_PRODUCT);
        db.execSQL(INSERT_DATA_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS USER_ACCOUNT");
            db.execSQL("DROP TABLE IF EXISTS PRODUCT");
            onCreate(db);
        }
    }
}
