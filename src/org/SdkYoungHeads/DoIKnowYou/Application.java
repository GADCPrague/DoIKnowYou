package org.SdkYoungHeads.DoIKnowYou;

public class Application extends android.app.Application {
	private GroupContainer data;
	public Group selectedGroup;
	public Tester currentTester;
	
	public Application() {
		data = new GroupContainer();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		data.load(getBaseContext());
		getDatabase().createExampleData();
	}
	
	public GroupContainer getDatabase() {
		return data;
	}
}
