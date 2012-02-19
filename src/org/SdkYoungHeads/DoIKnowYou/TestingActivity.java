package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
		
		Button btn = (Button)findViewById(R.id.testingSubmit);
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	check();
            }
		});
	}
	
	public void setChoices() {
		Tester tester = ((Application)getApplication()).currentTester; 
		guessing = tester.getTestCase();
		if (guessing == null) {
			finish();
			Intent i = new Intent(this, TestResultsActivity.class);
			startActivity(i);
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

	protected RadioButton selected;
	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
		selected =  (RadioButton)paramRadioGroup.findViewById(paramInt);
	}
	
	protected void check() {
		if (selected == null) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.make_your_guess).
			setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
			builder.show();
			return;
		}
		Boolean ok = guessing.getName() == selected.getText();
		if (!ok) {
			Toast.makeText(getBaseContext(), guessing.getName(), 1000).show(); // TODO: better notification
		}
		Tester t = ((Application)getApplication()).currentTester;
		t.putResult(ok);
		setChoices();
	}
}
