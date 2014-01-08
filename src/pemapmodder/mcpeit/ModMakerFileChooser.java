/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit;

import java.io.File;

import pemapmodder.mcpeit.R.string;
import pemapmodder.mcpeit.modpecreator.ModPECreator;
import pemapmodder.utils.Utils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ModMakerFileChooser extends Activity {
	protected static final int CHOOSE_FILE = 9542;
	protected static final int NEW_FILE_EDITTEXT_ID = 6529;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooser);
		setupActionBar();
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mod_maker_file_chooser, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	protected LinearLayout getLayout(){
		LinearLayout ret=new LinearLayout(this);
		ret.setOrientation(LinearLayout.VERTICAL);
		
		//choose file button//
		Button chooseFile=new Button(this);
		chooseFile.setText(string.MMFC_chooseFile);
		chooseFile.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v){
				chooseFile();
			}
		});
		ret.addView(chooseFile, Utils.flatParams);
		
		//create new file layout
		LinearLayout newFile=new LinearLayout(this);
		newFile.setOrientation(LinearLayout.HORIZONTAL);
		if(true){
			Button create=new Button(this);
			create.setText(string.MMFC_newFile);
			create.setOnClickListener(new OnClickListener(){
				@Override public void onClick(View v){
					create(((EditText)findViewById(NEW_FILE_EDITTEXT_ID)).getText().toString());
				}
			});
			newFile.addView(create, Utils.wrapParams);
		}
		if(true){
			EditText filename=new EditText(this);
			filename.setId(NEW_FILE_EDITTEXT_ID);
			filename.setHint(string.MMFC_filename);
			newFile.addView(filename, Utils.wrapParams);
		}
		
		ret.addView(newFile, Utils.flatParams);
		
		return ret;
	}
	protected void create(String name) {
		File f=new File(Utils.getAppDir().getAbsolutePath()+"/scripts/"+name+".js");
		new ModPECreator(this, f).finalize();
		startActivity(new Intent(this,ModEditorMain.class).setData(Uri.fromFile(f)));
	}
	protected void chooseFile() {
		Intent i=new Intent();
		i=i.setType("js");
		startActivityForResult(i, CHOOSE_FILE);
	}
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(data!=null&&resultCode==RESULT_OK){
			switch(requestCode){
			case CHOOSE_FILE:
				startActivity(new Intent(this, ModEditorMain.class).setData(data.getData()));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}