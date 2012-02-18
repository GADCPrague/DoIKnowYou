package org.SdkYoungHeads.DoIKnowYou;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GroupActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		Group g = ((Application)getApplication()).selectedGroup;
		
		TextView tv = (TextView)this.findViewById(R.id.group_name);
		tv.setText(g.getName());
		
		// TODO: seznam lidi
		// TODO: pridani cloveka
	}
}
