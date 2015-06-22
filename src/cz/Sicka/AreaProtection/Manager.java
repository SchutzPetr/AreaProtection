package cz.Sicka.AreaProtection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.WorldArea;
import cz.Sicka.AreaProtection.Chunks.ChunkStorageManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Utils;

public class Manager {
	private static Map<String, ChunkStorageManager> World_ChunkStorageManagers = new HashMap<String, ChunkStorageManager>();
	private static Map<String, Area> areas = new HashMap<String, Area>();
	private static Map<String, List<String>> worldAreas = new HashMap<String, List<String>>();
	private static Map<String, WorldArea> world_Area = new HashMap<String, WorldArea>();
	
	/**
	 * Note: Init when server start!
	 */
	public static void Initialization(){
		getWorld_ChunkStorageManagers().clear();
		getAreasInWorlds().clear();
		getAllAreas().clear();
		for(World w : AreaProtection.getInstance().getServer().getWorlds()){
			getWorld_ChunkStorageManagers().put(w.getName(), new ChunkStorageManager(w));
			getAreasInWorlds().put(w.getName(), new ArrayList<String>());
		}
	}

	public static Map<String, ChunkStorageManager> getWorld_ChunkStorageManagers() {
		return World_ChunkStorageManagers;
	}
	

	public static void addChunkStorageManager(String worldName){
		if(World_ChunkStorageManagers.containsKey(worldName)){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: ChunkStorageManager already exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		World_ChunkStorageManagers.put(worldName, new ChunkStorageManager(Bukkit.getWorld(worldName)));
	}
	
	/**
	 * @param ChunkStorage name that you want to get.
	 * @return ChunkStorage
	 * Note: if ChunkStorage not set -> Create new ChunkStorage and save them to map
	 */
	public static ChunkStorageManager getChunkStorageManager(String worldName){
		if(World_ChunkStorageManagers.containsKey(worldName)){
			return World_ChunkStorageManagers.get(worldName);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getChunkStorageManager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Info: World Name:" + worldName);
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public static Area getArea(String areaName){
		if(isAreaExist(areaName)){
			return getAllAreas().get(areaName);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public static boolean isAreaExist(String areaName){
		return getAllAreas().containsKey(areaName);
	}
	
	public static void removeArea(String areaName){
		if(isAreaExist(areaName)){
			getAllAreas().remove(areaName);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
	}
	
	public static void addAreaToList(Area area){
		if(!getAllAreas().containsKey(area.getAreaName())){
			getAllAreas().put(area.getAreaName(), area);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addAreaToList");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: SubMetod ID: " + 1);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: Area " + area.getAreaName() + "already exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
		}
		if(getAreasInWorlds().containsKey(area.getWorldName())){
			if(!getAreasInWorlds().get(area.getWorldName()).contains(area.getAreaName())){
				getAreasInWorlds().get(area.getWorldName()).add(area.getAreaName());
			}else{
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addAreaToList");
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: SubMetod ID: " + 2);
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: Area " + area.getAreaName() + "already exist");
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			}
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addAreaToList");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: SubMetod ID: " + 3);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: worldArea not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
		}
	}
	
	public static void addWorldArea(WorldArea area){
		world_Area.put(area.getWorldName(), area);
	}
	
	public static WorldArea getWorldArea(String worldName){
		if(world_Area.containsKey(worldName)){
			return world_Area.get(worldName);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getWorldArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: worldArea not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}

	public static Map<String, Area> getAllAreas() {
		return areas;
	}
	
	private static Map<String, List<String>> getAreasInWorlds() {
		return worldAreas;
	}
	
	public static List<String> getAreasInWorld(String worldName) {
		return getAreasInWorlds().get(worldName);
	}
	
	public static Location getDefaultTeleportLocation(Area area){
		return Utils.getLocationAboveGround(area.getCenter());
	}
}
