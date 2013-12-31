/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Utils {

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

}