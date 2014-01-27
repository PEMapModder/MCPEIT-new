/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.util.ArrayList;
import java.util.List;

import pemapmodder.NameAndId;
import pemapmodder.Utils;
import pemapmodder.mcpeit.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MEM_DefMob extends Activity {
	protected boolean realBack=false;
	protected int step=1;
	protected int defaultMobId=-1;
	protected int defaultMobRenderId=-1;
	protected String mobSkinPath=null;
	protected String optionalName=null;
	protected String[] actionsWhenClose={};
	protected double closeDistance=-1;
	public final static int TEMP_TEXTVIEW=0x60000000;
	public final static int EDITTEXT_MOBNAME_ID=0x60000011;
	public final static int SPINNER_MOBTYPE_ID=0x60000012;
	public final static int SPINNER_MOBSHAPE_ID=0x60000013;
	public static final int EDITTEXT_MOBDISTANCE_ID = 0x60000021;
	public static final int TEXTVIEW_BEGINNER = 0x60000022;
	public static final int LISTVIEW_ACTIONS_ID = 0x60000023;
	public static final int REQUEST_SELECT_MOB_ACTION = 0x50000021;
	@Override protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(getLayout(step));
		}catch(Throwable e){
			Utils.err(this, e);finish();
		}
	}
	protected LinearLayout getLayout(int step)throws Throwable{
		LinearLayout ret=new LinearLayout(this);
		TextView title=new TextView(this);
		title.setText(string.title_activity_mem__def_mob);
		ret.addView(title);
		if(step==1){
			//mob name
			EditText et=new EditText(this);
			et.setHint(string.MEM_mob_nameOptional);
			et.setId(EDITTEXT_MOBNAME_ID);
			ret.addView(et);
			//mob shape
			Spinner mobShape=getSpinnerByStringList(getAllEntitiesRenderTypesNames());
			mobShape.setId(SPINNER_MOBSHAPE_ID);
			ret.addView(mobShape,Utils.flatParams);
			//mob behavior
			Spinner mobDefBehav=getSpinnerByStringList(getAllEntitiesNames());
			mobDefBehav.setId(SPINNER_MOBTYPE_ID);
			ret.addView(mobDefBehav,Utils.flatParams);
		}
		if(step==2){
			//actions
			LinearLayout actionWhenClose=new LinearLayout(this);
			actionWhenClose.setOrientation(LinearLayout.HORIZONTAL);
			//sentence
			TextView sentence=new TextView(this);
			sentence.setId(TEXTVIEW_BEGINNER);
			sentence.setText(getString(string.MEM_mob_whenClose).replace("$1", "5"));
			actionWhenClose.addView(sentence, Utils.flatParams);
			//EditText for distance
			EditText distance=new EditText(this);
			distance.setRawInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			distance.setHint(string.MEM_mob_distanceActivate);
			distance.setId(EDITTEXT_MOBDISTANCE_ID);
			distance.addTextChangedListener(new MobDistanceTextWatcher());
			actionWhenClose.addView(distance, Utils.flatParams);
			ret.addView(actionWhenClose, Utils.flatParams);
			//actions
			ListView lv=new ListView(this);
			lv.setId(LISTVIEW_ACTIONS_ID);
			ret.addView(lv, Utils.flatParams);
			Button addAction=new Button(this);
			addAction.setText(string.MEM_mob_addAction);
			addAction.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
				startActivityForResult(new Intent(get(),MobActionSelector.class),REQUEST_SELECT_MOB_ACTION);
			}});
		}
		ret.addView(getNextButton());
		return ret;
	}
	protected void updateData()throws Throwable{
		switch(step){
		case 1:
			this.optionalName=((EditText)findViewById(EDITTEXT_MOBNAME_ID)).getText().toString();
			this.defaultMobRenderId=Utils.getEntityRenderId(
					((Spinner)findViewById(SPINNER_MOBSHAPE_ID))
					.getSelectedItem().toString(),this);
			this.defaultMobId=Utils.getEntityId(
					((Spinner)findViewById(SPINNER_MOBTYPE_ID))
					.getSelectedItem().toString(), this);
			break;
		}
	}
	protected Spinner getSpinnerByStringList(List<String> itemList)throws Throwable{
		ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,itemList);
		Spinner ret=new Spinner(this);
		ret.setAdapter(aa);
		return ret;
	}
	protected Spinner getSpinnerByStringArray(String[] items)throws Throwable{
		List<String> itemList=new ArrayList<String>();
		for(int i=0;i<items.length;i++)
			itemList.add(items[i]);
		return getSpinnerByStringList(itemList);
	}
	protected List<String> getAllEntitiesRenderTypesNames()throws Throwable{
		List<String> ret=new ArrayList<String>();
		ret.add(getString(string.MEM_mob_chooseRenderType));
		NameAndId[] nai=Utils.getEntitiesRenderTypes(this);
		for(int i=0;i<nai.length;i++)
			ret.add(nai[i].getName());
		return ret;
	}
	protected List<String> getAllEntitiesNames()throws Throwable{
		List<String> ret=new ArrayList<String>();
		ret.add(getString(string.MEM_mob_chooseBehaviourType));
		NameAndId[] nai=Utils.getEntities(this);
		for(int i=0;i<nai.length;i++)
			ret.add(nai[i].getName());
		return ret;
	}
	protected Button getNextButton(){
		Button ret=new Button(this);
		ret.setText(string.MEM_title);
		ret.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v) {
				step++;
				try {
					reloadView();
					updateData();
				} catch (Throwable e) {
					Utils.err(get(),e);
					finish();
				}
		}});
		ret.setLayoutParams(Utils.flatParams);
		return ret;
	}
	protected void reloadView() throws Throwable{
		setContentView(getLayout(step));
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mem__def_mob, menu);
		return true;
	}
	@Override public void onBackPressed(){
		if(realBack==false){
			Toast.makeText(this,string.MEM_confirmBack$1,Toast.LENGTH_LONG).show();
			Toast.makeText(this,string.MEM_confirmBack$2,Toast.LENGTH_SHORT).show();
			realBack=true;
		}
		else super.onBackPressed();
	}
	public MEM_DefMob get(){
		return this;
	}
	public class MobDistanceTextWatcher implements TextWatcher{
		@Override public void afterTextChanged(Editable s){
			Double cd=closeDistance;
			closeDistance=Double.parseDouble(s.toString());
			((TextView)findViewById(TEXTVIEW_BEGINNER)).setText(
					((TextView)findViewById(TEXTVIEW_BEGINNER)).getText().toString().replace(
					Double.toString(cd),
					((EditText)findViewById(EDITTEXT_MOBDISTANCE_ID)).getText().toString()));
		}
		@Override public void beforeTextChanged(CharSequence cs,int s,int c,int a){}
		@Override public void onTextChanged(CharSequence cs,int s,int b,int c){}
	}
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
	}
}
