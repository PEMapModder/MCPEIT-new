/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.modmaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;

public class ModCreator{
	OutputStreamWriter osw;
	Activity activity;
	public ModCreator(OutputStream os,Activity a) throws Throwable{
		this.osw=new OutputStreamWriter(os);
		this.activity=a;
	}
	public ModCreator(File f,Activity a) throws Throwable{
		this(new FileOutputStream(f),a);
	}
}
