package com.gabrielpoca.whereismymoney;

import static com.gabrielpoca.whereismymoney.DebtData.PERSON;
import static com.gabrielpoca.whereismymoney.DebtData.TYPE;
import static com.gabrielpoca.whereismymoney.DebtData.VALUE;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DebtsAdapter extends SimpleCursorAdapter {
	private static String[] from = { PERSON, TYPE };
	private static int[] to = { R.id.person, R.id.type };
//	private static final String DEBUG = "DEBTSADAPTER DEBUG: ";

	// State Variables
	private int batch_mode;

	public DebtsAdapter(Context context, int layout, Cursor c) {
		super(context, layout, c, from, to);
		batch_mode = 0;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		// Set Row Value
		int valueCol = c.getColumnIndexOrThrow(VALUE);
		float value = c.getFloat(valueCol);
		value = value / 100;
		TextView value_text = (TextView) v.findViewById(R.id.value);
		if (value_text != null) {
			value_text.setText(String.valueOf(value));
		}
		// Set Row CheckedTextView
		CheckedTextView cb = (CheckedTextView) v.findViewById(R.id.checkedtextview);
		if (batch_mode == 0) {
			cb.setVisibility(View.GONE);
		} else
			cb.setVisibility(View.VISIBLE);
		super.bindView(v, context, c);
	}

	public void toggleBatchMode() {
		if (batch_mode == 0) {
			batch_mode = 1;
		} else
			batch_mode = 0;
		this.notifyDataSetChanged();
	}
	
	public boolean isBatchMode() {
		if(batch_mode == 1)
			return true;
		else return false;
	}

}
