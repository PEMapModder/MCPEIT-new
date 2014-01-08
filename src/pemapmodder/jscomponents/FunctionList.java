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
