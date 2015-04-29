package cz.Sicka.AreaProtection.Area;

import java.util.HashMap;
import java.util.Map;

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

}
