/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.statement.block;

import pemapmodder.js.JSR.Exceptions.IDs;
import pemapmodder.js.exception.JSException;
import pemapmodder.js.lang.function.Function;
import pemapmodder.js.lang.statement.Statement;
import android.os.Bundle;

public abstract class TryBlock extends Block{
	public abstract String toRawString();
	public static Statement createUpon(String[] def,Bundle[] content)throws Exception{
		if(def.length==1)
			return Statement.REQUIRE_ANOTHER_BLOCK;
		if(def.length==2){
			if(def[1].substring(0,5)!="catch"&&def[1].substring(0,7)!="finally")
				throw JSException.getException(IDs.TRY_BLOCK_NOT_FINALIZED, "at try block \n<code>"+content[0].getString(Function.BUNDLE_CONTENT)+"</code>\nThe above is the "+content[0].getInt(Function.BUNDLE_SEQUENCE)+"th block in the function.");
			if(def[1].split("(")[0]=="catch"){
				return new TryCatchBlock(content[0],def[1].split("(")[1].split(")")[0],content[1]);
			}
		}
	}
}
