package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class Group {
	private String name;
	private UUID uuid;
	
	public Group() {
		people = new ArrayList<Person>();
		uuid = UUID.randomUUID();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private List<Person> people;
	
	public Person[] getPeople() {
		return people.toArray(new Person[people.size()]);
	}
	
	public List<Person> getPeopleList() {
		return people;
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(Person person) {
		people.remove(person);
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
        NodeList people = node.getChildNodes();
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
}
