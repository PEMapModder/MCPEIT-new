package pemapmodder.mcpeit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import pemapmodder.utils.Utils;

@SuppressWarnings("unused")
public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
