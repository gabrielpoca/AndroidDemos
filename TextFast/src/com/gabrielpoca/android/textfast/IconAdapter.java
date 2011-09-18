/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabrielpoca.android.textfast;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 *
 * @author gabriel
 */
public class IconAdapter extends ArrayAdapter<Integer> {
    
    private int[] _images;
    private Context _context;
    
    public IconAdapter(Context context, int layout , Integer[] images) {
		super(context, layout, images);
		_context = context;
		int img[] = new int[images.length];
		for(int i = 0; i < images.length; i++) {
			img[i] = images[i].intValue();
		}
		_images = img;
	}
    
}
