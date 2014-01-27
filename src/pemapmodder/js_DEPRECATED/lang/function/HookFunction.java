/*
 * @Copyright (C) 2013-2014 PEMapModder
 * 
 * You may share redistributions of this software for non-commercial use as long as you indicate the original creator PEMapModder and the source https://github.com/pemapmodder/MCPEIT-new.git
 */

package pemapmodder.js_DEPRECATED.lang.function;

public class HookFunction extends Function {
	public static final String[] BUILT_IN_HOOK_LIST = {
		"useItem",
		"attackHook",
		"procCmd",
		"modTick",
		"deathHook",
		"entityAddedHook",
		"entityRemovedHook",
		"blockEvent"
	};
	public HookFunction(String name, String[] params, String body) throws Exception {
		super(name,params,body);
	}
}
