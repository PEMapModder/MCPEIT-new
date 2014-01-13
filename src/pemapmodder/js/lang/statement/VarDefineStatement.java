/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.statement;

import pemapmodder.js.lang.var.VarSpace;

public class VarDefineStatement extends Statement {

	String name;
	VarSpace defaultValue;
	public VarDefineStatement(String varName, String inValue) {
		this.name=varName;
		this.defaultValue=VarSpace.createUpon(inValue);
	}

	@Override
	public String toRawString() {
		return "var "+this.name+"="+this.defaultValue.toRawString();
	}
	
}
