/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

/*
 * This is the class I worked hardest on. Please give me credit when using this.
 * 
 * A very stupid javascript interpreter though.
*/

package pemapmodder.js.interpreter;

import pemapmodder.js.JSR.Exceptions.IDs;
import pemapmodder.js.exception.JSException;
import pemapmodder.js.lang.ModScript;

public class JSInterpreter {
	public static ModScript toObject(String content) throws Exception{
		content=cleanLines(cleanSpaces(cleanStarSlashComments(cleanDoubleSlashComments(content))));
		if(content==null)return null;
		return null;//TODO replace this
	}
	private static String cleanLines(String script) {
		return script.replace("\n", "")+"\n";//add new line at the end
	}
	private static String cleanSpaces(String script) {
		while(script.contains("  "))
			script.replace("  ", " ");
		for(int i=1;i<script.length()-1;i++){
			if(script.charAt(i)!=' ')continue;
			boolean previousIsVarChar=false;
			for(int j=0;j<varChars.length;j++){
				if(script.charAt(i-1)==varChars[j]){
					previousIsVarChar=true;
					break;
				}
			}
			if(!previousIsVarChar)continue;
			boolean nextIsVarChar=false;
			for(int k=0;k<varChars.length;k++){
				if(script.charAt(i+1)==varChars[k]){
					nextIsVarChar=true;
					break;
				}
			}
			if(previousIsVarChar&&nextIsVarChar)continue;
			script=script.substring(0,i)+script.substring(i+1);
			i--;
		}
		return script;
	}
	public final static char[] varChars={
		'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','$','_'
	};
	private static String cleanDoubleSlashComments(String script)throws Exception{
		if(script==null)return null;
		script.replace('\r', '\n');
		String[] lines=script.split("\n");
		for(int i=0;i<lines.length;i++){
			if(2>1){//delete // comments
				if(lines[i].contains("//")){//just to optimize efficiency, avoid calling the loop if // is not contained
					int isInString=0;//0 for empty, 1 for \', 2 for \"
					for(int j=0;j<lines[i].length();j++){//avoid // in "" or ''
						char c=lines[i].charAt(j);
						if(c=='\"'&&lines[i].charAt(j-1)!='\\'){
							if(isInString==2){
								isInString=0;
							}
							else if(isInString==0){//first time in my life found `else if` so useful
								isInString=2;
							}
						}
						if(c=='\''&&lines[i].charAt(j-1)!='\\'){
							if(isInString==1){
								isInString=0;
							}
							else if(isInString==0){//second time in my life found `else if` so useful
								isInString=1;
							}
						}
						if(c=='/'&&isInString==0&&lines[i].charAt(j+1)=='/'){
							lines[i]=lines[i].substring(0, j);
							break;
						}
					}
					
				}
			}
		}
		String result="";
		for(int i=0;i<lines.length;i++){
			result+=(lines[i]+"\n");
		}
		return result.substring(0,result.length()-1);
	}
	private static String cleanStarSlashComments(String script) throws Exception{
		String out="";
		boolean isInComment=false;
		int isInQuote=0;//0 for free, 1 for \', 2 for \"
		int recordedStartCommentOffset=0;
		for(int i=0;i<script.length();i++){
			if(script.charAt(i)=='/'&&script.charAt(i+1)=='*'&&!isInComment&&isInQuote==0){
				isInComment=true;
				recordedStartCommentOffset=i;
			}
			else if(script.charAt(i)=='*'&&script.charAt(i+1)=='/'&&!isInComment&&isInQuote==0)
				throw JSException.getException(IDs.INVALID_TOKEN_GENERIC, "\"*/\"")[0];
			else if(script.charAt(i)!='\\'&&script.charAt(i+1)=='\''&&!isInComment&&isInQuote==0)
				isInQuote=1;
			else if(script.charAt(i)!='\\'&&script.charAt(i+1)=='\''&&!isInComment&&isInQuote==1)
				isInQuote=0;
			else if(script.charAt(i)!='\\'&&script.charAt(i+1)=='\"'&&!isInComment&&isInQuote==0)
				isInQuote=2;
			else if(script.charAt(i)!='\\'&&script.charAt(i+1)=='\"'&&!isInComment&&isInQuote==2)
				isInQuote=0;
			else if(script.charAt(i)=='*'&&script.charAt(i+1)=='/'&&isInComment){//isInQuote must be 0 or program error
				isInComment=false;
				out=out.substring(0,recordedStartCommentOffset)+out.substring(i+2);
			}
		}
		return out;
	}
}
