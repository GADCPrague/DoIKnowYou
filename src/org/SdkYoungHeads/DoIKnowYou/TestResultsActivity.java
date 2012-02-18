package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
		
		TextView tv = (TextView)findViewById(R.id.testResultString);
		tv.setText("You were " + tester.getPercent() + "% successful!");
	}

}
