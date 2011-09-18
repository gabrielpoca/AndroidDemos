/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielpoca.android.textfast.database;

import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author gabriel
 */
public class MsgData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "textfast.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "messages";
    public static final String MESSAGE_COLUMN = "message";
    public static final String ICON_COLUMN = "icon";

    public MsgData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ICON_COLUMN + " INTEGER,"
                + MESSAGE_COLUMN + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    
}
