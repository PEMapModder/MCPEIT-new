/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.function;

import pemapmodder.js.lang.statement.Statement;

public class Function {
	public Statement[] statements;
	public Function() {
		
	}
	public String toRawString(){
		String out="";
		for(int i=0;i<statements.length;i++)
			out+=(statements[i].toRawString()+"\n");
		return out;
	}
}
