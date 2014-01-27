/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED.lang.statement.block;

import pemapmodder.js_DEPRECATED.lang.function.Function;
import pemapmodder.js_DEPRECATED.lang.statement.Statement;
import android.os.Bundle;

public class TryCatchBlock extends TryBlock {
	Statement[] tryer;
	String catchErrVarName;
	Statement[] catcher;
	public TryCatchBlock(Bundle tryer, String catchErrVarName, Bundle catcher) throws Exception {
		String[] passTryer={tryer.getString(Function.BUNDLE_CONTENT)};
		this.tryer=Statement.createUpon(passTryer, new Bundle[0]);
		this.catchErrVarName=catchErrVarName;
		String[] passCatcher={catcher.getString(Function.BUNDLE_CONTENT)};
		this.catcher=Statement.createUpon(passCatcher,new Bundle[0]);
	}

	@Override
	public String toRawString() {
		// TODO Auto-generated method stub
		return null;
	}

}
