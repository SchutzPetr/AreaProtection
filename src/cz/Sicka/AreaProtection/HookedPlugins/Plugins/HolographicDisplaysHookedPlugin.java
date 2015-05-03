package cz.Sicka.AreaProtection.HookedPlugins.Plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import cz.Sicka.AreaProtection.HookedPlugins.HookedPluginsManager;

public class HolographicDisplaysHookedPlugin {
	private static boolean isholdisplays;

	public HolographicDisplaysHookedPlugin(){
		CheckHolographicDisplays();
	}
	
	private static void CheckHolographicDisplays(){
    	final PluginManager pm = Bukkit.getServer().getPluginManager();
    	Plugin hpl = pm.getPlugin("HolographicDisplays");
    	
    	if(hpl != null && hpl.isEnabled()){
    		isholdisplays = pm.getPlugin("HolographicDisplays") != null;
    		HookedPluginsManager.pluginList.add(hpl);
    	}
	}

	public boolean isHolographicDisplays() {
		return isholdisplays;
	}
}
