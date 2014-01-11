/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.idfinder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Id {
	private int id,damage;
	public Context ctx;
	public Id(int id, int damage, Context ctx)throws IndexOutOfBoundsException{
		if(id<0||id>15)throw new IndexOutOfBoundsException();
		this.id=id;
		this.damage=damage;
		this.ctx=ctx;
	}
	public int getId(){
		return this.id;
	}
	public int getDamage(){
		return this.damage;
	}
	public String getName(){
		switch(this.id){
		default:
			String msg="ID "+this.id+":"+this.damage+" name not found. Please report this.";
			Log.e(getClass().getPackage().getName()+getClass().getSimpleName(), msg);
			Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
			return null;
		}
	}
}
