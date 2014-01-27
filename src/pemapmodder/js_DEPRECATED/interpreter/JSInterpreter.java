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

package pemapmodder.js_DEPRECATED.interpreter;

import pemapmodder.StrUtils;
import pemapmodder.js_DEPRECATED.JSR.Exceptions.IDs;
import pemapmodder.js_DEPRECATED.exception.JSException;
import pemapmodder.js_DEPRECATED.lang.ModScript;
import pemapmodder.js_DEPRECATED.lang.comment.Comment;
import pemapmodder.js_DEPRECATED.lang.function.Function;
import pemapmodder.js_DEPRECATED.lang.statement.Statement;
import android.os.Bundle;

public class JSInterpreter {
	public static final String BUNDLE_FUNCTION_NAME = "pemapmodder.js.lang.function.name";
	public static final String BUNDLE_FUNCTION_PARAMS = "pemapmodder.js.lang.function.params";
	public static final String BUNDLE_FUNCTION_BODY = "pemapmodder.js.lang.function.body";
	public static final String BUNDLE_FUNCTION_OFFSET_START = "pemapmodder.js.lang.function.offset.start";
	public static final String BUNDLE_FUNCTION_OFFSET_END = "pemapmodder.js.lang.function.offset.end";
	public static ModScript toObject(String inContent) throws Exception{
		String content=new String(inContent);
		content=cleanLines(cleanSpaces(cleanStarSlashComments(cleanDoubleSlashComments(content))));
		if(content==null)return null;
		Bundle[] fxBundles=findFunctions(content);
		Function[] functions={};
		for(int i=0;i<fxBundles.length;i++)
			functions[i]=toFunction(fxBundles[i]);
		Statement[] initStatements=findInitStatements(content,fxBundles);
		Comment[] comments=findComments(inContent);
		return ModScript.createFromObjects(comments, initStatements, functions);
	}
	private static Comment[] findComments(String inContent) {
		String[] lines=inContent.split("\n");
		Comment[] result={};
		for(int i=0;i<lines.length;i++){
			Comment c=Comment.createUpon(lines[i]);
			if(c!=null)result[result.length]=c;
		}
		return result;
	}
	private static Statement[] findInitStatements(String content, Bundle[] fxBundles)throws Exception{
		for(int i=0;i<fxBundles.length;i++)
			content=StrUtils.cutString(content, fxBundles[i].getInt(BUNDLE_FUNCTION_OFFSET_START), fxBundles[i].getInt(BUNDLE_FUNCTION_OFFSET_END+1));
		String[] sentences=content.split(";");
		Statement[] ret={};
		for(int i=0;i<sentences.length;i++){
			Statement s=Statement.createUpon(sentences[i],true);
			if(s!=null)ret[ret.length]=s;
		}
		return ret;
	}
	private static Function toFunction(Bundle bundle) throws Exception {
		return Function.createUpon(bundle);
	}
	private static Bundle[] findFunctions(String script) throws Exception {
		Bundle[] result={};
		if(!script.contains("function "))return result;//straightforward skips an empty script or no-condition script
		int[] o=StrUtils.findOccurrences(script, "function");//forgot why it is `o`
		Bundle[] functions={};
		for(int i=0;i<o.length;i++){
			String fxName=StrUtils.substrExc(script, o[i]+9, "(");
			String[] params=StrUtils.substrExc(script, o[i]+fxName.length()+10, ")").split(",");
			int offset=o[i]+9+fxName.length()+1+StrUtils.substrInc(script, o[i]+fxName.length()+10, "){").length();
			int quoteStatus=0;
			int braces=1;
			String body=null;
			int j=offset;
			for(j=offset;j<script.length()-offset;j++){
				if(script.charAt(j)=='\\'&&script.charAt(j+1)=='\''){
					if(quoteStatus==0)
						quoteStatus=1;
					if(quoteStatus==1)
						quoteStatus=0;
				}
				if(script.charAt(j)=='\\'&&script.charAt(j+1)=='\"'){
					if(quoteStatus==0)
						quoteStatus=2;
					if(quoteStatus==2)
						quoteStatus=0;
				}
				if(script.charAt(j)=='{'&&quoteStatus==0)
					braces++;
				if(script.charAt(j)=='}'&&quoteStatus==0){
					braces--;
					if(braces==0){//ends
						body=script.substring(offset, j);
						break;
					}
				}
			}
			if(body==null)throw JSException.getException(IDs.BRACE_NOT_CLOSED,
					"non-closed function body braces for "+script.substring(o[i],offset-1));
			Bundle function=new Bundle();
			function.putString(BUNDLE_FUNCTION_NAME, fxName);
			function.putStringArray(BUNDLE_FUNCTION_PARAMS, params);
			function.putString(BUNDLE_FUNCTION_BODY, body);
			function.putInt(BUNDLE_FUNCTION_OFFSET_START, o[i]);
			function.putInt(BUNDLE_FUNCTION_OFFSET_END, j);
			functions[functions.length]=function;
		}
		
		return functions;
	}
	private static String cleanLines(String script) {
		return script.replace("\n", "");
	}
	private static String cleanSpaces(String script) {
		script.replace("\t", " ");
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
				throw JSException.getException(IDs.INVALID_TOKEN_GENERIC, "\"*/\"");
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
