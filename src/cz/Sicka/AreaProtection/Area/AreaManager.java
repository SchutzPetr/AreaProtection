package cz.Sicka.AreaProtection.Area;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Area.Area.AreaType;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Lang.Lang;

public class AreaManager {
	private static Map<String, Area> areas = new HashMap<String, Area>();
	private static Map<String, Area> worldArea = new HashMap<String, Area>();
	
	public static void addArea(String name, Area a){
		if(areas.containsKey(name)){
			areas.remove(name);
		}
		areas.put(name, a);
	}
	
	public static Area getArea(String name){
		return areas.get(name);
	}

	public static Map<String, Area> getAreas() {
		return areas;
	}
	
	public static void addWorldArea(String worldName, Area a){
		if(worldArea.containsKey(worldName)){
			worldArea.remove(worldName);
		}
		worldArea.put(worldName, a);
	}
	
	public static Area getWorldArea(String worldName){
		return worldArea.get(worldName);
	}

	public static Map<String, Area> getWorldArea() {
		return worldArea;
	}
	
	public static void createArea(AreaType type, String owner, String areaName, Location loc1, Location loc2, String world){
		if(type == AreaType.PLAYER_AREA){
			createPlayerArea(Bukkit.getPlayer(UUID.fromString(owner)), areaName, loc1, loc2, world);
		}
	}
	
	public static void removeArea(Player player, Area a){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreaConfigs().get(a.getWorldName());
		if(!areas.containsKey(a.getName()) || !c.getConfig().isSet("Areas." + a.getName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: AreaManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area not exist!");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: AreasList:&a " + !areas.containsKey(a.getName()));
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Config:&a " + !c.getConfig().isSet("Areas." + a.getName()));

			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		c.getConfig().getConfigurationSection("Areas").set(a.getName(), null);
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
		}
	}

}
