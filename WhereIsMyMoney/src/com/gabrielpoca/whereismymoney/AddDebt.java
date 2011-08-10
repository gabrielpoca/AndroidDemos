package com.gabrielpoca.whereismymoney;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import static com.gabrielpoca.whereismymoney.DebtData.IN;
import static com.gabrielpoca.whereismymoney.DebtData.OUT;

public class AddDebt extends Activity {
	private final static int PICK_CONTACT = 1;
	// Debug Variables
	private final static String DEBUG = "AddDbet DEBUG: ";
	// DebtManager Variable
	private DebtManager debtManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(DEBUG, "Creating Main View");
		setContentView(R.layout.add_debt);
		Button button = (Button) findViewById(R.id.submit);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				debtManager = new DebtManager(AddDebt.this);
				EditText person = (EditText) findViewById(R.id.person);
				EditText value = (EditText) findViewById(R.id.value);
				String type = null;
				RadioButton in_radio = (RadioButton) findViewById(R.id.in_radio);
				if (in_radio.isChecked())
					type = IN;
				else
					type = OUT;
				int int_value = (int) (Float.parseFloat(value.getText().toString())*100);
				debtManager.addDebt(person.getText().toString(), int_value, type);
				debtManager.close();
				finish();
			}
		});
		Button search_button = (Button) findViewById(R.id.searchcontacts);
		search_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});
	}
	
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		switch (reqCode) {
		case PICK_CONTACT:
			Log.d(DEBUG, "RESULT PICK_CONTACT");
			if(resultCode == Activity.RESULT_OK) {
				Log.d(DEBUG, "RESULT OK");
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if(c.moveToFirst()) {
					String name = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME));
					EditText et = (EditText) findViewById(R.id.person);
					et.setText(name);
				}
			}
			break;
		default:
			Log.d(DEBUG, "RESULT DEFAULT");
			break;
		}
	}


}
