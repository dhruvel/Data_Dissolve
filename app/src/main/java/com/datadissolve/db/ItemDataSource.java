package com.datadissolve.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ItemDataSource {
    private SQLiteDatabase database;
    private final DatabaseHelper dbHelper;

    public ItemDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ITEM_NAME, item.getItemName());
        database.insert(DatabaseHelper.TABLE_ITEMS, null, values);
    }

    public void updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ITEM_NAME, item.getItemName());
        database.update(DatabaseHelper.TABLE_ITEMS, values,
                DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    public void deleteItem(String itemName) {
        database.delete(DatabaseHelper.TABLE_ITEMS,
                DatabaseHelper.COLUMN_ITEM_NAME + " = ?", new String[]{String.valueOf(itemName)});
    }

    public List<String> getListOfItems() {
        List<String> itemList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    item.setItemName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEM_NAME)));
                    itemList.add(item.getItemName());
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return itemList;
    }
}