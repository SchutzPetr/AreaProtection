package cz.Sicka.AreaProtection.Configuration.Action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaPlayerFlags;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class LoadArea {
	private Map<String, Configuration> worldConfig = new HashMap<String, Configuration>();
	private AreaProtection plugin;
	
	public LoadArea(AreaProtection instance){
		this.plugin = instance;
		File dataF = this.plugin.getDataFolder();
		File worldFolder = new File(new File(dataF, "Save"), "Worlds");
		if (!worldFolder.isDirectory()) {
            worldFolder.mkdirs();
        }
		for(World world : AreaProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(worldFolder, "areas_" + world.getName() + ".yml");
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
				this.worldConfig.put(world.getName(), c);
			} catch(Exception e){
				
			}
		}
		
		loadAllAreas();
	}
	
	private void loadAllAreas() {
		for(String world : this.worldConfig.keySet()){
			loadAreas(this.worldConfig.get(world));
		}	
	}

	public void loadAreas(Configuration c){
		for(String areaName : c.getConfig().getConfigurationSection("Areas").getKeys(false)){
			loadArea(areaName, c);
		}
	}
	
	public void loadArea(String areaName, Configuration c){
		try{
			int x1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowX");
			int y1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowY");
			int z1 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.LowZ");
			
			int x2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighX");
			int y2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighY");
			int z2 = c.getConfig().getInt("Areas." + areaName + ".Data.Location.HighZ");
			
			int telx = c.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.X");
			int tely = c.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.Y");
			int telz = c.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.Z");
			
			String world = c.getConfig().getString("Areas." + areaName + ".Data.Location.World");
			
			Location teleportLocation = new Location(Bukkit.getWorld(world), telx, tely, telz);
			
			AreaType type = AreaType.valueOf(c.getConfig().getString("Areas." + areaName + ".Data.AreaType"));
			String owner = c.getConfig().getString("Areas." + areaName + ".Data.Owner");
			
			Area a = new Area(areaName, owner, type, x1, y1, z1, x2, y2, z2, world);
			
			a.setTeleportLocation(teleportLocation);
			
			a.setCreationDate(c.getConfig().getLong("Areas." + areaName + ".Data.CreationDate"));
			
			FlagsMap areaFlags = new FlagsMap();
			AreaPlayerFlags areaPlayerFlags = new AreaPlayerFlags();
			for(String aflags : c.getConfig().getConfigurationSection("Areas." + areaName + ".Flags").getKeys(false)){
				Flag f = FlagManager.getFlag(aflags);
				areaFlags.addFlag(f, c.getConfig().getBoolean("Areas." + areaName + ".Flags." + aflags ));
			}
			for(String players : c.getConfig().getConfigurationSection("Areas." + areaName + ".Players").getKeys(false)){
				for(String plflags : c.getConfig().getConfigurationSection("Areas." + areaName + ".Players." + players).getKeys(false)){
					Flag f = FlagManager.getFlag(plflags);
					areaPlayerFlags.addPlayerFlag(UUID.fromString(players), f, c.getConfig().getBoolean("Areas." + areaName + ".Players." + players + "." + plflags));
				}
			}
			a.setAreaFlags(areaFlags);
			a.setAreaPlayerFlags(areaPlayerFlags);
			Manager.addAreaToList(a);
			Manager.getChunkStorageManager(world).addAreaToChunkStorages(a);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public Map<String, Configuration> getSaveAreasFiles() {
		return worldConfig;
	}
}
