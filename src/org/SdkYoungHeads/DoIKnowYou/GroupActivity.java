package org.SdkYoungHeads.DoIKnowYou;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GroupActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			throw new RuntimeException();
		}
		UUID uuid = UUID.fromString(extras.getString("group_uuid"));
		GroupContainer gc = ((Application)getApplication()).getDatabase();
		Group g = gc.findGroup(uuid);
		
		TextView tv = (TextView)this.findViewById(R.id.group_name);
		tv.setText(g.getName());
		
		// TODO: seznam lidi
		// TODO: pridani cloveka
	}
}
