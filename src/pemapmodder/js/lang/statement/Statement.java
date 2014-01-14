/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.statement;

import android.os.Bundle;
import pemapmodder.js.lang.JSLang;
import pemapmodder.js.lang.statement.block.Block;

public abstract class Statement extends JSLang{
	public abstract String toRawString();
	public final static SpecialStatement REQUIRE_ANOTHER_BLOCK=
			new SpecialStatement("REQUIRE_ANOTHER_BLOCK");
	public static Statement createUpon(String string, boolean isIniter) {
		// TODO Auto-generated method stub
		if(string.substring(0,4)=="var "){//VarDefineStatement
			String[] args=string.substring(4).split("=");
			if(isIniter)
				return new FieldDefineStatement(args[0],string.substring(4+args[0].length()));
			return new VarDefineStatement(args[0],string.substring(4+args[0].length()));
		}
		return null;
	}

	public static Statement[] createUpon(String[] inStr, Bundle[] blockRes)throws Exception{
		final String savedBlock="MCPEIT_SAVED_BUNDLE_BLOCK";
		int blockId=0;
		Statement[] ret={};
		for(int i=0;i<inStr.length;i++){
			Statement test=createUpon(inStr[i],false);
			if(test!=null){
				ret[ret.length]=test;
				continue;
			}
			if(savedBlock.equals(inStr[i].substring(inStr[i].length()-savedBlock.length()))){
				Statement r=null;
				Bundle[] give={};
				i--;
				String[] pass={};
				while(!(r instanceof Block)){
					i++;
					give[give.length]=blockRes[blockId];
					blockId++;
					pass[pass.length]=inStr[i].substring(0, inStr[i].length()-savedBlock.length());
					r=Block.createUpon(pass, give);
				}
				ret[ret.length]=r;
				blockId++;
			}
		}
	}
}
