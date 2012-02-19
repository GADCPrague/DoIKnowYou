/*
 * package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SelectPersonsActivity extends Activity {

	protected ListView persons;
	protected MyPersonAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectpersons);
		
		persons = (ListView) this.findViewById(R.id.listOfPersonsToSelect);
		
		Person[] p = new Person[2];
		p[0] = new Person();
		p[0].setName("Person 1");
		p[1] = new Person();
		p[1].setName("Person 2");
		
		adapter = new MyPersonAdapter(this.getBaseContext(), p);
		persons.setAdapter(adapter);
		
		Button submit = (Button) findViewById(R.id.saveSelectedBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	ArrayList<Person> list = new ArrayList<Person>();
            	Log.d("for1", "" + adapter.checked.length);
            	for(int i = 0; i < adapter.checked.length; i++) {
            		Log.d("for1", "" + i + " / " + adapter.checked.length);
                	if(adapter.checked[i] == true) {
                		list.add(adapter.persons[i]);
                	}
                }
            	Log.d("for1", "List Size = " + adapter.checked.length);
            	Log.d("for1", "List Size = " + list.size());
            	Person[] p = new Person[list.size()];
            	for(int i = 0; i < list.size(); i++) {
            		Log.d("for2", "aaa");
            		p[i] = list.get(i);
            	}
//            	((Application)getApplication()).selectedPersons = p;
            	Log.d(">>>>>>>", "" + p.length);
            	finish();
            }

        });
	}
	
class MyPersonAdapter extends ArrayAdapter<Person> {
		
		protected Context context;
		public Person[] persons;
		public boolean[] checked;
		

		public MyPersonAdapter(Context context, Person[] persons) {
			super(SelectPersonsActivity.this, R.layout.selectpersons_row, persons);
			this.context = context;
			this.persons = persons;
			this.checked = new boolean[persons.length];
		}
		
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.selectpersons_row, parent, false);
			
//			ImageView personIcon = (ImageView) rowView.findViewById(R.id.group_icon);
			TextView personName = (TextView) rowView.findViewById(R.id.person_name);
			CheckBox check = (CheckBox) rowView.findViewById(R.id.check);
			
			check.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox c = (CheckBox) v;
					checked[position] = c.isChecked();
					Log.d("", "" + c.isChecked() + " " + position);
				}
			});
			personName.setText(persons[position].getName());

			return rowView;
		}
	}
}
*/
