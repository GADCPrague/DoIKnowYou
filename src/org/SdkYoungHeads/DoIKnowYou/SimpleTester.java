package org.SdkYoungHeads.DoIKnowYou;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTester implements Tester {
	List<Person> people;
	int index;
	Group group;
	int countOk;

	@Override
	public void setGroup(Group group) {
		people = new ArrayList<Person>();
		people.addAll(group.getPeopleList());
		this.group = group;
		Collections.shuffle(people);
		
		index = 0;
		countOk = 0;
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
		index++;
		if (ok) {
			countOk++;
		}
	}
	
	@Override
	public List<Person> getChoices() {
		List<Person> p = new ArrayList<Person>();
		p.addAll(group.getPeopleList());
		Collections.shuffle(p);
		int n = Math.min(4, p.size());
		List<Person> sl = p.subList(0, n);
		if (!sl.contains(getTestCase())) {
			sl.remove(0);
			sl.add(getTestCase());
		}
		return sl;
	}

	@Override
	public int getMaximumPoints() {
		return group.getCount();
	}
	
	@Override
	public int getPoints() {
		return countOk;
	}
	
	@Override
	public int getPercent() {
		return (int)(((double)getPoints() / (double)getMaximumPoints()) * 100.0);
	}
}
