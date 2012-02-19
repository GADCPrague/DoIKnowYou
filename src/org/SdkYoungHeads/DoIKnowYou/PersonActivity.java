package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		try {
			loadPerson();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadPerson() throws IOException {
		Person p = ((Application)getApplication()).selectedPerson;
		TextView tv = (TextView)findViewById(R.id.person_detail_name);
		tv.setText(p.getName());
		ImageView iv = (ImageView)findViewById(R.id.person_detail_image);
		iv.setImageBitmap(p.getSomePhoto(getBaseContext())); // TODO: vic fotek..
	}

}
