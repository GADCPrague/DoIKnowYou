package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.graphics.Bitmap;
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
		setContentView(R.layout.testing);
		setChoices();
	}
	
	public void setChoices() {
		Tester tester = ((Application)getApplication()).currentTester; 
		guessing = tester.getTestCase();
		if (guessing == null) {
			finish();
		} else {
			RadioGroup rg = (RadioGroup)findViewById(R.id.testingChoices);
			rg.removeAllViews();
			
			for (Person p: tester.getChoices()) {
				RadioButton rb = new RadioButton(this);
				rb.setText(p.getName());
		
				rg.addView(rb);
				rg.setOnCheckedChangeListener(this);
			}
		
			ImageView iw = (ImageView)findViewById(R.id.imageView1);
			Bitmap bmp = guessing.getSomePhoto();
			if (bmp != null) {
				iw.setImageBitmap(guessing.getSomePhoto());
			}
		}
	}

	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
		RadioButton rb = (RadioButton)paramRadioGroup.findViewById(paramInt);
		Toast.makeText(getBaseContext(), guessing.getName(), 2000).show();
		Tester t = ((Application)getApplication()).currentTester;
		t.putResult(guessing.getName() == rb.getText()); // TODO
		setChoices();
	}
}
