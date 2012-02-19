package org.SdkYoungHeads.DoIKnowYou;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class AddNewPersonActivity extends Activity {

	private static final int TAKE_PICTURE = 0;

	protected GridView groups;

	private Group[] listItems;
	protected ArrayAdapter<Group> adapter;
	private TextView textTargetUri;
	private List<Uri> imageArray;
	private MyGroupAdapter myAdapter;
	private Uri imageUri;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewperson);
		groups = (GridView) this.findViewById(R.id.list_of_images);

		List<Uri> imageArray = new ArrayList<Uri>();

		myAdapter = new MyGroupAdapter(this.getBaseContext(), imageArray);

		// groups.clearChoices();
		Button loadImageBtn = null;
		loadImageBtn = (Button) findViewById(R.id.loadimage);
		registerForContextMenu(loadImageBtn);
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
		// Group selectedGroup = ((Application) getApplication()).selectedGroup;

		/* start tlacitka pro vyber fotky z galerie */

		Button buttonLoadImage = (Button) findViewById(R.id.loadimage);
		textTargetUri = (TextView) findViewById(R.id.targeturi);
		buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new Intent( Intent.ACTION_PICK,
				 * android.provider
				 * .MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 * startActivityForResult(intent, 0);
				 */
				Button loadImageBtn = null;
				loadImageBtn = (Button) findViewById(R.id.loadimage);
				openContextMenu(loadImageBtn);

			}
		});
		/* konec tlacitka pro vyber fotky z galerie */

		setGroupSelection();

		groups.setAdapter(myAdapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo ami = (AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Add new photo");
		menu.add(0, v.getId(), 0, "Gallery");
		menu.add(0, v.getId(), 0, "Camera");
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		if (item.getTitle() == "Gallery") {
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 0);
		} else if (item.getTitle() == "Camera") {
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			File photo = new File(Environment.getExternalStorageDirectory(),
					"Pic.jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
			imageUri = Uri.fromFile(photo);
			startActivityForResult(intent, 3);
		} else {
			return false;
		}
		return true;
	}

	/*
	 * metoda preda URI do adapteru
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
					s.setSelection(position);
					break;
				}
				position++;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 3:
			if (resultCode == RESULT_OK) {
				//Uri selectedImage = imageUri;
				Log.d("", "photo taken");
				Log.d("", imageUri.toString());
				addToAdapter(imageUri);
				/*
				 * getContentResolver().notifyChange(selectedImage, null);
				 * ImageView imageView = (ImageView)
				 * findViewById(R.id.ImageView); ContentResolver cr =
				 * getContentResolver(); Bitmap bitmap; try { bitmap =
				 * android.provider.MediaStore.Images.Media .getBitmap(cr,
				 * selectedImage);
				 * 
				 * imageView.setImageBitmap(bitmap); Toast.makeText(this,
				 * selectedImage.toString(), Toast.LENGTH_LONG).show(); } catch
				 * (Exception e) { Toast.makeText(this, "Failed to load",
				 * Toast.LENGTH_SHORT) .show(); Log.e("Camera", e.toString()); }
				 */
			}
		case 0:
			if (resultCode == RESULT_OK) {
				addToAdapter(data.getData());
			}
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
		if("".equals(personName.trim())) {
			Builder builder = new AlertDialog.Builder(AddNewPersonActivity.this);
			builder.setMessage(R.string.name_not_set).
			setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
			builder.show();
			return;
		}

		final Spinner groupSpinner = (Spinner) findViewById(R.id.groupSpinner);
		String groupName = groupSpinner.getSelectedItem().toString();

		Person person = new Person();
		person.setName(personName);
		myAdapter.getCount();

		List<String> pathArray = new ArrayList<String>();
		for (int i = 0; i < myAdapter.getCount(); i++) {
			pathArray.add(myAdapter.getItem(i).toString());
			// Log.d("",myAdapter.getItem(i).toString());
		}
		person.setPhotoPaths(this.getBaseContext(), pathArray);

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
						// Action for �NO� Button
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
		Log.d("", " Prave kliknuto");
		Log.d("View", v.toString());
		// @ToDo tato metoda musi odtranit polo�ku z myAdapter
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
