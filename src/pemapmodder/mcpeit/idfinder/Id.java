/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.idfinder;

public class Id {
	private int id,damage;
	public Id(int id, int damage)throws IndexOutOfBoundsException{
		if(id<0||id>15)throw new IndexOutOfBoundsException();
		this.id=id;
		this.damage=damage;
	}
	public int getId(){
		return this.id;
	}
	public int getDamage(){
		return this.damage;
	}
}
