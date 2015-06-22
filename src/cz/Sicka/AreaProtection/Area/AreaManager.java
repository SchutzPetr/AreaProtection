package cz.Sicka.AreaProtection.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Chunks.ChunkStorageManager;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.ChunkCalculate;

public class AreaManager {
	public static List<Area> getPotencialCollisionAreas(Location loc, Location loc2) {
		ChunkStorageManager chm = Manager.getChunkStorageManager(loc.getWorld().getName());
		List<String> listChunkName = ChunkCalculate.calculateChunksAndgetChunkNames(loc.getBlockX(), loc.getBlockZ(), loc2.getBlockX(), loc2.getBlockZ());
		List<Area> are = new ArrayList<Area>();
		for(String chunkName : listChunkName){
			for(String area : chm.getChunkStorage(chunkName).getAreas()){
				if(are.contains(Manager.getArea(area))){
					continue;
				}
				are.add(Manager.getArea(area));
			}
		}
		return are;
    }
	
	public static List<Area> getColisionAreas(Location loc1, Location loc2) {
		List<Area> are = new ArrayList<Area>();
		for(Area a : getPotencialCollisionAreas(loc1, loc2)){
			if(!checkColision(a, loc1, loc2)){
				continue;
			}
			are.add(a);
		}
		return are;
	}
	
	public static boolean checkColision(Area area, Location loc_1, Location loc_2) {
		if((area.getLowX() <= loc_1.getBlockX() && loc_1.getBlockX() <= area.getHighX()) || (area.getLowX() <= loc_2.getBlockX() && loc_2.getBlockX() <= area.getHighX())){
			if((area.getLowY() <= loc_1.getBlockY() && loc_1.getBlockY() <= area.getHighY()) || (area.getLowY() <= loc_2.getBlockY() && loc_2.getBlockY() <= area.getHighY())){
				if((area.getLowZ() <= loc_1.getBlockZ() && loc_1.getBlockZ() <= area.getHighZ()) || (area.getLowZ() <= loc_2.getBlockZ() && loc_2.getBlockZ() <= area.getHighZ())){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isLocationInArea(Area area, Location loc) {
		 if (area.getLowX() <= loc.getBlockX() && area.getHighX() >= loc.getBlockX()) {
			 if (area.getLowZ() <= loc.getBlockZ() && area.getHighZ() >= loc.getBlockZ()) {
				 if (area.getLowY() <= loc.getBlockY() && area.getHighY() >= loc.getBlockY()) {
					 return true;
				 }
			 }
		 }
		 return false;
	}
	
	public static void createArea(AreaType type, String owner, String areaName, Location loc1, Location loc2, String world){
		if(type == AreaType.MAIN){
			createMainArea(owner, areaName, loc1, loc2, world);
		}
	}
	
	public static void removeArea(Player player, Area area){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getAreaName()) || !c.getConfig().isSet("Areas." + area.getAreaName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: AreaManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area not exist!");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: AreasList:&a " + !Manager.isAreaExist(area.getAreaName()));
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Config:&a " + !c.getConfig().isSet("Areas." + area.getAreaName()));

			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		Manager.getChunkStorageManager(area.getWorldName()).removeAreaFromChunkStorages(area);
		
		Manager.removeArea(area.getAreaName());
		c.getConfig().getConfigurationSection("Areas").set(area.getAreaName(), null);
		c.saveConfig();
	}
	
	private static void createMainArea(String owner, String areaName, Location loc1, Location loc2, String world){
		Area a = new Area(areaName, owner, AreaType.MAIN, loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), world);
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(world);
		if(c.getConfig().isSet("Areas." + areaName)){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ConfigurationManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: createPlayerArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area already exist!");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
		}else{
			Location teleportLocation = Manager.getDefaultTeleportLocation(a);
			
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowX", a.getLowX());
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowY", a.getLowY());
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowZ", a.getLowZ());
			
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighX", a.getHighX());
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighY", a.getHighY());
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighZ", a.getHighZ());
			
			c.getConfig().set("Areas." + areaName + ".Data.Location.World", a.getWorldName());
			
			c.getConfig().set("Areas." + areaName + ".Data.TPLocation.X", teleportLocation.getBlockX());
			c.getConfig().set("Areas." + areaName + ".Data.TPLocation.Y", teleportLocation.getBlockY());
			c.getConfig().set("Areas." + areaName + ".Data.TPLocation.Z", teleportLocation.getBlockZ());
			
			c.getConfig().set("Areas." + areaName + ".Data.Owner", owner);
			
			c.getConfig().set("Areas." + areaName + ".Data.AreaType", AreaType.MAIN.toString());
			
			c.getConfig().set("Areas." + areaName + ".Data.CreationDate", System.currentTimeMillis());
			
			c.getConfig().createSection("Areas." + areaName + ".Flags");
			c.getConfig().createSection("Areas." + areaName + ".Players");
			
			c.saveConfig();
			AreaProtection.getInstance().getConfigurationManager().getLoadArea().loadArea(areaName, c);
		}
	}

}
