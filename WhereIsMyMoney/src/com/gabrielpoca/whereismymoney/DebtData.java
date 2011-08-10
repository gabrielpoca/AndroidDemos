package com.gabrielpoca.whereismymoney;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DebtData extends SQLiteOpenHelper {
	// Database Variables
	private static final String DATABASE_NAME = "whereismymoney.db";
	private static final int DATABASE_VERSION = 1;
	// Table Variables
	public static final String TABLE_NAME = "debts";
	public static final String VALUE = "value";
	public static final String TYPE = "type";
	public static final String PERSON = "person";
	// Debt Type
	public final static String IN = "in";
	public final static String OUT = "out";

	public DebtData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + VALUE + " INTEGER,"
				+ TYPE + " TEXT NOT NULL," + PERSON + " TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
