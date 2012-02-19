package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
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
import android.view.ViewGroup.LayoutParams;
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
		try {
			setChoices();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Button btn = (Button)findViewById(R.id.testingSubmit);
		btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	try {
					check();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		});
	}
	
	protected void finishTesting() {

		finish();
		Intent i = new Intent(this, TestResultsActivity.class);
		startActivity(i);
	}
	
	public void setChoices() throws IOException {
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
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				rb.setLayoutParams(params);
						
				rg.addView(rb);
				rg.setOnCheckedChangeListener(this);
			}
		
			ImageView iw = (ImageView)findViewById(R.id.imageView1);
			Bitmap bmp = guessing.getSomePhoto(getBaseContext());
			if (bmp != null) {
				iw.setImageBitmap(guessing.getSomePhoto(getBaseContext()));
			}
		}
	}

	protected RadioButton selected;
	public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
		selected =  (RadioButton)paramRadioGroup.findViewById(paramInt);
	}
	
	protected boolean submitted;
	
	protected void enableAll() {
		RadioGroup rg = (RadioGroup)findViewById(R.id.testingChoices);
		for (int i=0; i<rg.getChildCount(); i++) {
			RadioButton rb = (RadioButton)rg.getChildAt(i);
			rb.setEnabled(true);
		}
		
	}
	
	protected void disableAll() {
		RadioGroup rg = (RadioGroup)findViewById(R.id.testingChoices);
		for (int i=0; i<rg.getChildCount(); i++) {
			RadioButton rb = (RadioButton)rg.getChildAt(i);
			rb.setEnabled(false);
		}
	}
	
	protected void check() throws IOException {
		
		Button b = (Button)findViewById(R.id.testingSubmit);
		if (submitted) {
			TestingActivity.this.setChoices();
			submitted = false;
			b.setText(R.string.submit);
			
			enableAll();
			
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
			selected.setBackgroundResource(R.color.wrong);
		} else {
			selected.setBackgroundResource(R.color.right);
		}
		b.setText(R.string.submit);
		
		disableAll();
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
