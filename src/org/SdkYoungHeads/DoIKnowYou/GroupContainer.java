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
		return groups.toArray(new Group[groups.size()]);
	}

	public void addGroup(Group g) {
		groups.add(g);
		groupsByUuid.put(g.getUUID(), g);
		save();
	}

	public void removeGroup(Group g) {
		groupsByUuid.remove(g.getUUID());
		groups.remove(g);
		save();
	}

	public Group getGroupByName(String name) {
		for (Group g : groups) {
			if (g.getName() == name) {
				return g;
			}
		}
		return null;
	}

	public void serialize(XmlSerializer serializer)
			throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "groups");
		for (Group g : getGroups()) {
			g.serialize(serializer);
		}
		serializer.endTag("", "groups");
	}

	private final String FILE = "data.xml";

	public void load(Context ctx) {
		try {
			FileInputStream fis = ctx.openFileInput(FILE);

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document dom = builder.parse(fis);
				Element root = dom.getDocumentElement();
				NodeList items = root.getElementsByTagName("group");
				for (int i = 0; i < items.getLength(); i++) {
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

	public void save(Context ctx) throws IllegalArgumentException,
			IllegalStateException, IOException {
		XmlSerializer serializer = Xml.newSerializer();
		FileOutputStream fos = ctx.openFileOutput(FILE, Context.MODE_PRIVATE);
		OutputStreamWriter writer = new OutputStreamWriter(fos);

	    serializer.setOutput(writer);
	    serializer.startDocument("UTF-8", true);
	    serialize(serializer);
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
				p.setName("Jan Novak " + i + " " + j);
				g.addPerson(p);
			}
			addGroup(g);
		}
	}

	public Person[] getAllPersons() {
		List<Person> persons = new ArrayList<Person>();
		for (Group g : getGroups()) {
			persons.addAll(g.getPeopleList());
		}
		return persons.toArray(new Person[persons.size()]);
	}

	public void save() {
		// This is called when anything is changed.
		// This DOESN'T SAVE yet. A context would be needed...
	}
}
