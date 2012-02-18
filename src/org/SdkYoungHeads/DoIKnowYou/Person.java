package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Person {
	public Person() {
		photoPaths = new ArrayList<String>();
		photos = null;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// TODO: add photo
	
	private Bitmap photos[];
	private List<String> photoPaths;
	
	private Boolean arePhotosLoaded() {
		return photos != null;
	}
	
	private void loadPhotos() {
		photos = new Bitmap[photoPaths.size()];
		for (int i = 0; i < photoPaths.size(); i++) {
			photos[i] = BitmapFactory.decodeFile(photoPaths.get(i));
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
		
	final static String NAME = "name";
	final static String PATH = "path";

	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "person");
		serializer.attribute("", NAME, getName());
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
        NodeList photos = node.getChildNodes();
        NamedNodeMap attributes = node.getAttributes();
        setName(attributes.getNamedItem(NAME).getTextContent());
        List<String> p = new ArrayList<String>();
        
        for (int i = 0; i < photos.getLength(); i++) {
        	attributes = photos.item(i).getAttributes();
        	p.add(attributes.getNamedItem(PATH).getTextContent());
        }
        
        photoPaths = p;
	}
}
