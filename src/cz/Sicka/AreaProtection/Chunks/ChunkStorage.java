package cz.Sicka.AreaProtection.Chunks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Lang.Lang;

public class ChunkStorage {
	private final int x;
	private final int z;
	private String name;
	private List<String> areas = new ArrayList<String>();
	
	public ChunkStorage(int x, int z){
		this.x = x;
		this.z = z;
		this.name = x + ", " + z;
	}
	
	public ChunkStorage(String chunkStorageName){
		String[] s = chunkStorageName.split(", ");
		this.x = Integer.valueOf(s[0]);
		this.z = Integer.valueOf(s[1]);;
		this.name = chunkStorageName;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
	
	public String getName(){
		return name;
	}
	
	public void addArea(Area area){
		addArea(area.getName());
	}
	
	public void addArea(String area){
		if(this.areas.contains(area)){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ChunkStorage");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area already exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		this.areas.add(area);
	}
	
	public void removeArea(Area area){
		removeArea(area.getName());
	}
	
	public void removeArea(String area){
		if(this.areas.contains(area)){
			this.areas.remove(area);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ChunkStorage");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
	}
	
	public boolean containsArea(Area area){
		return containsArea(area.getName());
	}
	
	public boolean containsArea(String area){
		return this.areas.contains(area);
	}

	public List<String> getAreas() {
		return areas;
	}
	
	public boolean isLocationInArea(int locx, int locy, int locz, World world){
		return isLocationInArea(new Location(world, locx, locy, locz));
	}
	
	public boolean isLocationInArea(Location loc){
		for(String s : this.areas){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				return true;
			}else{
				continue;
			}	
		}
		return false;
	}
	
	public Area getAreaByLocation(Location loc){
		for(String s : this.areas){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				return a;
			}else{
				continue;
			}
		}
		return null;
	}
}
