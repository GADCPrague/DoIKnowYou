package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListOfGroupsActivity extends Activity {

	protected ListView groups;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listofgroups);
		
		groups = (ListView) this.findViewById(R.id.list_of_groups);
		
	}
}
