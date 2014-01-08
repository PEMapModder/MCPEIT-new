/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.idfinder;

import pemapmodder.utils.Utils;
import android.app.Activity;

public class IDFinder {
	private String[][][] names;

	public IDFinder(Activity a){
		names=Utils.getNames(a);
	}
	/**
	 * 
	 * @param name name to search for
	 * @return an array of Id objects if matches are found, or null if no matches are found
	 */
	public Id[] getId(String name)throws Exception{
		Id[] ret={};
		for (int id = 0; id < names.length; id++) {
			for (int damage = 0; damage < names[id].length; damage++) {
				for (int seq = 0; seq < names[id][damage].length; seq++) {
					if (names[id][damage][seq].equals(name)) {
						ret[ret.length]=new Id(id, damage);
					}
				}
			}
		}
		if(ret.length==0)return null;
		return ret;
	}
}
