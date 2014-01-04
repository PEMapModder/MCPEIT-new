/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import pemapmodder.mcpeit.R.string;
import pemapmodder.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//@SuppressWarnings("unused")
public class StartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		init();
	}
	protected LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setOrientation(LinearLayout.VERTICAL);
		
		//Welcome//
		TextView title=new TextView(this);
		title.setText(string.Start_welcome);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
		ret.addView(title, Utils.flatParams);
		////Buttons////
		Button makerStarter=new Button(this);
		makerStarter.setText(string.Start_modMaker);
		makerStarter.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v){
				startModMaker();
			}
		});
		makerStarter.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		ret.addView(makerStarter, Utils.flatParams);
		
		return ret;
	}
	protected void startModMaker() {
		startActivity(new Intent(this,ModMakerFileChooser.class));
	}
	public void init(){
		try{
			File f=new File(Utils.FILE_PATH);
			if(!(f.isFile())){//first time
				f.delete();
				OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f));
				osw.write("1");
				osw.close();
			}else{
				OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f));
				Integer i=Integer.getInteger(Utils.readFile(f));
				if(i==null){//naughty
					Toast.makeText(this, string.Start_naughtyInit, Toast.LENGTH_LONG).show();
					osw.close();
					f.delete();
					init();
				}
				osw.write(Integer.toHexString(i+1));
				osw.close();
			}
		}catch(Throwable e){
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
