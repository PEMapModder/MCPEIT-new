/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.statement;

import pemapmodder.js.lang.JSLang;

public abstract class Statement extends JSLang{
	public abstract String toRawString();

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
}
