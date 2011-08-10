package com.example.sellist;

import java.util.List;

import com.example.sellist.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;


public class ContactArrayAdapter extends ArrayAdapter<Object> {

	private final int resourceId;

	@SuppressWarnings("unchecked")
	public ContactArrayAdapter(Context context, int textViewResourceId,
			List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Contact c = (Contact) getItem(position);

		// if the array item is null, nothing to display, just return null
		if (c == null) {
			return null;
		}

		// We need the layoutinflater to pick up the view from xml
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Pick up the TwoLineListItem defined in the xml file
		TwoLineListItem view;
		if (convertView == null) {
			view = (TwoLineListItem) inflater
					.inflate(resourceId, parent, false);
		} else {
			view = (TwoLineListItem) convertView;
		}

		// Set value for the first text field
		if (view.getText1() != null) {
			view.getText1().setText(c.getFirstName() + " " + c.getLastName());
		}

		// set value for the second text field
		if (view.getText2() != null) {
			view.getText2().setText("mobile: " + c.getMobile());
		}

		return view;
	}

}