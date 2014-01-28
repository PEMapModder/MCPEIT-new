package pemapmodder.mcpeit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class MobActionSelector extends Activity {
	public final static int VIEW_LIST=0;
	public final static int VIEW_YAW=1;
	public final static int VIEW_EXPLODE=2;
	public final static int VIEW_TIMER=3;
	public final static int VIEW_BEHURT=4;
	public final static int VIEW_HARMPLAYER=5;
	protected int view=VIEW_LIST;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateContentView();
	}
	protected LinearLayout updateContentView(){
		LinearLayout ret=new LinearLayout(this);
		switch(this.view){
		case VIEW_LIST:
			break;
		case VIEW_YAW:
			break;
		case VIEW_EXPLODE:
			break;
		case VIEW_TIMER:
			break;
		case VIEW_BEHURT:
			break;
		case VIEW_HARMPLAYER:
			break;
		}
		return ret;
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mob_action_selector, menu);
		return true;
	}
}
