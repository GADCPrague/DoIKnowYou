package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		
		try {
			loadPerson();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadPerson() throws IOException {
		Person p = ((Application)getApplication()).selectedPerson;
		TextView tv = (TextView)findViewById(R.id.person_detail_name);
		tv.setText(p.getName());
		ImageView iv = (ImageView)findViewById(R.id.person_detail_image);
		iv.setImageBitmap(p.getSomePhoto(getBaseContext())); // TODO: vic fotek..
	}
	
    private float initialX = 0;  
    private float initialY = 0;  
    private float deltaX = 0;  
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        //This prevents touchscreen events from flooding the main thread  
        synchronized (event)  
        {  
            try  
            {  
                //Waits 16ms.  
                event.wait(16);  
  
                //when user touches the screen  
                if(event.getAction() == MotionEvent.ACTION_DOWN)  
                {  
                    //reset deltaX and deltaY  
                    deltaX = 0;  
  
                    //get initial positions  
                    initialX = event.getRawX();  
                    initialY = event.getRawY();  
                }  
  
                //when screen is released  
                if(event.getAction() == MotionEvent.ACTION_UP)  
                {  
                    deltaX = event.getRawX() - initialX;
                    
                    Application app = (Application)getApplication();
                    Group g = app.selectedGroup;
                	int id = -1;
                	for (int i = 0; i < g.getCount(); i++) {
                		if (g.getPeople()[i] == app.selectedPerson) {
                			id = i;
                			break;
                		}
                	}
                	boolean c = false;
                    //swipped up  
                    if(deltaX < -10)  
                    {  
                    	Log.d("DoIKnowYou", "DOLEVA");
                    	if (id != -1) {
                    		id++;
                    		id %= g.getCount();
                    	}
                    	c= true;
                    }  
                    else if (deltaX > 10)
                    {  
                    	Log.d("DoIKnowYou", "DOPRAVA");
                    	if (id != -1) {
                    		id--;
                    		if (id == -1) id = g.getCount()-1;
                    	}
                    	c= true;
                    }  
                    
                    if (c){
                    app.selectedPerson = g.getPeople()[id];
                    loadPerson();
                    }
  
                    return false;  
                }  
            }  
  
            catch (InterruptedException e)  
            {  
                return false;  
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }
		return false;  
    }  

}
