package com.gabrielpoca.whereismymoney;

import static android.provider.BaseColumns._ID;
import static com.gabrielpoca.whereismymoney.DebtData.PERSON;
import static com.gabrielpoca.whereismymoney.DebtData.TABLE_NAME;
import static com.gabrielpoca.whereismymoney.DebtData.TYPE;
import static com.gabrielpoca.whereismymoney.DebtData.VALUE;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DebtManager {
	// Debug Variables
	private static final String DEBUG = "DEBT MANAGER DEBUG: ";
	// Query Variables
	private String[] FROM = { _ID, VALUE, TYPE, PERSON };
	private String ORDER_BY = _ID;
	// DebtData Variable
	private DebtData debtData;


	public DebtManager(Context context) {
		// Create DebtData
		debtData = new DebtData(context);
	}

	/**
	 * Returns a Cursor with the full database. Need to call startManagingCursor
	 * on it.
	 * 
	 * @return Cursor
	 */
	public Cursor getDebts() {
		Log.d(DEBUG, "Get Debts");
		SQLiteDatabase db = debtData.getReadableDatabase();
		Cursor cr = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
		return cr;
	}

	/**
	 * Inserts a Debt into the database.
	 * 
	 * @param person
	 *            Persons name.
	 * @param value
	 *            Debt value.
	 * @param type
	 *            Value type.
	 */
	public void addDebt(String person, float value, String type) {
		SQLiteDatabase db = debtData.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(PERSON, person);
		cv.put(VALUE, value);
		cv.put(TYPE, type);
		db.insertOrThrow(TABLE_NAME, null, cv);
	}

	/**
	 * Removes a debt from the database.
	 * 
	 * @param id
	 *            Debt _ID.
	 */
	public void removeDebt(int id) {
		SQLiteDatabase db = debtData.getWritableDatabase();
		db.delete(TABLE_NAME, _ID + "=" + id, null);
	}

	/**
	 * Closes the database.
	 */
	public void close() {
		debtData.close();
	}
}
