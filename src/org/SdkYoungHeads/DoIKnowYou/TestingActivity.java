package org.SdkYoungHeads.DoIKnowYou;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class TestingActivity extends Activity implements OnCheckedChangeListener {
	private Person guessing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			throw new RuntimeException();
		}
		UUID uuid = UUID.fromString(extras.getString("group_uuid"));
		GroupContainer gc = ((Application)getApplication()).getDatabase();
		Group g = gc.findGroup(uuid);
		guessing = g.getPeople()[0]; // TODO: tohle by se melo vyplnit pri hadani cloveka...
		
		RadioButton rb = new RadioButton(this);
		rb.setText(guessing.getName());
		
		RadioGroup rg = (RadioGroup)(this.findViewById(R.id.radioGroup1));
		rg.addView(rb);
		rg.setOnCheckedChangeListener(this);
		
		ImageView iw = (ImageView)findViewById(R.id.imageView1);
		iw.setImageBitmap(guessing.getSomePhoto());
		
		// TODO: add choices...
		// TODO: accept group to test...
	}

	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
		RadioButton rb = (RadioButton)paramRadioGroup.findViewById(paramInt);
		Toast.makeText(getBaseContext(), rb.getText(), 2000).show();
	}
}
