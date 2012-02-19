package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddNewPersonActivity extends Activity {

	protected GridView groups;

	private Group[] listItems;
	protected ArrayAdapter<Group> adapter;
	private TextView textTargetUri;
	private List<Uri> imageArray;
	private MyGroupAdapter myAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewperson);
		groups = (GridView) this.findViewById(R.id.list_of_images);

		List<Uri> imageArray = new ArrayList<Uri>();

		myAdapter = new MyGroupAdapter(this.getBaseContext(), imageArray);

		// groups.clearChoices();
	}

	@Override
	public void onResume() {
		super.onResume();

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

		// @ToDo nastavit defaultni select
		// s.setSelection(2);
		// Group selectedGroup = ((Application) getApplication()).selectedGroup;

		/* start tlacitka pro vyber fotky z galerie */
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
		/* konec tlacitka pro vyber fotky z galerie */

		setGroupSelection();

		groups.setAdapter(myAdapter);
	}

	/*
	 * metoda pøidá URI do adaptéru
	 */
	public void addToAdapter(Uri uri) {
		myAdapter.add(uri);
		myAdapter.notifyDataSetChanged();
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
			addToAdapter(targetUri);
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
		myAdapter.getCount();
		
		List<String> pathArray = new ArrayList<String>();
		for(int i=0;i<myAdapter.getCount();i++){
			pathArray.add(myAdapter.getItem(i).toString());
			//Log.d("",myAdapter.getItem(i).toString());
		}
		person.setPhotoPaths(this.getBaseContext(),pathArray);

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

	public void myClickHandler(View v) {
		Log.d("", " Práve kliknuto");
		Log.d("View", v.toString());
		// myAdapter.remove();
	}

	// http://xjaphx.wordpress.com/2011/06/12/custom-grid-view-of-applications/
	// http://android-er.blogspot.com/2011/02/select-image-using-android-build-in.html

	class MyGroupAdapter extends ArrayAdapter<Uri> {

		protected Context context;

		// private List<Uri> imageArray;

		public MyGroupAdapter(Context context, List<Uri> imageArray) {
			super(AddNewPersonActivity.this, R.layout.addnewperson_row,
					imageArray);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.addnewperson_row, parent,
					false);
			ImageView groupIcon = (ImageView) rowView
					.findViewById(R.id.photo_miniature);
			groupIcon.setImageURI(super.getItem(position));

			return rowView;
		}
	}

}
