package com.gabrielpoca.whereismymoney;

import static com.gabrielpoca.whereismymoney.DebtData.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WhereIsMyMoneyActivity extends ListActivity {
	// DEBUG Variables
	private String DEBUG = "DEBUG :";
	// DebtData Class
	private DebtManager debtManager;
	// Data Cursor
	private SimpleCursorAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(DEBUG, "OnCreate");
		setContentView(R.layout.list_view);
		debtManager = new DebtManager(WhereIsMyMoneyActivity.this);
		try {
			Cursor cursor = debtManager.getDebts();
			startManagingCursor(cursor);
			showDebts(cursor);
		} finally {
			debtManager.close();
		}
	}

	public void onPause() {
		super.onPause();
		Log.d(DEBUG, "OnPause!");
	}

	public void onResume() {
		super.onResume();
		Log.d(DEBUG, "OnResume!");
		Cursor cv = debtManager.getDebts();
		startManagingCursor(cv);
		adapter.changeCursor(cv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.adddebt:
			startActivity(new Intent(this, AddDebt.class));
			return true;
		case R.id.state:
			showState();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showState() {
		Log.d(DEBUG, "ShowState");
		// Create Dialog
		final Dialog dialog = new Dialog(WhereIsMyMoneyActivity.this);
		dialog.setTitle(R.string.state_dialog_title);
		dialog.setContentView(R.layout.statedialog);
		dialog.setCancelable(true);
		// Set Dialog Text
		TextView tv = (TextView) dialog.findViewById(R.id.statedialog_text);
		tv.setText("Final Balance:\n" + stateToInt() / 100);
		// Show dialog
		dialog.show();
	}

	/**
	 * Calculates the final balance of your debts
	 * 
	 * @return Float that represents the calculated value.
	 */
	private float stateToInt() {
		Cursor cr = debtManager.getDebts();
		int column_value = cr.getColumnIndex(VALUE);
		int column_type = cr.getColumnIndex(TYPE);
		float final_value = 0;
		while (cr.moveToNext()) {
			if (cr.getString(column_type).equals(IN)) {
				final_value += cr.getFloat(column_value);
			} else {
				final_value -= cr.getInt(column_value);
			}
		}
		return final_value;
	}

	/**
	 * Prints the debts from the cursor to the view.
	 */
	private void showDebts(Cursor cursor) {
		Log.d(DEBUG, "ShowDebts");
		adapter = new DebtsAdapter(WhereIsMyMoneyActivity.this, R.layout.row_view, cursor);
		Log.d(DEBUG, "Show Debts Set Adapter");
		setListAdapter(adapter);
		ListView lview = getListView();
		lview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				Log.d(DEBUG, "Long Click");
				AlertDialog.Builder builder = new AlertDialog.Builder(WhereIsMyMoneyActivity.this);
				Log.d(DEBUG, "Menu Msg");
				builder.setMessage("Are you sure you want to delete?")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								deleteDebt(position);
								Toast.makeText(WhereIsMyMoneyActivity.this, "Deleted!", Toast.LENGTH_LONG).show();
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				Log.d(DEBUG, "Show menu");
				builder.show();
				return true;
			}
		});
	}

	private void deleteDebt(int position) {
		Log.d(DEBUG, "DeleteDebt");
		ListView lview = getListView();
		Cursor cr = (Cursor) lview.getItemAtPosition(position);
		debtManager.removeDebt(cr.getInt(0));
		// set database changes in view
		Cursor cv = debtManager.getDebts();
		startManagingCursor(cv);
		adapter.changeCursor(cv);
	}

}