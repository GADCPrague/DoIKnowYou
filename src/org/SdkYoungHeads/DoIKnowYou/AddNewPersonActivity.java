package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.SdkYoungHeads.DoIKnowYou.ListOfGroupsActivity.MyGroupAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddNewPersonActivity extends Activity {

	protected ListView groups;

	private Group[] listItems;
	protected ArrayAdapter<Group> adapter;
	private TextView textTargetUri;
	private List<String> imageArray;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewperson);
		groups = (ListView) this.findViewById(R.id.list_of_images);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.imageArray = new ArrayList<String>();
		
		
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

		// @ToDo nastavit defaultní selekt
		// s.setSelection(2);
		// Group selectedGroup = ((Application) getApplication()).selectedGroup;

		/* start tlaèítka pro výbìr fotky z galerie */
		Button buttonLoadImage = (Button) findViewById(R.id.loadimage);
		textTargetUri = (TextView) findViewById(R.id.targeturi);
		buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 0);
			}
		});
		/* konec tlaèítka pro výbìr fotky z galerie */

		setGroupSelection();
		
		
		
		groups.clearChoices();
		groups.setAdapter(new MyGroupAdapter(this.getBaseContext()),this.imageArray);

		//groups.add("ds");
	}

	/*
	 * pøidá url do seznamu
	 */
	public void add(String uri){
		imageArray.add(uri);
	}
	
	/*
	 * metoda volana pridavacim tlacitkem pridava novou URI do listu obrazku
	 */
	private void addImageToGrid(Uri uri) {
		// ToDo tady se namísto vložení do pole vloží do photoGrid
		EditText nameField = (EditText) findViewById(R.id.editTextName);
		nameField.setText(uri.toString());
	}

	/*
	 * metoda nastavuje defaultni hodnotu spinneru
	 */
	protected void setGroupSelection() {
		Spinner s = (Spinner) findViewById(R.id.groupSpinner);
		Group selectedGroup = ((Application) getApplication()).selectedGroup;
		GroupContainer groupContainer = ((Application) getApplication())
				.getDatabase();
		if (selectedGroup != null) {
			int position = 0;
			for (Group g : groupContainer.getGroups()) {
				if (g == selectedGroup) {
					s.setSelection(position++);
					break;
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();
			addImageToGrid(targetUri);
		}
	}

	/*
	 * handler formulare pro pridani osoby, volan po kliku na tlacitko Pridat
	 * osobu. Provadi pridani osoby do skupiny.
	 * 
	 * @param View button
	 * 
	 * @return void
	 */
	public void addPerson(View button) throws IllegalArgumentException,
			IllegalStateException, IOException {
		final EditText nameField = (EditText) findViewById(R.id.editTextName);
		String personName = nameField.getText().toString();

		final Spinner groupSpinner = (Spinner) findViewById(R.id.groupSpinner);
		String groupName = groupSpinner.getSelectedItem().toString();

		Person person = new Person();
		person.setName(personName);

		GroupContainer groupContainer;
		groupContainer = ((Application) getApplication()).getDatabase();

		groupContainer.getGroupByName(groupName).addPerson(person);
		groupContainer.save(this);

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
						// Action for ï¿½NOï¿½ Button
						finish();
						dialog.cancel();
					}

				})

				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// Action for YES Button
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

	// http://xjaphx.wordpress.com/2011/06/12/custom-grid-view-of-applications/
	// http://android-er.blogspot.com/2011/02/select-image-using-android-build-in.html

class MyGroupAdapter extends ArrayAdapter<Group> {
		
		protected Context context;
		

		public MyGroupAdapter(Context context,List<String> imageArray) {
			super(AddNewPersonActivity.this, R.layout.addnewperson_row, imageArray);
			this.context = context;
		}
		

		
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.addnewperson_row, parent, false);
			TextView groupName = (TextView) rowView.findViewById(R.id.group_name);
			TextView groupCount = (TextView) rowView.findViewById(R.id.group_count);
			//ImageView groupIcon = (ImageView) rowView.findViewById(R.id.group_icon);

			groupName.setText("text");
			groupCount.setText("text");
			/*
			Bitmap b = g.getIcon();
			if (b != null) {
				groupIcon.setImageBitmap(b);
			}
			*/
			return rowView;
		}
	}
}
