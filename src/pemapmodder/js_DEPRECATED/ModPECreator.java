/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import pemapmodder.Utils;
import pemapmodder.js_DEPRECATED.lang.ModScript;
import android.content.Context;
//import java.io.FileInputStream;
//import pemapmodder.js.interpreter.JSInterpreter;

public class ModPECreator extends Object{
	protected Context ctx;
	protected File file;
	protected ModScript scriptObject;
	public ModPECreator(Context ctx, File f, ModScript script)throws Exception{
		this.ctx=ctx;
		this.file=f;
/*		String content="";
		FileInputStream is=new FileInputStream(f);
		for(int c=is.read();c!=-1;c=is.read())
			content+=(char)c;
		is.close();
*/
		this.scriptObject=script;
	}
	public String toRawString(){
		return this.scriptObject.getRaw();
	}
	public void save()throws Exception{
		this.file.delete();
		OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(this.file));
		osw.append(this.toRawString());
		osw.close();
	}
	@Override protected void finalize(){
		try {
			this.save();
			super.finalize();
		} catch (Throwable e) {
			Utils.err(this.ctx, e);
		}
	}
}