package pemapmodder.mcpeit;

import pemapmodder.mcpeit.idfinder.IDFinder;
import pemapmodder.mcpeit.idfinder.Id;
import pemapmodder.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class IDFinderActivity extends Activity {
	public static final int NAME_FILTER = 0x80000000;
	private static final int NAME_LIST = 0x80000001;
	public IDFinder finder=new IDFinder(this);
	@Override protected void onCreate(Bundle savedInstanceState) {
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
				filter();}});
		ret.addView(filter, Utils.flatParams);
		
		ScrollView scroll=new ScrollView(this);
		LinearLayout list=new LinearLayout(this);
		list.setId(NAME_LIST);
		list.setOrientation(LinearLayout.VERTICAL);
		filter();
		scroll.addView(list, Utils.flatParams);
		return ret;
	}
	protected void filter() {
		String name=((EditText)findViewById(NAME_FILTER)).getText().toString();
		try {
			Id[] ids=finder.getIds(name);
			LinearLayout list;
			(list=(LinearLayout)findViewById(NAME_LIST)).removeAllViews();
			for(int i=0;i<ids.length;i++){
				TextView t=new TextView(this);
				String idStr=new String(Integer.toString(ids[i].getId())+
						(ids[i].getDamage()==0?"":Integer.toString(ids[i].getDamage()))+"\n");
				t.setText(idStr+ids[i].getName()+"\n");
				t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				t.setId(0x10000000+ids[i].getId()*100+ids[i].getDamage());
				t.setOnClickListener(new OnClickListener(){
					@Override public void onClick(View v) {
						
					}});
				list.addView(t, Utils.flatParams);
			}
		} catch (Exception e) {
			Utils.err(this, e);
		}
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.idfinder, menu);
		return true;
	}

}
