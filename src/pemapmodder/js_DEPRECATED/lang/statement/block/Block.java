/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED.lang.statement.block;

import android.os.Bundle;
import pemapmodder.js_DEPRECATED.lang.statement.Statement;

public abstract class Block extends Statement {
	public Statement[] statements={};

	public static Statement[] createUpon(String[] def, Bundle[] content) throws Exception {
		//switch block keyword
		if(def[1].equals("try"))
			return TryBlock.createUpon(def,content);
		return null;
	}
}
