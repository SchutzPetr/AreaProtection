package cz.Sicka.AreaProtection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import cz.Sicka.AreaProtection.API.AreaProtectionManager;
import cz.Sicka.AreaProtection.Commands.AreaProtectionCommandManager;
import cz.Sicka.AreaProtection.Configuration.ConfigurationManager;
import cz.Sicka.AreaProtection.Configuration.Debug;
import cz.Sicka.AreaProtection.Configuration.LangConfiguration;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Listeners.BlockBreakeEvent;
import cz.Sicka.AreaProtection.Listeners.MoveListener;
import cz.Sicka.AreaProtection.Listeners.PlayerInteract;
import cz.Sicka.AreaProtection.Utils.AnsiColor;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;
import cz.Sicka.AreaProtection.Utils.Tasks.MainTask;

public class AreaProtection extends JavaPlugin {
	private static Logger log = Logger.getLogger("Minecraft");
	private static AreaProtection plugin;
	public int Version = 1;
	private ConfigurationManager c;
	private AreaProtectionManager apm;
	private static Debug deb;
	private LangConfiguration langc;
	private static List<String> enableWorlds = new ArrayList<String>();

	public static AreaProtection getInstance() {
		return plugin;
	}
	
	@Override
	public void onEnable(){
		plugin = this;
		langc = new LangConfiguration();
		new Lang();
		new AnsiColor();
		new FlagManager();
		
		enableWorlds.add("world");
		
		Manager.Initialization();
		deb = new Debug();
		
		c = new ConfigurationManager(plugin);
		apm = new AreaProtectionManager();
		
		getCommand("areaprotection").setExecutor(new AreaProtectionCommandManager());
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreakeEvent(), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new PlayerInteract(), this);
		
		new MainTask(this);
	}
	
	@Override
	public void onDisable(){
		c.saveFiles();
		deb.save();
		langc.save();
		SelectionManager.clearAll();
		Bukkit.getScheduler().cancelTasks(getInstance());
		for(Hologram h : HologramsAPI.getHolograms(getInstance())){
			h.delete();
		}
		
	}
	
	@Override
	public void onLoad(){
		
	}
	
	public static void LogMessage(Level level, String message){
		if(level == Level.SEVERE){
			deb.log(message);
		}
		log.log(level, AnsiColor.translateConsoleColorCodes(message));
	}
	
	public static void LogMessage(String message){
		LogMessage(Level.INFO, AnsiColor.translateConsoleColorCodes(message));
	}
	
	public AreaProtectionManager getAreaProtectionManager(){
		return apm;
	}
	
	public ConfigurationManager getConfigurationManager(){
		return c;
	}
	
	public static boolean isEnableWorld(String world){
		return enableWorlds.contains(world);
	}
	/*
	public ChunkAPManager getChunkAPManager() {
		return chapm;
	}*/

	public Debug getDebug() {
		return deb;
	}

	public LangConfiguration getLangConfiguration() {
		return langc;
	}
}
