package cz.Sicka.AreaProtection.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.ChunkStorage.ChunkStorageManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.ChunkCalculate;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class AreaManager {
	public static List<Subzone> getPotencialCollisionSubzones(Area area, Location loc, Location loc2) {
		ChunkStorageManager chm = Manager.getChunkStorageManager(loc.getWorld().getName());
		List<String> listChunkName = ChunkCalculate.calculateChunksAndgetChunkNames(loc.getBlockX(), loc.getBlockZ(), loc2.getBlockX(), loc2.getBlockZ());
		List<Subzone> subzones = new ArrayList<Subzone>();
		for(String chunkName : listChunkName){
			for(String sub : chm.getChunkStorage(chunkName).getAreaSubzones(area.getAreaName())){
				if(subzones.contains(area.getSubzoneManager().getSubzone(sub))){
					continue;
				}
				subzones.add(area.getSubzoneManager().getSubzone(sub));
			}
		}
		return subzones;
	}
	
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
	
	public static List<Subzone> getColisionSubzones(Area area, Location loc1, Location loc2) {
		List<Subzone> subzones = new ArrayList<Subzone>();
		for(Subzone sub : getPotencialCollisionSubzones(area, loc1, loc2)){
			if(!checkColision(sub, loc1, loc2)){
				continue;
			}
			subzones.add(sub);
		}
		return subzones;
	}
	
	public static boolean checkColision(Subzone sub, Location loc_1, Location loc_2) {
		if((sub.getLowX() <= loc_1.getBlockX() && loc_1.getBlockX() <= sub.getHighX()) || (sub.getLowX() <= loc_2.getBlockX() && loc_2.getBlockX() <= sub.getHighX())){
			if((sub.getLowY() <= loc_1.getBlockY() && loc_1.getBlockY() <= sub.getHighY()) || (sub.getLowY() <= loc_2.getBlockY() && loc_2.getBlockY() <= sub.getHighY())){
				if((sub.getLowZ() <= loc_1.getBlockZ() && loc_1.getBlockZ() <= sub.getHighZ()) || (sub.getLowZ() <= loc_2.getBlockZ() && loc_2.getBlockZ() <= sub.getHighZ())){
					return true;
				}
			}
		}
		return false;
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
	
	public static boolean createArea(AreaType type, Player owner, String areaName, Location loc1, Location loc2, String world){
		if(type == AreaType.MAIN){
			return false;
			//return createMainArea(owner, areaName, loc1, loc2, world);
		}else if(type == AreaType.SUBZONE){
			return createSubzoneArea(owner, areaName, loc1, loc2, world);
		}else{
			return false;
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
	
	public static boolean createMainArea(Area a){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(a.getWorldName());
		AreaProtection.LogMessage(Level.SEVERE, "1");
		String areaName = a.getAreaName();
		if(c.getConfig().isSet("Areas." + areaName )){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ConfigurationManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: createPlayerArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area already exist!");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return false;
		}else{
			AreaProtection.LogMessage(Level.SEVERE, "2");
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
			
			c.getConfig().set("Areas." + areaName + ".Data.Owner", a.getOwnerUniqueId().toString());
			
			c.getConfig().set("Areas." + areaName + ".Data.CreationDate", System.currentTimeMillis());
			
			c.getConfig().set("Areas." + areaName + ".Data.EnterMessage", "enterMessage");
			c.getConfig().set("Areas." + areaName + ".Data.LeaveMessage", "leaveMessage");
			
			c.getConfig().createSection("Areas." + areaName + ".Flags");
			c.getConfig().createSection("Areas." + areaName + ".Players");
			
			c.saveConfig();
			AreaProtection.getInstance().getConfigurationManager().getLoadArea().loadArea(areaName, c);
			return true;
		}
	}
	
	private static boolean createSubzoneArea(Player owner, String subzoneName, Location loc1, Location loc2, String world){
		PermissionArea permArea1 = AreaProtectionAPI.getAreaProtectionManager().getPermissionArea(loc1);
		PermissionArea permArea2 = AreaProtectionAPI.getAreaProtectionManager().getPermissionArea(loc1);
		if(permArea1 != null && permArea2 != null){
			if(permArea1.getType() == AreaType.MAIN && permArea2.getType() == AreaType.MAIN){
				if(permArea1.getAreaName().contains(permArea2.getAreaName())){
					List<Subzone> subzones = AreaManager.getColisionSubzones(Manager.getArea(permArea1.getAreaName()), loc1, loc2);
					if(subzones == null){
						System.out.print(subzones==null);
						return false;
					}
					if(!subzones.isEmpty() || subzones.size() != 0){
						Messages.sendMessage(owner, "Error! Oblast je v kolizi s jinou subzonou!");
						for(Subzone subzone : subzones){
							Messages.sendMessage(owner, "Error! Kolize s: " + subzone);
						}
						return false;
					}
					Subzone subzone = new Subzone(subzoneName, Manager.getArea(permArea1.getAreaName()), owner.getUniqueId().toString(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), world);
					Configuration config = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(world);
					String subzoneSelection = "Areas." + permArea1.getAreaName() + ".Subzones." + subzoneName;
					if(config.getConfig().isSet(subzoneSelection)){
						AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
						AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ConfigurationManager");
						AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: createSubzoneArea");
						AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Subzone already exist!");
						AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
						return false;
					}else{
						Location teleportLocation = Manager.getDefaultTeleportLocation(subzone);
						
						config.getConfig().set(subzoneSelection + ".Data.Location.LowX", subzone.getLowX());
						config.getConfig().set(subzoneSelection + ".Data.Location.LowY", subzone.getLowY());
						config.getConfig().set(subzoneSelection + ".Data.Location.LowZ", subzone.getLowZ());
						
						config.getConfig().set(subzoneSelection + ".Data.Location.HighX", subzone.getHighX());
						config.getConfig().set(subzoneSelection + ".Data.Location.HighY", subzone.getHighY());
						config.getConfig().set(subzoneSelection + ".Data.Location.HighZ", subzone.getHighZ());
						
						config.getConfig().set(subzoneSelection + ".Data.Location.World", subzone.getWorldName());
						
						config.getConfig().set(subzoneSelection + ".Data.TPLocation.X", teleportLocation.getBlockX());
						config.getConfig().set(subzoneSelection + ".Data.TPLocation.Y", teleportLocation.getBlockY());
						config.getConfig().set(subzoneSelection + ".Data.TPLocation.Z", teleportLocation.getBlockZ());
						
						config.getConfig().set(subzoneSelection + ".Data.Owner", owner);
						
						config.getConfig().set(subzoneSelection + ".Data.CreationDate", System.currentTimeMillis());
						
						config.getConfig().set(subzoneSelection + ".Data.EnterMessage", "enterMessage");
						config.getConfig().set(subzoneSelection + ".Data.LeaveMessage", "leaveMessage");
						
						config.getConfig().createSection(subzoneSelection + ".Flags");
						config.getConfig().createSection(subzoneSelection + ".Players");
						
						config.saveConfig();
						Manager.reloadArea(permArea1.getAreaName());
						return true;
					}
				}else{
					Messages.sendMessage(owner, "Error! Subzona lze vytvorit pouze uvnitr oblasti!");
					return false;
				}
			}else if(permArea1.getType() == AreaType.WORLD_AREA || permArea2.getType() == AreaType.WORLD_AREA){
				Messages.sendMessage(owner, "Error! Subzona lze vytvorit pouze uvnitr oblasti!");
				return false;
			}else{
				Messages.sendMessage(owner, "Error! Subzona nelze vytvorit v subzone!");
				if(permArea1.getType() == AreaType.SUBZONE){
					Messages.sendMessage(owner, "Error! Kolize s: " + permArea1.getAreaName());
				}else if(permArea2.getType() == AreaType.SUBZONE){
					Messages.sendMessage(owner, "Error! Kolize s: " + permArea2.getAreaName());
				}
				return false;
			}
		}else{
			return false;
		}
	}

}
