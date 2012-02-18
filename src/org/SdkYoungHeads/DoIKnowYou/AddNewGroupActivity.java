package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddNewGroupActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewgroup);
		
		Button addPerson = (Button) findViewById(R.id.addPersonToGroupBtn);
        addPerson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectPersonsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
	}
}
