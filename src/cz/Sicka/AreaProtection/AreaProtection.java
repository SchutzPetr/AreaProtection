package cz.Sicka.AreaProtection;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cz.Sicka.AreaProtection.API.AreaProtectionManager;
import cz.Sicka.AreaProtection.Chunks.ChunkAPManager;
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

public class AreaProtection extends JavaPlugin {
	private static Logger log = Logger.getLogger("Minecraft");
	private static AreaProtection plugin;
	public int Version = 1;
	private ConfigurationManager c;
	private AreaProtectionManager apm;
	private static Debug deb;
	private LangConfiguration langc;

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
		ChunkAPManager.init();
		deb = new Debug();
		
		c = new ConfigurationManager(plugin);
		apm = new AreaProtectionManager();
		
		getCommand("areaprotection").setExecutor(new AreaProtectionCommandManager());
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreakeEvent(), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new PlayerInteract(), this);
	}
	
	@Override
	public void onDisable(){
		c.saveFiles();
		deb.save();
		langc.save();
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
	
	public LangConfiguration getLangConfiguration(){
		return langc;
	}
	/*
	public ChunkAPManager getChunkAPManager() {
		return chapm;
	}*/
}
