package com.gabrielpoca.android.textfast;

import com.gabrielpoca.android.textfast.database.MsgManager;
import static com.gabrielpoca.android.textfast.PickContacts.MESSAGE;


import static com.gabrielpoca.android.textfast.database.MsgData.MESSAGE_COLUMN;
import static com.gabrielpoca.android.textfast.database.MsgData.ICON_COLUMN;
import java.util.HashMap;
import java.util.Iterator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TextFast extends Activity {

    private final static String TAG = TextFast.class.getSimpleName();
    private MsgManager _msg_manager;
    private HashMap<Integer, String> _msg_map;
    private Icons _icon_map;
    private int _selected_icon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        /* Initiate local variables */
        _msg_manager = new MsgManager(this);
//        feedDatabase();
        /* Maps each icon to an integer */
        _icon_map = new Icons();
        /* Reads and stores <Msg - Icon> map from the database */
        initMsgMap();


        /* Insert Table Rows */
        TableLayout table = (TableLayout) findViewById(R.id.main_table);

        Iterator<Integer> it = _msg_map.keySet().iterator();
        while (it.hasNext()) {
            LayoutInflater inflater = getLayoutInflater();
            TableRow row = (TableRow) inflater.inflate(R.layout.table_row, null);

            ImageView img1 = (ImageView) row.findViewById(R.id.img1);
            ImageView img2 = (ImageView) row.findViewById(R.id.img2);
            /* First row icon */
            int icon = (Integer) it.next();
            img1.setImageResource(icon);
            img1.setTag(icon);

            if (it.hasNext()) {
                /* Second row icon */
                int icon2 = (Integer) it.next();
                img2.setImageResource(icon2);
                img2.setTag(icon2);
            }

            table.addView(row);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.textfast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_msg:                
                Log.d(TAG, "NEW_MSG");
                Intent it = new Intent(this, InsertMsg.class);
                startActivity(it);
                return true;                
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Initiates _msg_map.
     */
    private void initMsgMap() {
        _msg_map = new HashMap<Integer, String>();
        Cursor cr = _msg_manager.getMsgs();
        while (cr.moveToNext()) {
            _msg_map.put(_icon_map.getIcon(cr.getInt(cr.getColumnIndex(ICON_COLUMN))), cr.getString(cr.getColumnIndex(MESSAGE_COLUMN)));
        }
    }

    /**
     * Handles imageview onclick.
     * 
     * @param v
     */
    public void onClick(View v) {
        ImageView img = ((ImageView) v);
        if (img.getTag() != null) {
            _selected_icon = (Integer) img.getTag();

            /* Vibrate */
            img.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);

            Intent it = new Intent(this, PickContacts.class);
            it.putExtra(MESSAGE, _msg_map.get(_selected_icon));
            startActivity(it);
        }
    }

    private void feedDatabase() {
        _msg_manager.upgradeDatabase();
        _msg_manager.insertMsg(0, "Café?");
        _msg_manager.insertMsg(3, "Lanchar?");
        _msg_manager.insertMsg(2, "Almoçar?");
    }
}