/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.utils;

public class StrUtils extends Utils {
	public static String substrInc(String str, int startOffset, String end){
		String ret=str.substring(startOffset);
		String returner=null;
		for(int i=0;i<ret.length()-end.length()+1;i++){
			if(ret.substring(i, i+end.length())==end){
				returner=ret.substring(i, i+end.length());
				break;
			}
		}
		return returner;
	}
	public static String substrExc(String str, int start, String end){
		String result=substrInc(str,start,end);
		if(result==null)return null;
		return result.substring(0, result.length()-1-end.length());
	}
}
