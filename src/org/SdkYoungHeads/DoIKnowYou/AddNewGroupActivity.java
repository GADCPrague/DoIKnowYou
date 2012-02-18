package org.SdkYoungHeads.DoIKnowYou;

import org.SdkYoungHeads.DoIKnowYou.ListOfGroupsActivity.MyGroupAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AddNewGroupActivity extends Activity {
	
	protected ListView personList;
	SelectedPersonsAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewgroup);
		
Person[] p = ((Application)getApplication()).selectedPersons;
		
		if(p == null) {
			p = new Person[1];
			p[0] = new Person();
			p[0].setName("No record");
		}
		
		personList = (ListView) this.findViewById(R.id.list_of_groups);
		adapter = new SelectedPersonsAdapter(this.getBaseContext(), p);
		setAdapter();
		
		Button addPerson = (Button) findViewById(R.id.addPersonToGroupBtn);
        addPerson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectPersonsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setAdapter();
	}
	
	protected void setAdapter() {
		
		Person[] p = ((Application)getApplication()).selectedPersons;
		
		if(p == null) {
			p = new Person[1];
			p[0] = new Person();
			p[0].setName("No record");
		}
		Log.d("????? >>>>>>", ">>>>>>>>>>>>>>>>>>>>>>>>" + p.length);
		
		adapter.setData(p);
		
		adapter.notifyDataSetChanged();
		
	}
	
class SelectedPersonsAdapter extends ArrayAdapter<Person> {
		
		protected Context context;
		protected Person[] persons;
		

		public SelectedPersonsAdapter(Context context, Person[] persons) {
			super(AddNewGroupActivity.this, R.layout.group_row, persons);
			this.context = context;
			this.persons = persons;
		}
		
		public void setData(Person[] p) {
			this.persons = p;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.group_row, parent, false);

//			ImageView groupIcon = (ImageView) rowView.findViewById(R.id.group_icon);
//			ImageView groupArrow = (ImageView) rowView.findViewById(R.id.group_arrow);
			
			TextView personName = (TextView) rowView.findViewById(R.id.person_name);
			
			personName.setText(persons[position].getName());
			
		
			// Change the icon for Windows and iPhone
//			String s = values[position];
//			if (s.startsWith("iPhone")) {
//				imageView.setImageResource(R.drawable.no);
//			} else {
//				imageView.setImageResource(R.drawable.ok);
//			}

			return rowView;
		}
	}
}
