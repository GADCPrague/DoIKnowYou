package org.SdkYoungHeads.DoIKnowYou;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class Person {
	private UUID uuid;
	private Group group;
	private String name;
	
	public Person() {
		photoPaths = new ArrayList<String>();
		photos = null;
		uuid = UUID.randomUUID();
	}
	
	public void setGroup(Group g) {
		group = g;
	}
	
	public void save() {
		if (group != null) {
			group.save();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		save();
	}
	
	// TODO: add photo
	
	private Bitmap photos[];
	private List<String> photoPaths;
	
	private Boolean arePhotosLoaded() {
		return photos != null && (photos.length == 0 && photoPaths.size() > 0);
	}
	
	private void loadPhotos(Context context) throws IOException {
		photos = new Bitmap[photoPaths.size()];
		for (int i = 0; i < photoPaths.size(); i++) {
			FileInputStream fis = context.openFileInput(photoPaths.get(i));
			photos[i] = BitmapFactory.decodeStream(fis);
			fis.close();
		}
	}
	
	// TODO: delete all photos when deleting the group.
	
	private void ensurePhotosLoaded(Context context) throws IOException {
		if (!arePhotosLoaded()) {
			loadPhotos(context);
		}
	}
	
	// TODO: add photo, remove photo, reorder

	public Bitmap getMainPhoto(Context context) throws IOException {
		ensurePhotosLoaded(context);
		
		//if (photos.length == 0) {
		//	return BitmapFactory.decodeResource(getResources(), R.id.defaultPhoto);
		//}
		if (photos.length == 0) {
			return null;
		}
		return photos[0];
	}
	
	public Bitmap getSomePhoto(Context context) throws IOException {
		if (getPhotos(context).length == 0) return null;
		Random r = new Random();
		return getPhotos(context)[r.nextInt(getPhotos(context).length)];
	}
	
	public Bitmap[] getPhotos(Context context) throws IOException {
		ensurePhotosLoaded(context);
		return photos;
	}
	
	public void setPhotoPaths(Context context, List<String> paths) throws IOException {
		for (int i = 0; i < paths.size(); i++) {
			String s = paths.get(i);
			Bitmap b = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(s)));
			int width = b.getWidth(), height = b.getHeight();
			if (width > height) {
				height = (int)(50 * (double)height / (double)width);
				width = 50;
			} else {
				width = (int)(50 * (double)width / (double)height);
				height = 50;
			}
			
			String name = UUID.randomUUID().toString() + ".jpg";
			b = Bitmap.createScaledBitmap(b, width, height, true);
			FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
			b.compress(Bitmap.CompressFormat.JPEG, 85, fos);
			fos.close();
			
			paths.set(i, name);
		}
		photoPaths = paths;
		photos = new Bitmap[0];
	}
			
	final static String NAME = "name";
	final static String PATH = "path";
	final static String UUID_ATTR = "uuid";

	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "person");
		serializer.attribute("", NAME, getName());
		serializer.attribute("", UUID_ATTR, uuid.toString());
		serializer.startTag("", "photos");
		for (String path : photoPaths) {
			serializer.startTag("", "photo");
			serializer.attribute("", PATH, path);
			serializer.endTag("", "photo");
		}
		serializer.endTag("", "photos");
		serializer.endTag("", "person");
	}

	public void deserialize(Node node) {
        NodeList photos = node.getChildNodes().item(0).getChildNodes();
        NamedNodeMap attributes = node.getAttributes();
        setName(attributes.getNamedItem(NAME).getTextContent());
        uuid = UUID.fromString(attributes.getNamedItem(UUID_ATTR).getTextContent());
        List<String> p = new ArrayList<String>();
        
        for (int i = 0; i < photos.getLength(); i++) {
        	attributes = photos.item(i).getAttributes();
        	p.add(attributes.getNamedItem(PATH).getTextContent());
        }
        
        photoPaths = p;
	}
}
