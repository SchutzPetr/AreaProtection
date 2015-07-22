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
import cz.Sicka.Core.Utils.Configuration.DefaultConfig;

public class AreaProtection extends JavaPlugin {
	private static Logger log = Logger.getLogger("Minecraft");
	private static AreaProtection plugin;
	public int Version = 1;
	private ConfigurationManager c;
	private AreaProtectionManager apm;
	private static Debug deb;
	private LangConfiguration langc;
	private static List<String> enableWorlds = new ArrayList<String>();
	private AreaProtectionDynmap dynmap;
	private BlockBreakListener blockBreak;
	private BlockPlaceListener blockPlace;
	private BucketListener bucket;
	private EndermanPickupListener endermanPickup;
	private EntityDamageListener entityDamage;
	private ExplosionListener explosion;
	private FireListener fire;
	private FlowListener flow;
	private InteractListener interact;
	private LoginLogoutListener loginLogout;
	private MoveListener move;
	private PistonListener piston;
	private SpawnListener spawn;
	private TeleportListener teleport;
	private VehicleMoveListener vehicleMove;
	private WorldListener world;

	public static AreaProtection getInstance() {
		return plugin;
	}
	
	@Override
	public void onEnable(){
		plugin = this;
		langc = new LangConfiguration();
		new Lang();
		
		DefaultConfig configuration = new DefaultConfig(plugin, "Configuration.yml");
		configuration.saveDefaultConfig();
		
		new AnsiColor();
		new FlagManager(null);
		
		enableWorlds.add("world");
		
		Manager.Initialization();
		deb = new Debug();
		
		c = new ConfigurationManager(plugin);
		apm = new AreaProtectionManager();
		
		getCommand("areaprotection").setExecutor(new AreaProtectionCommandManager());
		
		blockBreak = new BlockBreakListener();
		blockPlace = new BlockPlaceListener();
		bucket = new BucketListener();
		endermanPickup = new EndermanPickupListener();
		entityDamage = new EntityDamageListener();
		
		explosion = new ExplosionListener();
		fire = new FireListener();
		flow = new FlowListener();
		interact = new InteractListener();
		loginLogout = new LoginLogoutListener();
		
		move = new MoveListener();
		piston = new PistonListener();
		spawn = new SpawnListener();
		teleport = new TeleportListener();
		vehicleMove = new VehicleMoveListener();
		
		world = new WorldListener();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(blockBreak, this);
		pm.registerEvents(blockPlace, this);
		pm.registerEvents(bucket, this);
		pm.registerEvents(endermanPickup, this);
		pm.registerEvents(entityDamage, this);
		
		pm.registerEvents(explosion, this);
		pm.registerEvents(fire, this);
		pm.registerEvents(flow, this);
		pm.registerEvents(interact, this);
		pm.registerEvents(loginLogout, this);
		
		pm.registerEvents(move, this);
		pm.registerEvents(piston, this);
		pm.registerEvents(spawn, this);
		pm.registerEvents(teleport, this);
		pm.registerEvents(vehicleMove, this);
		
		pm.registerEvents(world, this);
		
		new MainTask(this);
		new ConversionManager(this);
		
		dynmap = new AreaProtectionDynmap(this);
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

	/**
	 * @return the dynmap
	 */
	public AreaProtectionDynmap getAreaProtectionDynmap() {
		return dynmap;
	}
}
