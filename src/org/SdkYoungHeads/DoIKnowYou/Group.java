package org.SdkYoungHeads.DoIKnowYou;

public class Group {
	public String getName() {
		return "Ahoj";
	}
	
	public Person[] getPeople() {
		Person[] people = new Person[5];
		people[0] = new Person();
		people[1] = new Person();
		people[2] = new Person();
		people[3] = new Person();
		people[4] = new Person();
		
		return people;
	}
}
