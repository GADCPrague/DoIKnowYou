package org.SdkYoungHeads.DoIKnowYou;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupActivity extends Activity {
	protected ListView people;
	protected Person currentlySelectedPerson;
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		Group g = ((Application)getApplication()).selectedGroup;
		
		TextView tv = (TextView)this.findViewById(R.id.group_name);
		tv.setText(g.getName());

		people = (ListView) this.findViewById(R.id.list_of_people);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refill();
	}
	
	public void refill() {
		people.clearChoices();
		people.setAdapter(new MyPeopleAdapter(this.getBaseContext(), ((Application)getApplication()).selectedGroup));
	    registerForContextMenu(people);
	}
	
	@Override  
	   public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
	super.onCreateContextMenu(menu, v, menuInfo); 
	AdapterContextMenuInfo ami = (AdapterContextMenuInfo)menuInfo;
    currentlySelectedPerson = ((Application)getApplication()).selectedGroup.getPeople()[ami.position];
	    menu.setHeaderTitle("Person actions");  
	    menu.add(0, 0, 0, "Edit");
	    menu.add(0, 1, 1, "Delete");  
	}
	
 @Override  
 public boolean onContextItemSelected(final MenuItem item) {  
     if(item.getTitle()=="Delete") {
    	 new AlertDialog.Builder(this).setMessage(R.string.really_delete_person). // TODO: format
		setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	try {
					deleteSelectedPerson();
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
	           	   dialog.dismiss();
	              }
	           }).
	        setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           }).show();
     }
     else if (item.getTitle()=="Edit"){function1(item.getItemId());} // TODO: make person editing work
     else {return false;}  
 return true;  
 }  
 
 protected void deleteSelectedPerson() throws IllegalArgumentException, IllegalStateException, IOException {
	 GroupContainer gc = ((Application)getApplication()).getDatabase();
	 ((Application)getApplication()).selectedGroup.removePerson(currentlySelectedPerson);
 	currentlySelectedPerson = null;
	gc.save(this);
 	refill();
	
}

public void function1(int id){  
     Toast.makeText(this, "function 1 called", Toast.LENGTH_SHORT).show();  
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

			//GroupActivity.this.registerForContextMenu(rowView); <-- po tomhle nefunguje single click :(
		
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
	
	public void runTest(View view) {
		Application app = ((Application)getApplication());
		
		if (app.selectedGroup.getCount() < 2) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.too_few_members).
			setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
			builder.show();
			return;
		} else {
			app.currentTester = new SimpleTester();
			app.currentTester.setGroup(app.selectedGroup);
			Intent i = new Intent(this, TestingActivity.class);
			startActivity(i);
		}
	}
	
	public void addPerson(View view) {
		Intent i = new Intent(this, AddNewPersonActivity.class);
		startActivity(i);
	}
}
