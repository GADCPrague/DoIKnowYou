package org.SdkYoungHeads.DoIKnowYou;

public class Application extends android.app.Application {
	private GroupContainer data;
	public Group selectedGroup;
	//@ToDo selectGroup není duvod aby byla public, udelat settery a gettery
	public Tester currentTester;
	
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
