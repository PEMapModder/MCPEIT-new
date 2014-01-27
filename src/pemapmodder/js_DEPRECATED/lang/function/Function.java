/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED.lang.function;

import android.os.Bundle;
import pemapmodder.Utils;
import pemapmodder.js_DEPRECATED.exception.JSException;
import pemapmodder.js_DEPRECATED.interpreter.JSInterpreter;
import pemapmodder.js_DEPRECATED.lang.JSLang;
import pemapmodder.js_DEPRECATED.lang.statement.Statement;

public class Function extends JSLang{
	public final static String BUNDLE_SEQUENCE="bundle.sequence";
	public final static String BUNDLE_CONTENT="bundle.content";
	public String myName;
	public String[] params;
	public Statement[] statements;
	public Function(String name, String[] params, String inBody)throws Exception{
		try{myName=name;
		this.params=params;
		String body=new String(inBody);
		Bundle[] blocks={};
		int quoteStatus=0;//0 for free, 1 for \', 2 for \"
		//unclosed braces exceptions should be caught from JSInterpreter.class
		int braces=0;//we will handle nested blocks later
		int savedOffset=0;
		for(int o=0;o<body.length();o++){//extract blocks
			if(body.charAt(o)=='\"'){
				if(quoteStatus==2&&body.charAt(o-1)!='\\')
					quoteStatus=0;
				if(quoteStatus==0)
					quoteStatus=2;
			}
			if(body.charAt(o)=='\''){
				if(quoteStatus==1&&body.charAt(o-1)!='\\')
					quoteStatus=0;
				if(quoteStatus==0)
					quoteStatus=1;
			}
			if(body.charAt(o)=='{'&&quoteStatus==0){
				braces++;
				savedOffset=o;
			}
			if(body.charAt(o)=='}'&&quoteStatus==0){
				braces--;
				if(braces==0){
					int offset=blocks.length;
					blocks[offset]=new Bundle();
					blocks[offset].putInt(BUNDLE_SEQUENCE, o);
					blocks[offset].putString(BUNDLE_CONTENT, body.substring(savedOffset, o+1));
					body=body.substring(0,savedOffset)+"MCPEIT_SAVED_BUNDLE_BLOCK;"+body.substring(o+1);
				}
			}
		}
		//working against for(;;)
		String wB=new String(body);
		int brackets=0;
		quoteStatus=0;
		String[] ss={};
		for(int i=0;i<wB.length();i++){
			if(wB.charAt(i)=='\''&&wB.charAt(i-1)!='\\'){
				if(quoteStatus==0)quoteStatus=1;
				if(quoteStatus==1)quoteStatus=0;
			}
			if(wB.charAt(i)=='\"'&&wB.charAt(i-1)!='\\'){
				if(quoteStatus==0)quoteStatus=2;
				if(quoteStatus==2)quoteStatus=0;
			}
			if(wB.charAt(i)=='('&&quoteStatus==0)
				brackets++;
			if(wB.charAt(i)==')'&&quoteStatus==0)
				brackets--;
			if(wB.charAt(i)==';'&&quoteStatus==0&&brackets==0){
				String s=wB.substring(0,i);
				if(s.length()!=0)
					ss[ss.length]=s;
				wB=wB.substring(i+1);
				i=-1;
			}
		}
		for(int i=0;i<ss.length;i++){
			this.statements=Statement.createUpon(ss, blocks);
		}
		}catch(Exception e){
			Bundle[] errs=JSException.readExceptionRaw(e);
			errs[errs.length]=new Bundle();
			errs[errs.length-1].putInt(JSException.BUNDLE_CODEID, -1);
			String implodedParams="";
			for(int i=0;i<this.params.length;i++){
				implodedParams+=this.params[i]+",";
			}
			errs[errs.length-1].putString(JSException.BUNDLE_DETAILS, "An error occurred at function "+myName+"("+implodedParams+")");
		}
	}
	public String toRawString(){
		String out="";
		for(int i=0;i<statements.length;i++)
			out+=(statements[i].toRawString()+"\n");
		return out;
	}
	public static Function createUpon(Bundle b) throws Exception {
		if(Utils.isInArray(
				HookFunction.BUILT_IN_HOOK_LIST,
				b.getString(JSInterpreter.BUNDLE_FUNCTION_NAME)
				)!=0)return new HookFunction(b.getString(JSInterpreter.BUNDLE_FUNCTION_NAME),
						b.getStringArray(JSInterpreter.BUNDLE_FUNCTION_PARAMS),
						b.getString(JSInterpreter.BUNDLE_FUNCTION_BODY)
						);
		return new Function(b.getString(JSInterpreter.BUNDLE_FUNCTION_NAME),
				b.getStringArray(JSInterpreter.BUNDLE_FUNCTION_PARAMS),
				b.getString(JSInterpreter.BUNDLE_FUNCTION_BODY)
				);
	}
}
