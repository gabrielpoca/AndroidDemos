/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielpoca.android.textfast;

import android.database.Cursor;
import java.util.HashMap;
import android.database.Cursor;

/**
 *
 * @author gabriel
 */
public class Icons {

    private HashMap<Integer, Integer> _icon_map;

    public Icons() {
        _icon_map = new HashMap<Integer, Integer>();
        _icon_map.put(0, R.drawable.cafe);
        _icon_map.put(1, R.drawable.comer);
        _icon_map.put(2, R.drawable.almocar);
        _icon_map.put(3, R.drawable.lanchar);
        _icon_map.put(4, R.drawable.jantar);
    }

    public int getIcon(int id) {
        return _icon_map.get(id);
    }
    
    public String[] getIcons() {
        String[] s = new String[_icon_map.size()];
        for(int i = 0; i < _icon_map.size(); i++) {
            s[i] = _icon_map.get(i).toString();
        }
        return s;
    }
}
