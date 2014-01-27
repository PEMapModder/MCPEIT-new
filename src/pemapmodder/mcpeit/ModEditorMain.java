/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.io.File;

import pemapmodder.Utils;
import pemapmodder.mcpeit.R.string;
import android.app.Activity;
import android.content.Intent;
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

	public static final String JS_EXCEPTION_MSG = "pemapmodder.mcpeit.ModEditorMain.jsExceptionMsg";
	public static final String JS_EXCEPTION_PATH = "pemapmodder.mcpeit.ModEditorMain.jsExceptionPath";
	protected boolean backToMain=false;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
	}
	protected LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setOrientation(LinearLayout.VERTICAL);
		//Title TextView
		TextView title=new TextView(this);
		title.setText(getString(string.MEM_title$1)+" "+new File(getIntent().getData().getPath()).getName()+getString(string.MEM_title$2));
		ret.addView(title, Utils.flatParams);
		//DefBlock Button
		Button defBlock=new Button(this);
		defBlock.setText(string.MEM_defineNewBlock);
		defBlock.setOnClickListener(new OnClickListener(){@Override public void onClick(View v){
			defineBlock();
		}});
		ret.addView(defBlock,Utils.flatParams);
		
		return ret;
	}
	protected void defineBlock() {
		// TODO Auto-generated method stub
		
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
}
