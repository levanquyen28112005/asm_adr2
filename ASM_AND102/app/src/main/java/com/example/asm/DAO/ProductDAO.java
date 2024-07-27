package com.example.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.data.DBHelper;
import com.example.asm.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final DBHelper dbHelper;
    Context context;

    public ProductDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }


    public List<Product> GetAllListProduct() {
        List<Product> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM PRODUCT", null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    list.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                } while (cursor.moveToNext());
                database.setTransactionSuccessful();
            }

        } catch (Exception e) {

        } finally {
            database.endTransaction();
        }

        return list;
    }

    public boolean AddProduct(Product product){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", product.getName());
        contentValues.put("cost", product.getCost());
        contentValues.put("quantity", product.getQuantity());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("PRODUCT", null, contentValues);
        return check != -1;
    }
    public boolean UpdateProduct(Product product, int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", product.getName());
        contentValues.put("cost", product.getCost());
        contentValues.put("quantity", product.getQuantity());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.update("PRODUCT", contentValues, "id = ?", new String[]{String.valueOf(id)});
        return check != -1;
    }

    public boolean DeleteProduct(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<Product> list = GetAllListProduct();

        long check = database.delete("PRODUCT", "id=?", new String[]{String.valueOf(list.get(id).getId())});
        return check!=-1;
    }
}
