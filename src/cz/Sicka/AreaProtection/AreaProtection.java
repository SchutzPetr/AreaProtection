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
import cz.Sicka.AreaProtection.Configuration.Conversion.ConversionManager;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Listeners.BlockBreakListener;
import cz.Sicka.AreaProtection.Listeners.BlockPlaceListener;
import cz.Sicka.AreaProtection.Listeners.BucketListener;
import cz.Sicka.AreaProtection.Listeners.EndermanPickupListener;
import cz.Sicka.AreaProtection.Listeners.EntityDamageListener;
import cz.Sicka.AreaProtection.Listeners.ExplosionListener;
import cz.Sicka.AreaProtection.Listeners.FireListener;
import cz.Sicka.AreaProtection.Listeners.FlowListener;
import cz.Sicka.AreaProtection.Listeners.InteractListener;
import cz.Sicka.AreaProtection.Listeners.LoginLogoutListener;
import cz.Sicka.AreaProtection.Listeners.MoveListener;
import cz.Sicka.AreaProtection.Listeners.PistonListener;
import cz.Sicka.AreaProtection.Listeners.SpawnListener;
import cz.Sicka.AreaProtection.Listeners.TeleportListener;
import cz.Sicka.AreaProtection.Listeners.VehicleMoveListener;
import cz.Sicka.AreaProtection.Listeners.WorldListener;
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
		new FlagManager(null);
		
		enableWorlds.add("world");
		
		Manager.Initialization();
		deb = new Debug();
		
		c = new ConfigurationManager(plugin);
		apm = new AreaProtectionManager();
		
		getCommand("areaprotection").setExecutor(new AreaProtectionCommandManager());
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(), this);
		pm.registerEvents(new BlockPlaceListener(), this);
		pm.registerEvents(new BucketListener(), this);
		pm.registerEvents(new EndermanPickupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		
		pm.registerEvents(new ExplosionListener(), this);
		pm.registerEvents(new FireListener(), this);
		pm.registerEvents(new FlowListener(), this);
		pm.registerEvents(new InteractListener(), this);
		pm.registerEvents(new LoginLogoutListener(), this);
		
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new PistonListener(), this);
		pm.registerEvents(new SpawnListener(), this);
		pm.registerEvents(new TeleportListener(), this);
		pm.registerEvents(new VehicleMoveListener(), this);
		
		pm.registerEvents(new WorldListener(), this);
		
		
		new BlockBreakListener();
		new BlockPlaceListener();
		new BucketListener();
		new EndermanPickupListener();
		new EntityDamageListener();
		
		new ExplosionListener();
		new FireListener();
		new FlowListener();
		new InteractListener();
		new LoginLogoutListener();
		
		new MoveListener();
		new PistonListener();
		new SpawnListener();
		new TeleportListener();
		new VehicleMoveListener();
		
		new WorldListener();
		
		new MainTask(this);
		new ConversionManager(this);
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

	public Debug getDebug() {
		return deb;
	}

	public LangConfiguration getLangConfiguration() {
		return langc;
	}
}
