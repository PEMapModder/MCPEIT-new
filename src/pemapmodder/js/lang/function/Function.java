/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.function;

import android.os.Bundle;
import pemapmodder.js.interpreter.JSInterpreter;
import pemapmodder.js.lang.JSLang;
import pemapmodder.js.lang.statement.Statement;
import pemapmodder.utils.Utils;

public class Function extends JSLang{
	private final static String BUNDLE_SEQUENCE="bundle.sequence";
	private final static String BUNDLE_CONTENT="bundle.content";
	public Statement[] statements;
	public Function(String name, String[] params, String inBody) {
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
					body=body.substring(0,savedOffset)+"SAVED_BUNDLE_BLOCK;"+body.substring(o+1);
				}
			}
		}
		//TODO split by ";"
		//TODO Subdivide nested blocks
	}
	public String toRawString(){
		String out="";
		for(int i=0;i<statements.length;i++)
			out+=(statements[i].toRawString()+"\n");
		return out;
	}
	public static Function createUpon(Bundle b) {
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
