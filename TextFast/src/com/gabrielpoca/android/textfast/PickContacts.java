package com.gabrielpoca.android.textfast;

import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PickContacts extends ListActivity {

    private final static String TAG = PickContacts.class.getSimpleName();
    public final static String MESSAGE = "message";
    private String _message;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.contact_list);

        /* Get message from extras */

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            _message = extras.getString(MESSAGE);
        }
        /* Set listview rows */

        Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,
                new String[]{Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER},
                null, null, Phone.DISPLAY_NAME);
        startManagingCursor(cursor);
        String[] columns = new String[]{Phone.DISPLAY_NAME};
        int[] to = new int[]{R.id.name_row};
        ListAdapter mAdapter = new SimpleCursorAdapter(this,
                R.layout.contact_list_row, cursor, columns, to);
        this.setListAdapter(mAdapter);
        
        /* Set bitton listners */

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ListView lv = getListView();
                SparseBooleanArray checked_positions = lv.getCheckedItemPositions();
                for (int i = 0; i < checked_positions.size(); i++) {
                    if (checked_positions.valueAt(i)) {
                        Cursor ctv = (Cursor) lv.getItemAtPosition(checked_positions.keyAt(i));
                        sendSMS(ctv.getString(ctv.getColumnIndex(Phone.NUMBER)), _message);
                    }
                }
                finish();
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                finish();
            }
        });
    }

    /**
     * Sends sms.
     * 
     * @param phoneNumber
     *            Phone number.
     * @param message
     *            Message to be sent.
     */
    private void sendSMS(String phoneNumber, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
                TextFast.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
