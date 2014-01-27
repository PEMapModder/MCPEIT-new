/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder;

public class NameAndId {
	public String name;
	public int id;
	public NameAndId(String name,Integer id){
		this.name=name;
		this.id=((id==null)?-1:id);
	}
	public String getName(){
		return name;
	}
	public int getId(){
		return id;
	}
}
