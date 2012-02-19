package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.graphics.Bitmap;

public class Group {
	private String name;
	private UUID uuid;
	private GroupContainer gc;
	
	public Group() {
		people = new ArrayList<Person>();
		uuid = UUID.randomUUID();
	}
	
	public void setGroupContainer(GroupContainer gc) {
		this.gc = gc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		save();
	}
	
	private List<Person> people;
	
	public Person[] getPeople() {
		return people.toArray(new Person[people.size()]);
	}
	
	public List<Person> getPeopleList() {
		return people;
	}
	
	public void save() {
		if (gc != null) {
			gc.save();
		}
	}
	
	public void addPerson(Person person) {
		person.claimPhotos();
		people.add(person);
		save();
	}
	
	public void removePerson(Person person) {
		people.remove(person);
		save();
	}
	
	private final String NAME = "name";
	private final String UUID_ATTR = "uuid";

	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "group");
		serializer.attribute("", NAME, getName());
		serializer.attribute("", UUID_ATTR, uuid.toString());
		serializer.startTag("", "people");
		for (Person p: getPeople()) {
			p.serialize(serializer);
		}
		serializer.endTag("", "people");
		serializer.endTag("", "group");
	}
	
	public void deserialize(Node node) {
        NodeList people = (node.getChildNodes().item(0)).getChildNodes();
        NamedNodeMap attributes = node.getAttributes();
        setName(attributes.getNamedItem(NAME).getTextContent());
        uuid = UUID.fromString(attributes.getNamedItem(UUID_ATTR).getTextContent());
        
        for (int i = 0; i < people.getLength(); i++) {
        	Person p = new Person();
        	p.deserialize(people.item(i));
        	addPerson(p);
        }
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public int getCount() {
		return people.size();
	}

	public Bitmap getIcon() {
		if (people.size() == 0) return null;
		return people.get(0).getMainPhoto(); // TODO: maybe some other one...
	}
}
