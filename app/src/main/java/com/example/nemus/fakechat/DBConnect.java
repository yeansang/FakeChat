package com.example.nemus.fakechat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Vector;

/**
 * Created by nemus on 2016-06-27.
 */
public class DBConnect extends SQLiteOpenHelper{
    public DBConnect(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CHAT( id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean input(String in){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO CHAT (word) VALUES (\""+in+"\");");
        db.close();
        return true;
    }

    public Vector<String> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CHAT",null);
        Vector<String> out = new Vector<String>();

        if(cursor.isAfterLast()){
            return null;
        }

        while(cursor.moveToNext()){
            String str = cursor.getString(1);
            out.add(str);
        }
        return out;
    }

}
