package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
        		if (gc.getGroupByName(name.getText().toString()) != null) {
        			Builder builder = new AlertDialog.Builder(AddNewGroupActivity.this);
        			builder.setMessage("A group with this name already exists. Please choose another one.").
        			setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
        	               public void onClick(DialogInterface dialog, int id) {
        	                    dialog.cancel();
        	               }
        	           }); // TODO: resource
        			builder.show();
        			return;
        		}
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
