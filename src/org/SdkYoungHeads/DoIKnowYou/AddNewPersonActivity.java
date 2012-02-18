package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddNewPersonActivity extends Activity {

	ArrayList<String> listItems = new ArrayList<String>();
	protected ArrayAdapter<String> adapter;
	private GroupContainer groupContainer;
	
	TextView textTargetUri;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewperson);

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		GroupContainer groupContainer;
		groupContainer = ((Application) getApplication()).getDatabase();
		for (Group group : groupContainer.getGroups()) {
			adapter.add(group.getName());
		}
		
		Spinner s = (Spinner) findViewById(R.id.groupSpinner);
		s.setAdapter(adapter);
		//@ToDo nastavit defaultní selekt 
		//s.setSelection(2);
		//Group selectedGroup = ((Application) getApplication()).selectedGroup;
		
		
		Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
		 textTargetUri = (TextView)findViewById(R.id.targeturi);
	      buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

	  @Override
	  public void onClick(View arg0) {
	   // TODO Auto-generated method stub
	   Intent intent = new Intent(Intent.ACTION_PICK,
	     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	   startActivityForResult(intent, 0);
	  }});
		
	}
	

	  public void getPhoto() {
		   // TODO Auto-generated method stub
		   Intent intent = new Intent(Intent.ACTION_PICK,
		     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		   startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 // TODO Auto-generated method stub
	 super.onActivityResult(requestCode, resultCode, data);

	 if (resultCode == RESULT_OK){
	  Uri targetUri = data.getData();
	  EditText nameField = (EditText) findViewById(R.id.editTextName);
	  nameField.setText(targetUri.toString());
	 }
	}

	/*
	 * handler formulare pro pridani osoby, volan po kliku na tlacitko Pridat osobu. Provadi pridani osoby do skupiny.
	 * 
	 * @param View button
	 * @return void
	 */
	public void addPerson(View button) {
		final EditText nameField = (EditText) findViewById(R.id.editTextName);
		String personName = nameField.getText().toString();

		final Spinner groupSpinner = (Spinner) findViewById(R.id.groupSpinner);
		String groupName = groupSpinner.getSelectedItem().toString();
		
		Person person = new Person();
		person.setName(personName);
		
		GroupContainer groupContainer;
		groupContainer = ((Application) getApplication()).getDatabase();
		
		groupContainer.getGroupByName(groupName).addPerson(person);

		this.createDialog();
	
		
	}

	/*
	 * zobrazi dialog po uspesnem pridani osoby
	 * 
	 * @return void
	 */
	protected void createDialog() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("New person has been added. Add another one?")
				.setCancelable(false)
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for ‘NO’ Button
						finish();
						dialog.cancel();
					}

				})
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for ‘YES’ Button
						Intent intent = getIntent();
						finish();
						startActivity(intent);
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("Person added");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
	}

}
