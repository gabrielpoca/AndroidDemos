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
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WhereIsMyMoneyActivity extends ListActivity {
	// DEBUG Variables
	private String DEBUG = "DEBUG :";
	// DebtData Class
	private DebtManager debtManager;
	// Data Cursor
	private DebtsAdapter adapter;
	// Menu Items
	private static final int ADD_DEBT = 0;
	private static final int STATE = 1;
	private static final int BATCH_MODE = 2;
	private static final int BATCH_MODE_DELETE = 3;

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
		menu.add(0, ADD_DEBT, 0, R.string.adddebt_label);
		menu.add(0, STATE, 0, R.string.state);
		menu.add(0, BATCH_MODE, 0, R.string.batchmode);
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(DEBUG, "SELECTED" + String.valueOf(item.getItemId()));
		// Handle item selection
		switch (item.getItemId()) {
		case ADD_DEBT:
			startActivity(new Intent(this, AddDebt.class));
			return true;
		case STATE:
			showState();
			return true;
		case BATCH_MODE:
			toggleBatchMode();
			return true;
		case BATCH_MODE_DELETE:
			batchModeDelete();
			toggleBatchMode();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Adds or removes the batch mode delete menu entry.
	 */
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (adapter.isBatchMode()) {
			if (menu.findItem(BATCH_MODE_DELETE) == null)
				menu.add(0, BATCH_MODE_DELETE, 0, R.string.batchmode_delete);
		} else {
			if (menu.findItem(BATCH_MODE_DELETE) != null)
				menu.removeItem(BATCH_MODE_DELETE);
		}
		return true;
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
		adapter = new DebtsAdapter(WhereIsMyMoneyActivity.this,
				R.layout.row_view, cursor);
		Log.d(DEBUG, "Show Debts Set Adapter");
		setListAdapter(adapter);
		ListView lview = getListView();
		lview.setChoiceMode(ListView.CHOICE_MODE_NONE);
		lview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Log.d(DEBUG, "Long Click");
				AlertDialog.Builder builder = new AlertDialog.Builder(
						WhereIsMyMoneyActivity.this);
				Log.d(DEBUG, "Menu Msg");
				builder.setMessage("Are you sure you want to delete?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										deleteDebt(position);
										Toast.makeText(
												WhereIsMyMoneyActivity.this,
												"Deleted!", Toast.LENGTH_LONG)
												.show();
									}
								}).setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				Log.d(DEBUG, "Show menu");
				builder.show();
				return true;
			}
		});
	}

	/**
	 * Turns BatchMode on or off.
	 */
	private void toggleBatchMode() {
		adapter.toggleBatchMode();
		boolean bm = adapter.isBatchMode();
		ListView lview = getListView();
		if (bm) {
			lview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		} else {
			lview.setChoiceMode(ListView.CHOICE_MODE_NONE);
		}
	}

	/**
	 * Removes a debt based on its position in the list view. Restores the view
	 * after the operation. Not used in batch mode.
	 * 
	 * @param position
	 *            Debt position.
	 */
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

	/**
	 * Removes a debt based on its position on the list view. Used in batch
	 * mode.
	 * 
	 * @param position
	 *            Debt position.
	 */
	private void batchModeDeleteDebt(int position) {
		Log.d(DEBUG, "DeleteDebt");
		ListView lview = getListView();
		Cursor cr = (Cursor) lview.getItemAtPosition(position);
		debtManager.removeDebt(cr.getInt(0));
	}

	/**
	 * Calls batchModeDeleteDebt for each selected debt.
	 */
	private void batchModeDelete() {
		ListView lview = getListView();
		SparseBooleanArray checkedItems = lview.getCheckedItemPositions();
		if (checkedItems != null) {
			for (int i = 0; i < checkedItems.size(); i++) {
				Log.d(DEBUG, "CHECKED ITEM " + checkedItems.valueAt(i));
				if (checkedItems.valueAt(i)) {
					batchModeDeleteDebt(checkedItems.keyAt(i));
				}
			}
			Cursor cv = debtManager.getDebts();
			startManagingCursor(cv);
			adapter.changeCursor(cv);
		}
	}

}