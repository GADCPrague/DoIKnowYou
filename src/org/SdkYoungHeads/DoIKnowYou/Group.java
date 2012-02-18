package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class Group {
	private String name;
	
	public Group() {
		people = new ArrayList<Person>();
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
	// TODO
	
	public void addPerson(Person person) {
		// TODO: implementovat
	}
	
	public void removePerson(Person person) {
		// TODO: implementovat
	}
	
	private final String NAME = "name";

	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "group");
		serializer.attribute("", NAME, getName());
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
        
        for (int i = 0; i < people.getLength(); i++) {
        	Person p = new Person();
        	p.deserialize(people.item(i));
        	addPerson(p);
        }
	}
}
