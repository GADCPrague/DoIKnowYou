package org.SdkYoungHeads.DoIKnowYou;

public class Application extends android.app.Application {
	private GroupContainer data;
	public Group selectedGroup;
	public Person selectedPerson;
	//@ToDo selectGroup nen� duvod aby byla public, udelat settery a gettery
	public Tester currentTester;

//  vypnuto	
//	public Person[] selectedPersons = null;
	
	public Application() {
		data = new GroupContainer();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		data.load(getBaseContext());
	}
	
	public GroupContainer getDatabase() {
		return data;
	}
}
