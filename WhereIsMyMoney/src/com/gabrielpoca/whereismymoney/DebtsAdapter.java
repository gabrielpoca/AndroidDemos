package com.gabrielpoca.whereismymoney;

import static com.gabrielpoca.whereismymoney.DebtData.PERSON;
import static com.gabrielpoca.whereismymoney.DebtData.TYPE;
import static com.gabrielpoca.whereismymoney.DebtData.VALUE;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DebtsAdapter extends SimpleCursorAdapter {
	private static String[] from = { PERSON, TYPE };
	private static int[] to = { R.id.person, R.id.type };
	private static final String DEBUG = "DEBTSADAPTER DEBUG: ";

	public DebtsAdapter(Context context, int layout, Cursor c) {
		super(context, layout, c, from, to);
	}


	@Override
	public void bindView(View v, Context context, Cursor c) {
		Log.d(DEBUG, "BIND VIEW");
		int valueCol = c.getColumnIndexOrThrow(VALUE);
		float value = c.getFloat(valueCol);
		value = value / 100;
		Log.d(DEBUG, "VALUE "+value);
		TextView value_text = (TextView) v.findViewById(R.id.value);
		Log.d(DEBUG, "SET VALUE");
		if (value_text != null) {
			value_text.setText(String.valueOf(value));
		}
		Log.d(DEBUG, "SUPER BIND VIEW");
		super.bindView(v, context, c);
	}

}
