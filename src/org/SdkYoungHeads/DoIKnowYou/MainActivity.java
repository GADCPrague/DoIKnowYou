package org.SdkYoungHeads.DoIKnowYou;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity { // ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO: Tady vypsat seznam skupin...
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/*
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		*/

		String s;
		try {
	    XmlSerializer serializer = Xml.newSerializer();
	    StringWriter writer = new StringWriter();
	    serializer.setOutput(writer);
	    serializer.startDocument("UTF-8", true);
	    GroupContainer gc = new GroupContainer();
	    gc.serialize(serializer);
	    serializer.endDocument();
	    s=writer.toString();
		} catch (Exception e) {
			s="!!!";
		}
	    TextView tv = (TextView)this.findViewById(R.id.text);
	    tv.setText(s);
	    
	    //return writer.toString();
	}
/*
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//String item = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		
		
		// TODO: prejit na detail aktivitu
	}*/
}