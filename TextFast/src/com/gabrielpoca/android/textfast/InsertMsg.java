/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielpoca.android.textfast;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import java.util.Vector;

/**
 *
 * @author gabriel
 */
public class InsertMsg extends ListActivity {

    private static final String TAG = InsertMsg.class.getSimpleName();
    
    private Icons _icon_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_msg);
        _icon_map = new Icons();
        setListAdapter(new ArrayAdapter(this, R.layout.insert_msg_row, _icon_map.getIcons()));
    }
}
