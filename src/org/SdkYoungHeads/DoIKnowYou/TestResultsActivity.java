package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestResultsActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_results);
		
		Tester tester = ((Application)getApplication()).currentTester;
		ProgressBar pb = (ProgressBar)findViewById(R.id.testResultsProgressBar);
		pb.setMax(tester.getMaximumPoints());
		pb.setProgress(tester.getPoints());
		
		TextView title = (TextView) findViewById(R.id.text);
		title.setText(title.getText() + " " + ((Application)getApplication()).selectedGroup.getName());
		
		TextView tv = (TextView)findViewById(R.id.testResultString);
		tv.setText("You were " + tester.getPercent() + "% successful!");
		// TODO: right, wrong, who, ...

	     Button okay = (Button) findViewById(R.id.testResultsOkay);
        okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	finish();
            }
        });
	}

}
