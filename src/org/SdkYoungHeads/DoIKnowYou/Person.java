package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	
	// TODO: add photo, remove photo, reorder

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
	
	public Bitmap getSomePhoto() {
		if (getPhotos().length == 0) return null;
		Random r = new Random();
		return getPhotos()[r.nextInt(getPhotos().length)];
	}
	
	public Bitmap[] getPhotos() {
		ensurePhotosLoaded();
		return photos;
	}
	
	public void setPhotoPaths(List<String> paths) {
		photoPaths = paths;
		photos = new Bitmap[0];
	}
	
	public void claimPhotos() {
		// TODO: jestli fotky nepatri me, zmensit a ulozit ke me.
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
