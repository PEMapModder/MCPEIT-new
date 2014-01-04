/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class Utils {
	/**
	 * totally wrap content
	 */
	public final static LayoutParams wrapParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	
	public final static LayoutParams flatParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	public final static String FILE_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/games/pemapmodder.mcpeit/DONTDELETEME.txt";
	/**
	 * Find the first occurrence of an object in an object array
	 * @param array array to search in
	 * @param item the object to search
	 * @return the key of the object, or -1 if not found
	 */
	public static int isInArray(Object[] array, Object item){
		for(int i=0;i<array.length;i++){
			if(array[i].equals(item)){
				return i;
			}
		}
		return -1;
	}
	/**
	 * Gets the contents in a file as a string
	 * @param file the file to read
	 * @return the contents in the file
	 * @throws Throwable in case the file is not readable
	 */
	public static String readFile(File f)throws Throwable{
		return readFile(new FileInputStream(f));
	}
	/**
	 * Gets the contents readable in an input stream as a string
	 * @param is input stream of the file
	 * @return the contents readable in the stream
	 * @throws Throwable in case the stream is not readable
	 * @see readFile(File)
	 */
	public static String readFile(InputStream is)throws Throwable{
		String ret="";
		try{
			for(int c=is.read();c!=-1;c=is.read()){
				ret+=c;
			}
		}finally{
			is.close();
		}
		return ret;
	}
	public final static String[][][] getNames(Activity a){
		String[][][] ret={};
		try{
			String c=readFile(a.getAssets().open("names.txt"));
			String[] itemsRaw=c.split("\n");
			String[] items={};
			for(int i=0;i<itemsRaw.length;i++){
				if(itemsRaw[i].charAt(0)=='#')continue;
				items[items.length]=itemsRaw[i];
			}
			String[][] itemDamages={};
			for(int i=0;i<items.length;i++){
				
				itemDamages[i]=items[i].split(";");
			}
			
			for(int id=0;id<itemDamages.length;id++){
				for(int damage=0;damage<itemDamages[id].length;damage++){
					ret[id][damage]=itemDamages[id][damage].split(",");
				}
			}
		}catch(Throwable e){
			if(e.getClass()==IndexOutOfBoundsException.class){
				Toast.makeText(a, "While trying to get name of an item, an offset outofbouds error occured", Toast.LENGTH_LONG).show();
				Log.e("pemapmodder.utils.Utils.getNames()", "IndexOutOfBoundsException");
			}
			Log.e("pemapmodder.utils.Utils.getNames()", e.toString());
			return null;
		}
		
		return ret;
	}
}
