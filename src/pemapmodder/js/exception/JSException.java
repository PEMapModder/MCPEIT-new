/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.exception;


public class JSException{
	public static Exception[] getException(int[] exceptionId, String[] explain){
		if(exceptionId.length!=explain.length)throw new RuntimeException();
		String msg="";
		for(int i=0;i<exceptionId.length;i++)
			msg+=(Integer.toHexString(exceptionId[i])+":"+explain[i]+"\n");
		Exception[] r={new Exception(msg)};
		return r;
	}
	public static String[] readException(Exception e){
		return e.getMessage().split("\n");
	}
	public static Exception[] getException(int inInt, String inStr) {
		int[] i={inInt};
		String[] str={inStr};
		return getException(i, str);
	}
}
