package pemapmodder.mcpeit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MEM_DefBlock extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mem_defblock);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mem__def_block, menu);
		return true;
	}

}
