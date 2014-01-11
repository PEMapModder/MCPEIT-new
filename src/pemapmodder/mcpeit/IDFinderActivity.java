package pemapmodder.mcpeit;

import pemapmodder.mcpeit.idfinder.IDFinder;
import pemapmodder.mcpeit.idfinder.Id;
import pemapmodder.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class IDFinderActivity extends Activity {
	public static final int NAME_FILTER = 0x80000000;
	private static final int NAME_LIST = 0x80000001;
	public IDFinder finder=new IDFinder(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
	}
	private LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		EditText filter=new EditText(this);
		
		filter.setId(NAME_FILTER);
		filter.addTextChangedListener(new TextWatcher(){
			@Override public void afterTextChanged(Editable arg0){}
			@Override public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){}
			@Override public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
				filter();
			}
		});
		ret.addView(filter, Utils.flatParams);
		
		TableLayout list=new TableLayout(this);
		list.setId(NAME_LIST);
		filter();
		return ret;
	}
	protected void filter() {
		String name=((EditText)findViewById(NAME_FILTER)).getText().toString();
		try {
			Id[] ids=finder.getIds(name);
			for(int i=0;i<ids.length;i++){
				TextView t=new TextView(this);
				t.setText(ids[i].getName());
				
			}
		} catch (Exception e) {
			Utils.err(this, e);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.idfinder, menu);
		return true;
	}

}
