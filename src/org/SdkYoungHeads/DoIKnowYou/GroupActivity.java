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

public class GroupActivity extends Activity {

	protected ListView people;
	
	Group[] listItems;
	protected ArrayAdapter<Group> adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		Group g = ((Application)getApplication()).selectedGroup;
		
		TextView tv = (TextView)this.findViewById(R.id.group_name);
		tv.setText(g.getName());

		
		people = (ListView) this.findViewById(R.id.list_of_people);

		people.setAdapter(new MyPeopleAdapter(this.getBaseContext(), ((Application)getApplication()).selectedGroup));
		// TODO: seznam lidi
		// TODO: pridani cloveka
	}
	
	class MyPeopleAdapter extends ArrayAdapter<Person> {
		
		protected Context context;
		protected Group group;

		public MyPeopleAdapter(Context context, Group g) {
			super(GroupActivity.this, R.layout.group_row, g.getPeople());
			this.context = context;
			this.group = g;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.group_row, parent, false);
			
			TextView name = (TextView) rowView.findViewById(R.id.person_name);
//			ImageView groupIcon = (ImageView) rowView.findViewById(R.id.group_icon);
//			ImageView groupArrow = (ImageView) rowView.findViewById(R.id.group_arrow);
			
			Person p = group.getPeople()[position];
			name.setText(p.getName());
			
		
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
