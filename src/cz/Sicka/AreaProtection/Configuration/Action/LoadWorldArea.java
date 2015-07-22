package cz.Sicka.AreaProtection.Configuration.Action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.WorldArea;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.FlagsMap;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class LoadWorldArea {
	private Map<String, Configuration> worldConfig = new HashMap<String, Configuration>();
	private AreaProtection plugin;
	
	public LoadWorldArea(AreaProtection instance){
		this.plugin = instance;
		File folder = new File(this.plugin.getDataFolder(), "WorldConfigurations");
		if (!folder.isDirectory()) {
			folder.mkdirs();
        }
		for(World world : AreaProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(folder, world.getName() + "_worldconfiguration" + ".yml");
			boolean newFile = false;
			if(!worldFile.exists()){
				try {
					worldFile.createNewFile();
					newFile = true;
				} catch (IOException e) {
					AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
					AreaProtection.LogMessage(Level.SEVERE, Lang.ErrorInCreatingFile + worldFile.getName());
					AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
					e.printStackTrace();
				}
			}
			try{
				Configuration c = new Configuration(worldFile);
				if(newFile) {
					c.getConfig().set("Version", AreaProtection.getInstance().Version);
					c.getConfig().set("Options.World", world.getName());
					c.getConfig().set("Options.WorldAreaName", world.getName());
					c.getConfig().set("Options.Owner", "Nature");
					c.getConfig().createSection("Options.Flags");
					
					c.saveConfig();
				}
				this.worldConfig.put(world.getName(), c);
			} catch(Exception e){
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				AreaProtection.LogMessage(Level.SEVERE, Lang.ErrorInLoadingSaveFile + world.getName());
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				e.printStackTrace();
			}
		}
		
		loadAllWorldAreas();
	}
	
	private void loadAllWorldAreas() {
		for(String world : this.worldConfig.keySet()){
			loadWorldAreas(this.worldConfig.get(world));
		}	
	}

	public void loadWorldAreas(Configuration c){
		for(String areaName : c.getConfig().getConfigurationSection("Options").getKeys(false)){
			loadWorldArea(areaName, c);
		}
	}
	
	public void loadWorldArea(String areaName, Configuration c){
		try{
			String worldName = c.getConfig().getString("Options.World", "world");
			String worldAreaName = c.getConfig().getString("Options.WorldAreaName", "world");
			String worldAreaOwner = c.getConfig().getString("Options.Owner", "Nature");
			WorldArea worldarea = new WorldArea(worldName, worldAreaName, worldAreaOwner);
			
			FlagsMap areaFlags = new FlagsMap();
			for(String aflags : c.getConfig().getConfigurationSection("Options.Flags").getKeys(false)){
				Flag f = FlagManager.getFlag(aflags);
				areaFlags.addFlag(f, c.getConfig().getBoolean("Options.Flags." + aflags ));
			}
			worldarea.setAreaFlags(areaFlags);
			Manager.addWorldArea(worldarea);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public Map<String, Configuration> getSaveWorldAreasFiles() {
		return worldConfig;
	}
}
