/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.comment;

public abstract class Comment {
	public abstract String toRawString();
	public static FullLineComment createUpon(String string){
		if(string.substring(0, 2)!="//")return null;
		if(string.charAt(2)=='@')return new AnnotationComment(string.split(" ")[0].substring(3),string.split(" ", 2)[1]);
		return new FullLineComment(string.substring(2));
	}
}
