package cz.Sicka.AreaProtection.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area.AreaType;
import cz.Sicka.AreaProtection.Chunks.ChunkStorageManager;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Lang.Lang;

public class AreaManager {
	public static List<Area> getPotencialCollisionAreas(Location loc, Location loc2) {
		ChunkStorageManager chm = Manager.getChunkStorageManager(loc.getWorld().getName());
		List<String> listChunkName = chm.calculateChunksAndgetChunkNames(loc.getBlockX(), loc.getBlockZ(), loc2.getBlockX(), loc2.getBlockZ());
		List<Area> are = new ArrayList<Area>();
		for(String chunkName : listChunkName){
			for(String area : chm.getChunkStorage(chunkName).getAreas()){
				are.add(Manager.getArea(area));
			}
		}
		return are;
    }
	
	public static List<Area> getColisionAreas(Location loc1, Location loc2) {
		List<Area> are = new ArrayList<Area>();
		for(Area a : getPotencialCollisionAreas(loc1, loc2)){
			if(checkColision(a, loc1, loc2)){
				are.add(a);
			}
		}
		return are;
	}
	
	public static boolean checkColision(Area area, Location loc1, Location loc2) {
		return isLocationInArea(area, loc1) || isLocationInArea(area, loc2);
	}
	
	private static boolean isLocationInArea(Area area, Location loc) {
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
		if(type == AreaType.PLAYER_AREA){
			createPlayerArea(Bukkit.getPlayer(UUID.fromString(owner)), areaName, loc1, loc2, world);
		}
	}
	
	public static void removeArea(Player player, Area area){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreaConfigs().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getName()) || !c.getConfig().isSet("Areas." + area.getName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: AreaManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area not exist!");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: AreasList:&a " + !Manager.isAreaExist(area.getName()));
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Config:&a " + !c.getConfig().isSet("Areas." + area.getName()));

			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		Manager.getChunkStorageManager(area.getWorldName()).removeAreaFromChunkStorages(area);
		
		Manager.removeArea(area.getName());
		c.getConfig().getConfigurationSection("Areas").set(area.getName(), null);
		c.saveConfig();
	}
	
	private static void createPlayerArea(Player owner, String areaName, Location loc1, Location loc2, String world){
		PlayerArea pa = new PlayerArea(areaName, owner.getUniqueId(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), world);
		Area a = new Area(pa);
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreaConfigs().get(world);
		if(c.getConfig().isSet("Areas." + areaName)){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ConfigurationManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: createPlayerArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area already exist!");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
		}else{
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowX", a.getLowX());
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowY", a.getLowY());
			c.getConfig().set("Areas." + areaName + ".Data.Location.LowZ", a.getLowZ());
			
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighX", a.getHighX());
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighY", a.getHighY());
			c.getConfig().set("Areas." + areaName + ".Data.Location.HighZ", a.getHighZ());
			
			c.getConfig().set("Areas." + areaName + ".Data.Location.World", a.getWorldName());
			
			c.getConfig().set("Areas." + areaName + ".Data.Owner", owner.getUniqueId().toString());
			
			c.getConfig().set("Areas." + areaName + ".Data.AreaType", AreaType.PLAYER_AREA.toString());
			
			c.getConfig().getLong("Areas." + areaName + ".Data.CreationDate", System.currentTimeMillis());
			
			c.getConfig().createSection("Areas." + areaName + ".Data.Flags");
			
			c.saveConfig();
			a.getAreaFlags();
			Manager.addAreaToList(a);
			
			Manager.getChunkStorageManager(world).addAreaToChunkStorages(a);
		}
	}

}
