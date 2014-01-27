/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.io.File;

import pemapmodder.Utils;
import pemapmodder.mcpeit.R.string;
import pemapmodder.mcpeit.modmaker.ModCreator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ModEditorMain extends Activity {
	public final static int ATVT_RET_FOOD=0x1b2c8d0;
	public final static int ATVT_RET_ITEM=0x1b2c8d1;
	public final static int ATVT_RET_BLOCK=0x1b2c8d2;
	public final static int ATVT_RET_MOB=0x1b2c8d3;
	protected boolean backToMain=false;
	protected ModCreator creator;
	protected File output;
	@Override protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			output=new File(getIntent().getData().getPath());
			setContentView(getLayout());
			initCreator();
		}catch(Throwable e){
			Utils.err(this, e);
			finish();
		}
	}
	protected LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setOrientation(LinearLayout.VERTICAL);
		//Title TextView
		TextView title=new TextView(this);
		String titleText=getString(string.MEM_title).replace("$1", output.getName().substring(0, output.getName().length()-3));
		title.setText(titleText);
		ret.addView(title, Utils.flatParams);
		//DefBlock Button
		Button defBlock=new Button(this);
		defBlock.setText(string.MEM_defineNewBlock);
		defBlock.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
			startActivityForResult(new Intent(get(),MEM_DefBlock.class),ATVT_RET_BLOCK);
		}});
		ret.addView(defBlock,Utils.flatParams);
		//DefItem Button
		Button defItem=new Button(this);
		defItem.setText(string.MEM_defineNewItem);
		defItem.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
			startActivityForResult(new Intent(get(),MEM_DefItem.class),ATVT_RET_ITEM);
		}});
		//DefFoodItem Button
		Button defFoodItem=new Button(this);
		defFoodItem.setText(string.MEM_defineNewFoodItem);
		defFoodItem.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
			startActivityForResult(new Intent(get(),MEM_DefFood.class),ATVT_RET_FOOD);
		}});
		//DefMod Button
		Button defMob=new Button(this);
		defMob.setText(string.MEM_defineNewMob);
		defMob.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
			startActivityForResult(new Intent(get(),MEM_DefMob.class),ATVT_RET_MOB);
		}});
		return ret;
	}
	protected void initCreator()throws Throwable{
		File f=new File(getIntent().getData().getPath());
		this.creator=new ModCreator(f,this);
	}
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mod_editor_main, menu);
		return true;
	}
	@Override public void onBackPressed(){
		if(this.backToMain){
			setContentView(getLayout());
			this.backToMain=false;
		}
		else super.onBackPressed();
	}
	protected ModEditorMain get(){
		return this;
	}
}
