package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddNewGroupActivity extends Activity {
	protected Group group;
	
	public void onCreate(Bundle savedInstanceState) {
		group = new Group();
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
        
        final TextView name = (TextView)findViewById(R.id.groupNameEdit);
                
        Button addGroup = (Button) findViewById(R.id.createGroupBtn);
        addGroup.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		group.setName(name.getText().toString());
        		GroupContainer gc = ((Application)getApplication()).getDatabase();
        		gc.addGroup(group);
				try {
					gc.save(getBaseContext());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
        	}
        });
	}
}
