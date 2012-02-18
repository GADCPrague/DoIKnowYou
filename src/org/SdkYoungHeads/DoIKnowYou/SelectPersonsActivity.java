package org.SdkYoungHeads.DoIKnowYou;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectPersonsActivity extends Activity {

	protected ListView persons;
	
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
		
		persons.setAdapter(new MyPersonAdapter(this.getBaseContext(), p));
	}
	
class MyPersonAdapter extends ArrayAdapter<Person> {
		
		protected Context context;
		protected Person[] persons;
		

		public MyPersonAdapter(Context context, Person[] persons) {
			super(SelectPersonsActivity.this, R.layout.selectpersons_row, persons);
			this.context = context;
			this.persons = persons;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.selectpersons_row, parent, false);
			
//			ImageView personIcon = (ImageView) rowView.findViewById(R.id.group_icon);
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
