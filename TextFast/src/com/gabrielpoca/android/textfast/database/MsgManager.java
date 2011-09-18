/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielpoca.android.textfast.database;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static com.gabrielpoca.android.textfast.database.MsgData.ICON_COLUMN;
import static com.gabrielpoca.android.textfast.database.MsgData.MESSAGE_COLUMN;
import static com.gabrielpoca.android.textfast.database.MsgData.TABLE_NAME;

/**
 *
 * @author gabriel
 */
public class MsgManager {
    
    private static String[] FROM = {
        ICON_COLUMN, MESSAGE_COLUMN
    };
    

    private MsgData _msg_data;

    public MsgManager(Context context) {
        _msg_data = new MsgData(context);
    }

    public Cursor getMsgs() {
        SQLiteDatabase db = _msg_data.getReadableDatabase();
        Cursor cr = db.query(TABLE_NAME, FROM, null, null, null, null, null);
        return cr;
    }

    public void insertMsg(int icon, String message) {
        SQLiteDatabase db = _msg_data.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ICON_COLUMN, icon);
        cv.put(MESSAGE_COLUMN, message);
        db.insertOrThrow(TABLE_NAME, null, cv);
    }
    
    public void upgradeDatabase() {
        SQLiteDatabase db = _msg_data.getWritableDatabase();
        _msg_data.onUpgrade(db, 1, 1);
    }
}
