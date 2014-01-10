/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.io.File;

import pemapmodder.js.ModPECreator;
import pemapmodder.js.interpreter.JSInterpreter;
import pemapmodder.js.lang.ModScript;
import pemapmodder.mcpeit.R.string;
import pemapmodder.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ModEditorMain extends Activity {

	protected boolean backToMain=false;
	protected ModPECreator creator;
	protected ModScript script;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		try {
			File f=new File(getIntent().getData().getPath());
			final String scriptContent=Utils.readFile(f);
			Thread interpreter=new Thread(new Runnable(){
				@Override public void run() {
					try {
						script=JSInterpreter.toObject(scriptContent);
					} catch (Exception e) {
						Utils.err(getApplicationContext(), e);
					}
				}
			});
			interpreter.setPriority(Thread.MAX_PRIORITY);
			interpreter.start();
			creator=new ModPECreator(this,f,script);
			script=null;//to save memory
		} catch (Throwable e) {
			Utils.err(this,e);
		}
	}
	protected LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setOrientation(LinearLayout.VERTICAL);
		
		TextView title=new TextView(this);
		title.setText(getString(string.MEM_title$1)+" "+new File(getIntent().getData().getPath()).getName()+getString(string.MEM_title$2));
		ret.addView(title, Utils.flatParams);
		
		Button viewRaw=new Button(this);
		viewRaw.setText(string.MEM_viewScriptRaw);
		viewRaw.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){viewRaw();}});
		ret.addView(viewRaw, Utils.wrapParams);
		
		Button viewTranslated=new Button(this);
		viewTranslated.setText(string.MEM_viewScriptAI);
		viewTranslated.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){viewTranslated();}});
		
		return ret;
	}
	protected void viewTranslated() {
		ScrollView sv=new ScrollView(this);
		TextView tv=new TextView(this);
		tv.setText(creator.toString());
		sv.addView(tv, Utils.flatParams);
		setContentView(sv);
		this.backToMain=true;
		Toast.makeText(this, string.MEM_viewScript_back, Toast.LENGTH_SHORT).show();
	}
	protected void viewRaw() {
		ScrollView sv=new ScrollView(this);
		TextView ll=new TextView(this);
		ll.setText(creator.toRawString());
		sv.addView(ll, Utils.flatParams);
		setContentView(sv);
		this.backToMain=true;
		Toast.makeText(this, string.MEM_viewScript_back, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mod_editor_main, menu);
		return true;
	}
	@Override
	public void onBackPressed(){
		if(this.backToMain){
			setContentView(getLayout());
			this.backToMain=false;
		}
		else super.onBackPressed();
	}
}
