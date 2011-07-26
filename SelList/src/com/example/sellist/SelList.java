package com.example.sellist;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class SelList extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ContactArrayAdapter(this, R.layout.main,
				getContacts()));
	}

	private List<Contact> getContacts() {

		List<Contact> contacts = new ArrayList<Contact>();

		Contact c;

		c = new Contact();
		c.setFirstName("Shriram");
		c.setLastName("Shrikumar");
		c.setMobile("07777777777");

		contacts.add(c);

		c = new Contact();
		c.setFirstName("John");
		c.setLastName("Doe");
		c.setMobile("MOBILE.NUMBER");

		contacts.add(c);

		return contacts;

	}
}