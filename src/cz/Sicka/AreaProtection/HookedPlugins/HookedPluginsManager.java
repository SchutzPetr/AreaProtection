package cz.Sicka.AreaProtection.HookedPlugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.HookedPlugins.Plugins.HolographicDisplaysHookedPlugin;
import cz.Sicka.AreaProtection.HookedPlugins.Plugins.VaultHookedPlugin;

public class HookedPluginsManager {
	private AreaProtection plugin;
	
	public static List<Plugin> pluginList;
	private VaultHookedPlugin vault;
	private HolographicDisplaysHookedPlugin hdp;
	
	
	public HookedPluginsManager(AreaProtection instance){
		plugin = instance;
		pluginList = new ArrayList<Plugin>();
		vault = new VaultHookedPlugin();
		hdp = new HolographicDisplaysHookedPlugin();
		printPluginList();
		
	}
	
	public static void printPluginList(){
		if(pluginList.size() != 0 && pluginList != null){
			for (int i = 0; i < pluginList.size(); i++) {
				String str = "&8";
				str = str + pluginList.get(i).getName();
				//MessageManager.LogMessage(Level.INFO, Lang.Successfulhooked + str);
			}
		}
	}
	
	public List<Plugin> getPluginList() {
		return pluginList;
	}
	
	public VaultHookedPlugin getVault(){
		return vault;
	}
	
	public HolographicDisplaysHookedPlugin getHolographicDisplays(){
		return hdp;
	}
}