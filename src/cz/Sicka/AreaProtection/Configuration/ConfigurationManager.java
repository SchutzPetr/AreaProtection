package cz.Sicka.AreaProtection.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.PlayerArea;
import cz.Sicka.AreaProtection.Area.ServerArea;
import cz.Sicka.AreaProtection.Area.WorldArea;
import cz.Sicka.AreaProtection.Area.Area.AreaType;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class ConfigurationManager {
	private Map<String, Configuration> worldsareasConfigs = new HashMap<String, Configuration>();
	private Map<String, Configuration> worldsworldConfigs = new HashMap<String, Configuration>();
	private AreaProtection plugin;
	
	public ConfigurationManager(AreaProtection plugin){
		this.plugin = plugin;
		loadWorlds();
		loadWorldConfigurations();
	}
	
	public Map<String, Configuration> getSaveAreaConfigs(){
		return worldsareasConfigs;
	}
	
	public Map<String, Configuration> getWorldConfigs(){
		return worldsworldConfigs;
	}
	
	public void saveFiles(){
		for(Configuration c : this.worldsareasConfigs.values()){
			c.saveConfig();
		}
		
		for(Configuration c : this.worldsworldConfigs.values()){
			c.saveConfig();
		}
	}
	
	public void loadWorldConfigurations(){
		File folder = new File(this.plugin.getDataFolder(), "WorldConfigurations");
		if (!folder.isDirectory()) {
			folder.mkdirs();
        }
		for(World w : AreaProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(folder, w.getName() + "_worldconfiguration" + ".yml");
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
					c.getConfig().set("Options.World", w.getName());
					c.getConfig().set("Options.DisplayName", w.getName());
					c.getConfig().createSection("Options.Flags");
					
					c.saveConfig();
				}
				this.worldsworldConfigs.put(w.getName(), c);
				FlagsMap fm = new FlagsMap();
				WorldArea wa = new WorldArea(c.getConfig().getString("Options.World", w.getName()), c.getConfig().getString("Options.DisplayName", w.getName()));
				Area a = new Area(wa);
				
				for(String aflags : c.getConfig().getConfigurationSection("Options.Flags").getKeys(false)){
					Flag f = FlagManager.getFlag(aflags);
					fm.addFlag(f, c.getConfig().getBoolean("Options.Flags." + aflags ));
				}
				a.setAreaFlags(fm);
				Manager.addWorldArea(a);
			} catch (Exception e) {
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				AreaProtection.LogMessage(Level.SEVERE, Lang.ErrorInLoadingSaveFile + w.getName());
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				e.printStackTrace();
			}
				
		}
		
	} 
	
	public void loadWorlds(){
		File dataF = this.plugin.getDataFolder();
		File worldFolder = new File(new File(dataF, "Save"), "Worlds");
		if (!worldFolder.isDirectory()) {
            worldFolder.mkdirs();
        }
		
		for(World w : AreaProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(worldFolder, "areas_" + w.getName() + ".yml");
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
					c.getConfig().createSection("Areas");
					c.saveConfig();
				}
				this.worldsareasConfigs.put(w.getName(), c);
				for(String areaName : c.getConfig().getConfigurationSection("Areas").getKeys(false)){
					int x1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowX");
					int y1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowY");
					int z1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowZ");
					
					int x2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighX");
					int y2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighY");
					int z2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighZ");
					String world = c.getConfig().getString("Areas." + areaName + ".Data.Location.World");
					
					if(AreaType.valueOf(c.getConfig().getString("Areas." + areaName + ".Data.AreaType")) == AreaType.SERVER_AREA){
						String owner = c.getConfig().getString("Areas." + areaName + ".Data.Owner");
						ServerArea sa = new ServerArea(areaName, owner, x1, y1, z1, x2, y2, z2, world);
						Area a = new Area(sa);
						
						a.setCreationDate(c.getConfig().getLong("Areas." + areaName + ".Data.CreationDate"));
						
						FlagsMap fm = new FlagsMap();
						for(String aflags : c.getConfig().getConfigurationSection("Areas." + areaName + ".Data.Flags").getKeys(false)){
							Flag f = FlagManager.getFlag(aflags);
							fm.addFlag(f, c.getConfig().getBoolean("Areas." + areaName + ".Data.Flags." + aflags ));
						}
						a.setAreaFlags(fm);
						Manager.addAreaToList(a);
						Manager.getChunkStorageManager(world).addAreaToChunkStorages(a);
					}else{
						UUID uuid = UUID.fromString(c.getConfig().getString("Areas." + areaName + ".Data.Owner"));
						PlayerArea pa = new PlayerArea(areaName, uuid, x1, y1, z1, x2, y2, z2, world);
						Area a = new Area(pa);
						
						a.setCreationDate(c.getConfig().getLong("Areas." + areaName + ".Data.CreationDate"));
						
						FlagsMap fm = new FlagsMap();
						for(String aflags : c.getConfig().getConfigurationSection("Areas." + areaName + ".Data.Flags").getKeys(false)){
							Flag f = FlagManager.getFlag(aflags);
							fm.addFlag(f, c.getConfig().getBoolean("Areas." + areaName + ".Data.Flags." + aflags ));
						}
						a.setAreaFlags(fm);
						Manager.addAreaToList(a);
						Manager.getChunkStorageManager(world).addAreaToChunkStorages(a);
					}
				}
			} catch (Exception e) {
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				AreaProtection.LogMessage(Level.SEVERE, Lang.ErrorInLoadingSaveFile + w.getName());
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				e.printStackTrace();
			}
		}
	}
}
