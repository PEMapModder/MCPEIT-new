package pemapmodder.mcpeit;

import pemapmodder.Utils;
import pemapmodder.mcpeit.R.string;
import pemapmodder.mcpeit.idfinder.IDFinder;
import pemapmodder.mcpeit.idfinder.Id;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

public class IDFinderActivity extends Activity {
	public static final int NAME_FILTER = 0x80000000;
	private static final int NAME_LIST = 0x80000001;
	public static final String INTENT_RESULT_ITEM_ID = "mcpeit.normal.IDFinderActivity.itemId";
	public static final String INTENT_RESULT_ITEM_DAMAGE = "mcpeit.normal.IDFinderActivity.itemDamage";
	private static final int CONTENT_VIEW = 0x80000002;
	public IDFinder finder=new IDFinder(this);
	public boolean hadBackBeenPressed=false;
	protected Intent result=new Intent();
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		setResult(RESULT_CANCELED);
	}
	private LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setId(CONTENT_VIEW);
		ret.setOrientation(LinearLayout.VERTICAL);
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
						int sumed=v.getId()-0x10000000;
						onItemChosen(sumed/100,sumed%100);
					}});
				list.addView(t, Utils.flatParams);
			}
			
		} catch (Exception e) {
			Utils.err(this, e);
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	protected void onItemChosen(int id, int damage) {
		result.putExtra(INTENT_RESULT_ITEM_ID, id);
		result.putExtra(INTENT_RESULT_ITEM_DAMAGE, damage);
		setResult(RESULT_OK,result);
		finish();
	}
	@Override public void onBackPressed(){
		if(!hadBackBeenPressed){
			hadBackBeenPressed=true;
			Toast.makeText(this, string.IDFinder_backTwicePress, Toast.LENGTH_SHORT).show();
		}
		else{
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.idfinder, menu);
		return true;
	}

}
