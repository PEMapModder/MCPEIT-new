/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder;

public class StrUtils extends Utils {
	/**
	 * 
	 * @param str
	 * @param startOffset
	 * @param end
	 * @return
	 */
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
	/**
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substrExc(String str, int start, String end){
		String result=substrInc(str,start,end);
		if(result==null)return null;
		return result.substring(0, result.length()-1-end.length());
	}
	/**
	 * @param str
	 * @param sub
	 * @return offsets of the occurrences in an int array
	 */
	public static int[] findOccurrences(String str, String sub){
		int[] ret={};
		for(int i=0;i<str.length()-sub.length()+1;i++){
			if(str.substring(i, i+sub.length()).equals(sub))
				ret[ret.length]=i;
		}
		return ret;
	}
	/**
	 * Cut a string's substring with start and end offsets provided
	 * @param str 
	 * @param pause offset starting to be cut
	 * @param resume start offset of the second piece of cut string
	 */
	public static String cutString(String str,int pause,int resume){
		return str.substring(0, pause)+str.substring(resume);
	}
}
