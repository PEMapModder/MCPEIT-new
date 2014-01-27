/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import pemapmodder.Utils;
import pemapmodder.mcpeit.R;
import pemapmodder.mcpeit.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//import pemapmodder.mcpeit.ModEditorMain;

public class ShowJSErrorActivity extends Activity {
	public class handleException implements UncaughtExceptionHandler{
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			Utils.err(get(), ex);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
	}
	private LinearLayout getLayout() {
		LinearLayout contentView=new LinearLayout(this);
		contentView.setOrientation(LinearLayout.VERTICAL);

		LinearLayout titleLayout=new LinearLayout(this);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView title=new TextView(this);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
		titleLayout.addView(title, Utils.wrapParams);
		
		Button dump=new Button(this);
		dump.setText(string.JSErr_dump+" "+Utils.getAppDir().getAbsolutePath()+"JSerrors.log");
		dump.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)10.5);
		dump.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v){
				dumpFile();
			}});
		titleLayout.addView(dump, Utils.wrapParams);
		
		contentView.addView(titleLayout, Utils.flatParams);
		
		ScrollView log=new ScrollView(this);
		TextView logText=new TextView(this);
		logText.setText(getString(string.JSErr_errLog));
		logText.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float)10.5);
		log.addView(logText, Utils.flatParams);
		contentView.addView(log,Utils.flatParams);
		
		return contentView;
	}
	protected void dumpFile(){
		Thread t=new Thread(new Runnable(){
			@Override public void run(){
				try{
					dump();
				}catch(Throwable e){
					Utils.err(get(), e);
				}
			}});
		t.start();
		t.setUncaughtExceptionHandler(new handleException());
		Toast.makeText(this, string.JSErr_dumpFinish, Toast.LENGTH_SHORT).show();
	}
	protected ShowJSErrorActivity get(){
		return this;
	}
	protected void dump() throws Throwable{
			File f=new File(Utils.getAppDir()+"error.log");
			String old=Utils.readFile(f);
			OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(new File(Utils.getAppDir()+"error.log")));
			osw.append(old);
			osw.append("ERROR_DUMP_START");
			osw.append(DateFormat.format("dd-MM-YYYY hh:mm:ss",
					System.currentTimeMillis())+"\n");
			osw.append("Error occurred while trying to read javascript file at \r");
//			osw.append("    "+getIntent().getStringExtra(ModEditorMain.JS_EXCEPTION_PATH)+"\n");
//			osw.append(getIntent().getStringExtra(ModEditorMain.JS_EXCEPTION_MSG));
			osw.append("\nERROR_DUMP_END\n\n");
			osw.close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_jserror, menu);
		return true;
	}
}
