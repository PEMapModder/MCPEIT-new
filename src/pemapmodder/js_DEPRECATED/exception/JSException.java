/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED.exception;

import pemapmodder.js_DEPRECATED.JSR.Exceptions.ToStrings;
import android.os.Bundle;


public class JSException{
	public final static String BUNDLE_CODEID="pemapmodder.js.exception.JSException.exceptionLineCode";
	public final static String BUNDLE_DETAILS="pemapmodder.js.exception.JSException.exceptionLineDetails";
	public static Exception getException(int[] exceptionId, String[] explain){
		if(exceptionId.length!=explain.length)throw new RuntimeException();
		String msg="";
		for(int i=0;i<exceptionId.length;i++)
			msg+=("Error: (code 0x"+Integer.toHexString(exceptionId[i])+"; ToString "+ToStrings.get(exceptionId[i])+": Details:"+explain[i]+"\n");
		return new Exception(msg);
	}
	public static Exception getException(Bundle[] in){
		String msg="";
		for(int i=0;i<in.length;i++){
			msg+=("Error: (code 0x"+Integer.toHexString(in[i].getInt(BUNDLE_CODEID))+"; ToString "+ToStrings.get(in[i].getInt(BUNDLE_CODEID))+": Details:"+in[i].getString(BUNDLE_DETAILS)+"\n");
		}
		return new Exception(msg);
	}
	public static String[] readException(Exception e){
		return e.getMessage().split("\n");
	}
	public static Bundle[] readExceptionRaw(Exception e){
		String[] errs=readException(e);
		Bundle[] ret={};
		for(int i=0;i<errs.length;i++){
			Integer id=Integer.getInteger(errs[i].split(";")[0].substring(15));
			if(id==null)return null;
			String explain=errs[i].split(": Details:")[1];
			Bundle thisRet=new Bundle();
			thisRet.putInt(BUNDLE_CODEID, id);
			thisRet.putString(BUNDLE_DETAILS, explain);
			ret[ret.length]=thisRet;
		}
		return ret;
	}
	public static Exception getException(int inInt, String inStr) {
		int[] i={inInt};
		String[] str={inStr};
		return getException(i, str);
	}
}
