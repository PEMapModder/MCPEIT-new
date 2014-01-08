/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.jscomponents;

public class FunctionList {
	public boolean hasUseItem;
	public boolean hasAttackHook;
	public boolean hasProcCmd;
	public boolean hasModTick;
	public boolean hasDestroyBlock;
	public boolean hasDeathHook;
	public FunctionList(String... fxs){
		for(int i=0;i<fxs.length;i++){
			String f=fxs[i];
			if(f=="useItem")hasUseItem=true;
			if(f=="attackHook")hasAttackHook=true;
			if(f=="procCmd")hasProcCmd=true;
			if(f=="modTick")hasModTick=true;
			if(f=="destroyBlock")hasDestroyBlock=true;
			if(f=="deathHook")hasDeathHook=true;
		}
	}
}
