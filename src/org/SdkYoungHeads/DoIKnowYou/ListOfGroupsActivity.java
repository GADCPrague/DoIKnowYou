package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class ListOfGroupsActivity extends Activity {


	protected ListView groups;
	
	Group[] listItems;
	protected ArrayAdapter<String> adapter;
		
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listofgroups);
		
		GroupContainer gc = ((Application)getApplication()).getDatabase();
		
		groups = (ListView) this.findViewById(R.id.list_of_groups);
			
//		listItems = new String[3];
//		listItems[0] = "ALL";
//		listItems[1] = "WOR";
//		listItems[2] = "SCH";
		
//		groups.setAdapter(new MyGroupAdapter(this.getBaseContext(), listItems));
		
		//GroupContainer gc = ((Application)getApplication()).getDatabasePath(name)
	}
	
	class MyGroupAdapter extends ArrayAdapter<String> {
		
		protected Context context;
		protected String[] names;
		

		public MyGroupAdapter(Context context, String[] names) {
			super(ListOfGroupsActivity.this, R.layout.listofgroups_row, names);
			this.context = context;
			this.names = names;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listofgroups_row, parent, false);
			
			TextView groupName = (TextView) rowView.findViewById(R.id.group_name);
			TextView groupDescription = (TextView) rowView.findViewById(R.id.group_description);
			TextView groupCount = (TextView) rowView.findViewById(R.id.group_count);
//			ImageView groupIcon = (ImageView) rowView.findViewById(R.id.group_icon);
//			ImageView groupArrow = (ImageView) rowView.findViewById(R.id.group_arrow);
			
			groupName.setText(names[position]);
			groupDescription.setText("[ Description ]");
			groupCount.setText("[ Count ]");
			
		
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
