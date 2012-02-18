package org.SdkYoungHeads.DoIKnowYou;

import java.util.List;

public interface Tester {
	public void setGroup(Group group);
	public Person getTestCase();
	public void putResult(Boolean ok);
	public List<Person> getChoices();
	public int getMaximumPoints();
	public int getPoints();
	public int getPercent();
}
