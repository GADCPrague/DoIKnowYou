package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Person {
	public String getName() {
		return "Pepa";
	}
	
	private Bitmap photos[];
	private String photoPaths[];
	
	private Boolean arePhotosLoaded() {
		return photos != null;
	}
	
	private void loadPhotos() {
		photos = new Bitmap[photoPaths.length];
		for (int i = 0; i < photoPaths.length; i++) {
			photos[i] = BitmapFactory.decodeFile(photoPaths[i]);
		}
	}
	
	private void ensurePhotosLoaded() {
		if (!arePhotosLoaded()) {
			loadPhotos();
		}
	}

	public Bitmap getMainPhoto() {
		ensurePhotosLoaded();
		
		//if (photos.length == 0) {
		//	return BitmapFactory.decodeResource(getResources(), R.id.defaultPhoto);
		//}
		if (photos.length == 0) {
			return null;
		}
		return photos[0];
	}
	
	public Bitmap[] getPhotos() {
		ensurePhotosLoaded();
		return photos;
	}

	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "person");
		serializer.attribute("", "name", getName());
		serializer.startTag("", "photos");
		for (String path : photoPaths) {
			serializer.startTag("", "photo");
			serializer.attribute("", "path", path);
			serializer.endTag("", "photo");
		}
		serializer.endTag("", "person");
	}

}
