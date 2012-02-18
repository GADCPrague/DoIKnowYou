package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTester implements Tester {
	List<Person> people;
	int index;
	Group group;

	@Override
	public void setGroup(Group group) {
		people = new ArrayList<Person>();
		people.addAll(group.getPeopleList());
		this.group = group;
		Collections.shuffle(people);
		
		index = 0;
	}

	@Override
	public Person getTestCase() {
		if (index == people.size()) {
			return null;
		}
		return people.get(index);
	}

	@Override
	public void putResult(Boolean ok) {
		index++; // TODO: do something with the result
	}
	
	@Override
	public List<Person> getChoices() {
		List<Person> p = new ArrayList<Person>();
		p.addAll(group.getPeopleList());
		Collections.shuffle(p);
		List<Person> sl = p.subList(0, 3);
		if (!sl.contains(getTestCase())) {
			sl.add(getTestCase());
		}
		return sl;
	}

}
