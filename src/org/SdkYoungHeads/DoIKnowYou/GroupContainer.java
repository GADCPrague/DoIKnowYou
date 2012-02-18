package org.SdkYoungHeads.DoIKnowYou;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

public class GroupContainer {
	private List<Group> groups;
	private HashMap<UUID, Group> groupsByUuid;
	
	public GroupContainer() {
		groups = new ArrayList<Group>();
		groupsByUuid = new HashMap<UUID, Group>();
	}
	
	public Group[] getGroups() {
		//Group[] categories = new Group[0];
		/*
		categories[0] = new Group();
		categories[1] = new Group();
		categories[2] = new Group();
		categories[3] = new Group();
		categories[4] = new Group();
		 */
		
		//return categories;
		return groups.toArray(new Group[groups.size()]);
		//return groups.toArray();
	}
	
	public void addGroup(Group g) {
		groups.add(g);
		groupsByUuid.put(g.getUUID(), g);
	}

	// TODO: removeGroup()
	
	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "groups");
		for (Group g: getGroups()) {
			g.serialize(serializer);
		}
		serializer.endTag("", "groups");
	}
	
	private final String FILE = "data.xml";
	
	public void load(Context ctx) {
		try {
			FileInputStream fis = ctx.openFileInput(FILE);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	try {
	        	DocumentBuilder builder = factory.newDocumentBuilder();
	        	Document dom = builder.parse(fis);
	        	Element root = dom.getDocumentElement();
	        	NodeList items = root.getElementsByTagName("group");
	        	for (int i=0;i<items.getLength();i++){
	        		Group g = new Group();
	        		g.deserialize(items.item(i));
	        		addGroup(g);
	        	}
	    	} catch (Exception e) {
	        	throw new RuntimeException(e);
	    	}
		} catch (FileNotFoundException e) {
			return; // Not a problem. Return Groups == [].
		}
	}

	public void save(Context ctx) throws IllegalArgumentException, IllegalStateException, IOException {
	    XmlSerializer serializer = Xml.newSerializer();
		FileOutputStream fos = ctx.openFileOutput(FILE, Context.MODE_PRIVATE);
		OutputStreamWriter writer = new OutputStreamWriter(fos);
	    serializer.setOutput(writer);
	    serializer.startDocument("UTF-8", true);
	    GroupContainer gc = new GroupContainer();
	    gc.serialize(serializer);
	    serializer.endDocument();
	    fos.close();
	}

	public Group findGroup(UUID uuid) {
		return groupsByUuid.get(uuid);
	}
	
	public void createExampleData() {
		groups = new ArrayList<Group>();
		for (int i = 0; i < 5; i++) {
			Group g = new Group();
			g.setName("Skupina " + i);
			for (int j = 0; j < 10; j++) {
				Person p = new Person();
				p.setName("Jan Novak " + i);
				g.addPerson(p);
			}
			addGroup(g);
		}
	}
}
