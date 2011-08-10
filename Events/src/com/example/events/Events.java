package com.example.events;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import static com.example.events.Constants.*;

public class Events extends Activity {
	private EventsData events;
	private static String[] FROM = { _ID, TIME, TITLE, };
	private static String ORDER_BY = TIME + " DESC";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        events = new EventsData(this);
        try {
        	addEvent("Hello, Android!");
        	Cursor cursor = getEvents();
        	showEvents(cursor);
        } finally {
        	events.close();
        }
    }
    
    private void showEvents(Cursor cursor) {
    	StringBuilder builder = new StringBuilder("Saved events:\n");
    	while(cursor.moveToNext()) {
    		long id = cursor.getLong(0);
    		long time = cursor.getLong(1);
    		String title = cursor.getString(2);
    		builder.append(id).append(": ");
    		builder.append(time).append(": ");
    		builder.append(title).append("\n");
    	}
    	TextView tv = (TextView) findViewById(R.id.text);
    	tv.setText(builder.toString());
    }
    
    private Cursor getEvents() {
    	SQLiteDatabase db = events.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
    	startManagingCursor(cursor);
    	return cursor;
    }
    
    private void addEvent(String string) {
    	SQLiteDatabase db = events.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TIME, System.currentTimeMillis());
    	values.put(TITLE, string);
    	db.insertOrThrow(TABLE_NAME, null, values);
    }
}