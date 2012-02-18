package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class AddNewPersonActivity extends Activity {


	ArrayList<String> listItems = new ArrayList<String>();
	protected ArrayAdapter<String> adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewperson);

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		GroupContainer groupContainer;
		groupContainer = ((Application)getApplication()).getDatabase();
		for (Group group : groupContainer.getGroups()) {
			adapter.add(group.getName());
		}

		Spinner s = (Spinner) findViewById(R.id.spinner);
		s.setAdapter(adapter);

		/*
		 * Spinner s = (Spinner) findViewById(R.id.spinner); ArrayAdapter
		 * adapter = ArrayAdapter.createFromResource( this, R.array.planets,
		 * android.R.layout.simple_spinner_item);
		 * adapter.setDropDownViewResource
		 * (android.R.layout.simple_spinner_dropdown_item);
		 * adapter.add("some string data"); adapter.add("and anothe");
		 * 
		 * s.setAdapter(adapter);
		 */

	}

}
