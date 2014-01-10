/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang;

import pemapmodder.js.interpreter.JSInterpreter;
import pemapmodder.js.lang.comment.Comment;
import pemapmodder.js.lang.function.Function;
import pemapmodder.js.lang.statement.Statement;

public class ModScript {

	private String raw;
	public Comment[] comments;
	public Statement[] initStatements;
	public Function[] functions;
	public ModScript(String content) throws Exception {
		this.raw=content;
		ModScript copy=JSInterpreter.toObject(content);
		this.comments=copy.comments;
		this.initStatements=copy.initStatements;
		this.functions=copy.functions;
	}
	//raw
	private ModScript(Comment[] com, Statement[] initer, Function[] fxs){
		this.comments=com;
		this.initStatements=initer;
		this.functions=fxs;
		this.rebuildRaw();
	}
	public static ModScript createFromObjects(Comment[] com, Statement[] initer, Function[] fxs){
		return new ModScript(com,initer,fxs);
	}
	public String getRaw(){
		return this.raw;
	}
	private void rebuildRaw(){
		this.raw="";
		for(int i=0;i<this.comments.length;i++)
			this.raw+=(this.comments[i].toRawString()+"\n");
		for(int i=0;i<this.initStatements.length;i++)
			this.raw+=(this.initStatements[i].toRawString()+"\n");
		for(int i=0;i<this.functions.length;i++)
			this.raw+=(this.functions[i].toRawString()+"\n");
	}
	
}
