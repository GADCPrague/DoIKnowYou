package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

	ArrayList<String> listItems = new ArrayList<String>();
	protected ArrayAdapter<String> adapter;
	private GroupContainer groupContainer;

	private GridView photoGrid;
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
		// @ToDo nastavit defaultní selekt
		// s.setSelection(2);
		// Group selectedGroup = ((Application) getApplication()).selectedGroup;


		
	    GridView photoGrid = (GridView) findViewById(R.id.photoGrid);
	    photoGrid.setAdapter(new ImageAdapter(getApplicationContext()));


	    photoGrid.setOnItemClickListener(new GridView.OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) 
	        {                
	            //tady je nutné smazat obrázek
	        }
	    });   

	    
	    
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

	}

	private void addImageToGrid(Uri uri){
		//ToDo tady se namísto vložení do pole vloží do photoGrid
		EditText nameField = (EditText) findViewById(R.id.editTextName);
		nameField.setText(uri.toString());
		
		
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
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
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

	//http://xjaphx.wordpress.com/2011/06/12/custom-grid-view-of-applications/
	//http://android-er.blogspot.com/2011/02/select-image-using-android-build-in.html
	
	public class ImageAdapter extends BaseAdapter 
	{
		//ToDo pøedìlat na ArrayAdapter nebo jinak dostat do Adaptéru metodu add
	    private Context context;

	    public ImageAdapter(Context c) 
	    {
	        context = c;
	    }

	    //---returns the number of images---
	    public int getCount() {
	        return imageIDs.length;
	    }

	    //---returns the ID of an item--- 
	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    //---returns an ImageView view---
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	        ImageView imageView;
	        if (convertView == null) {
	            imageView = new ImageView(context);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(5, 5, 5, 5);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        imageView.setImageResource(imageIDs[position]);
	        return imageView;
	    }

	    Integer[] imageIDs = {
	            R.drawable.ic_launcher,
	            R.drawable.ic_launcher
	    };
	}   
}

 
