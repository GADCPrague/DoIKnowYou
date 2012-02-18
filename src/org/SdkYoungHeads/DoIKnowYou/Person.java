package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

public class Person {
	public String getName() {
		return "Pepa";
	}
	
	public void serialize(XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
		serializer.startTag("", "person");
		serializer.attribute("", "name", getName());
		serializer.endTag("", "person");
	}
	
}
