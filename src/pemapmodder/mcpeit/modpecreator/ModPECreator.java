/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.modpecreator;

import java.io.File;
import java.io.FileOutputStream;

import pemapmodder.jscomponents.Script;
import pemapmodder.utils.Utils;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

public class ModPECreator extends Object{
	private FileOutputStream os;
	private Context ctx;
	private String script;
	private Script body;
	@Override public int hashCode(){
		throw new UnsupportedOperationException();
	}
	@Override public boolean equals(Object other){
		throw new UnsupportedOperationException();
	}
	@Override public String toString(){
		save();
		return script;
	}
	public ModPECreator(Context ctx, String path){
		try {
			this.ctx=ctx;
			os=new FileOutputStream(new File(path));
			script=Utils.readFile(ctx.getAssets().open("initer.js"));
			save();
		}catch(Throwable e){
			err(e);
		}
	}
	public ModPECreator(Context ctx, File f){
		this(ctx, f.getAbsolutePath());
	}
	public ModPECreator(Context ctx, Uri uri){
		this(ctx, uri.getPath());
	}
	/**
	 * 
	 * @param name field name
	 * @param initer field initializer
	 * @return true if adding field is successful, false if a field with the same name is already in the script
	 */
	public void save(){
		try{
			for(int i=0;i<script.length();i++){
				os.write(script.charAt(i));
			}
		}catch(Throwable e){
			err(e);
		}
	}
	public void err(Throwable e){
		Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
	}
	@Override
	public void finalize(){
		try{
			save();
		os.flush();
		os.close();
		super.finalize();
		}catch(Throwable e){
			err(e);
		}
	}
	public String toRawString() {
		save();
		return script;
	}
}