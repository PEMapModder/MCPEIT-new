/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js.lang.comment;

public class AnnotationComment extends FullLineComment {

	public String name;
	public AnnotationComment(String name, String string) {
		super(string);
		this.name=name;
	}
	@Override
	public String toRawString() {
		return "//@"+name+" "+comment+"\n";
	}
	
}
