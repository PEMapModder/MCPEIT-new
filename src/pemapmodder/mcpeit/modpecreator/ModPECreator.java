/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.modpecreator;

import java.io.File;
import java.io.FileOutputStream;

import pemapmodder.mcpeit.modpecreator.jscomponents.Statement;
import pemapmodder.utils.Utils;
import android.content.Context;
import android.widget.Toast;

public class ModPECreator extends Object{
	private FileOutputStream os;
	private Context ctx;
	private String script;
	@Override public int hashCode(){
		throw new UnsupportedOperationException();
	}
	@Override public boolean equals(Object other){
		throw new UnsupportedOperationException();
	}
	@Override public String toString(){
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
	/**
	 * 
	 * @param name field name
	 * @param initer field initializer
	 * @return true if adding field is successful, false if a field with the same name is already in the script
	 */
	public boolean addField(String name, String initer){
		script="var "+name+"="+initer+";";
		return true;
	}
	public void addInitStatement(String fx, String[] params){
		String statement=fx+"(";
		for(int i=0;i<params.length;i++){
			statement+=params[i]+",";
		}
		addInitStatement(statement.substring(0, statement.length()-2)+");");
	}
	public void addInitStatement(String statement){
		script=statement+'\n'+script;
	}
	public void addFunctionStatement(String fx, Statement statement){
		String text=statement.toString();
	}
	public void save(){
		try{
			for(int i=0;i<script.length();i++){
				os.write(script.charAt(i));
			}
		}catch(Throwable e){
			err(e);
		}
	}
	protected void err(Throwable e){
		Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
	}
	@Override protected void finalize() throws Throwable{
		save();
		os.flush();
		os.close();
		super.finalize();
	}
}