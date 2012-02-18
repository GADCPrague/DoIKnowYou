package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfGroupsActivity extends Activity {

	protected ListView groups;
	
	ArrayList<String> listItems=new ArrayList<String>();
	protected ArrayAdapter<String> adapter;
	
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listofgroups);
		
		groups = (ListView) this.findViewById(R.id.list_of_groups);
		
		adapter=new ArrayAdapter<String>(this,
			    android.R.layout.simple_list_item_1,
			    listItems);
		
		groups.setAdapter(adapter);
		
		listItems.add("ALL");
		listItems.add("WORK");
		listItems.add("SCHOOL");
		
	}
	
	
}
