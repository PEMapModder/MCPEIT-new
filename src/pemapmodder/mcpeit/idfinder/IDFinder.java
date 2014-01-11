/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.mcpeit.idfinder;

import java.util.Locale;

import pemapmodder.utils.Utils;
import android.app.Activity;
import android.content.Context;

public class IDFinder {
	private String[][][] names;
	public Context savedContext;

	public IDFinder(Activity a){
		names=Utils.getNames(a);
		savedContext=a;
	}
	/**
	 * 
	 * @param name name to search for
	 * @return an array of Id objects if matches are found, or null if no matches are found
	 */
	public Id[] getIds(String inName)throws Exception{
		String name=new String(inName.toLowerCase(Locale.ENGLISH));
		Id[] ret={};
		for (int id = 0; id < names.length; id++) {
			for (int damage = 0; damage < names[id].length; damage++) {
				for (int seq = 0; seq < names[id][damage].length; seq++) {
					String thisName=names[id][damage][seq].toLowerCase(Locale.ENGLISH);
					if(Utils.compare(thisName,name)){
						ret[ret.length]=new Id(id,damage,savedContext);
						break;
					}
				}
			}
		}
		return ret.length==0?null:ret;
	}
}
