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
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Area.PermissionArea;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Area.WorldArea;
import cz.Sicka.AreaProtection.ChunkStorage.ChunkStorageManager;
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
	
	public static void reloadArea(String areaName){
		Area area = getArea(areaName);
		removeArea(areaName);
		AreaProtection.getInstance().getConfigurationManager().getLoadArea().loadArea(areaName, area.getWorldName());
	}
	
	public static void reloadArea(Area area){
		removeArea(area.getAreaName());
		AreaProtection.getInstance().getConfigurationManager().getLoadArea().loadArea(area.getAreaName(), area.getWorldName());
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
		String lowerCaseName = areaName.toLowerCase();
		if(isAreaExist(lowerCaseName)){
			return getAllAreas().get(lowerCaseName);
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
		return getAllAreas().containsKey(areaName.toLowerCase());
	}
	
	public static void removeArea(String areaName){
		String lowerCaseName = areaName.toLowerCase();
		if(isAreaExist(lowerCaseName)){
			Area area = getArea(lowerCaseName);
			getAllAreas().remove(lowerCaseName);
			for(String chunk : area.getChunks()){
				getChunkStorageManager(area.getWorldName()).getChunkStorage(chunk).removeArea(lowerCaseName);
			}
			worldAreas.remove(lowerCaseName);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
	}
	
	public static void addArena(Area area){
		addAreaToList(area);
		getChunkStorageManager(area.getWorldName()).addAreaToChunkStorages(area);
	}
	
	private static void addAreaToList(Area area){
		String lowerCaseName = area.getAreaName().toLowerCase();
		if(!getAllAreas().containsKey(lowerCaseName)){
			getAllAreas().put(lowerCaseName, area);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addAreaToList");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: SubMetod ID: " + 1);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: Area " + lowerCaseName + " already exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
		}
		if(getAreasInWorlds().containsKey(area.getWorldName())){
			if(!getAreasInWorlds().get(area.getWorldName()).contains(lowerCaseName)){
				getAreasInWorlds().get(area.getWorldName()).add(lowerCaseName);
			}else{
				AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addAreaToList");
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: SubMetod ID: " + 2);
				AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: Area " + lowerCaseName + " already exist");
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
		world_Area.put(area.getWorldName().toLowerCase(), area);
	}
	
	public static WorldArea getWorldArea(String worldName){
		if(world_Area.containsKey(worldName.toLowerCase())){
			return world_Area.get(worldName.toLowerCase());
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Manager");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getWorldArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: worldArea not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public static boolean isWorldExist(String worldName){
		return world_Area.containsKey(worldName.toLowerCase());
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
	
	public static Location getDefaultTeleportLocation(Subzone subzone){
		return Utils.getLocationAboveGround(subzone.getCenter());
	}
	
	public static Location getDefaultTeleportLocation(Area area){
		return Utils.getLocationAboveGround(area.getCenter());
	}
	
	public static PermissionArea getPermissionArea(String areaName){
		String lowerCaseName = areaName.toLowerCase();
		if(world_Area.containsKey(lowerCaseName)){
			WorldArea wa = cz.Sicka.AreaProtection.Manager.getWorldArea(lowerCaseName);
			return new PermissionArea(wa.getAreaName(), AreaType.WORLD_AREA, wa.getWorldFlags(), wa.getWorldName(), "Nature");
		}
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainArea = split[0];
			String sub = split[1];
			if(Manager.isAreaExist(mainArea)){
				Area area = Manager.getArea(mainArea);
				if(area.getSubzoneManager().isSubzoneExist(sub)){
					Subzone subzone = area.getSubzoneManager().getSubzone(sub);
					return new PermissionArea(subzone.getSubzoneName(), area.getAreaName(), AreaType.SUBZONE, subzone.getAreaFlags(), subzone.getWorldName(), subzone.getOwner().getName());
				}else{
					System.out.print("Subzone not exist!");
					return null;
				}
			}else{
				System.out.print("Area not exist!");
				return null;
			}
		}else{
			if(Manager.isAreaExist(lowerCaseName)){
				Area area = Manager.getArea(lowerCaseName);
				return new PermissionArea(area.getAreaName(), AreaType.MAIN, area.getAreaFlags(), area.getWorldName(), area.getOwner().getName());
			}else{
				System.out.print("Area not exist!");
				return null;
			}
		}
	}
}
