package pemapmodder.js.lang.comment;

public class FullLineComment extends Comment {

	public String comment="";
	public FullLineComment(String comment){
		this.comment=comment;
	}
	@Override
	public String toRawString() {
		return "//"+comment+"\n";
	}

}
