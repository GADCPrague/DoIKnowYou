package org.SdkYoungHeads.DoIKnowYou;

import java.util.Timer;
import java.util.TimerTask;

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
	
	protected void finishTesting() {

		finish();
		Intent i = new Intent(this, TestResultsActivity.class);
		startActivity(i);
	}
	
	public void setChoices() {
		Tester tester = ((Application)getApplication()).currentTester; 
		guessing = tester.getTestCase();
		if (guessing == null) {
			finishTesting();
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
	
	protected boolean submitted;
	
	protected void check() {
		
		Button b = (Button)findViewById(R.id.testingSubmit);
		if (submitted) {
			TestingActivity.this.setChoices();
			submitted = false;
			b.setText(R.string.submit);
			return;
		}
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
			selected.setBackgroundColor(R.color.wrong);
		} else {
			selected.setBackgroundColor(R.color.right);
		}
		selected.invalidate();
		
		Tester t = ((Application)getApplication()).currentTester;
		t.putResult(ok);
		//RadioGroup rg = (RadioGroup)findViewById(R.id.testingChoices);
		//rg.setEnabled(false);
		
		if (t.getTestCase() == null) finishTesting();
		else {
		b.setText(R.string.test_next_question);
		
		submitted = true;
		}
	}
}
