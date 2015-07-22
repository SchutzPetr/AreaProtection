package cz.Sicka.AreaProtection.ChunkStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Lang.Lang;

public class ChunkStorage {
	private final int x;
	private final int z;
	private String name;
	
	private Map<String, SubzoneChunk> areasAndsubzones = new HashMap<String, SubzoneChunk>();
	
	public ChunkStorage(int x, int z){
		this.x = x;
		this.z = z;
		this.name = x + ", " + z;
	}
	
	public ChunkStorage(String chunkStorageName){
		String[] s = chunkStorageName.split(", ");
		this.x = Integer.valueOf(s[0]);
		this.z = Integer.valueOf(s[1]);
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
	
	public List<String> getAreaSubzones(String areaName){
		if(this.areasAndsubzones.containsKey(areaName.toLowerCase())){
			return this.areasAndsubzones.get(areaName.toLowerCase()).getSubzones();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ChunkStorage");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area not exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public void addArea(Area area){
		if(this.areasAndsubzones.containsKey(area.getAreaName().toLowerCase())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: ChunkStorage");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: addArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: description: area already exist");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
		areasAndsubzones.put(area.getAreaName().toLowerCase(), new SubzoneChunk(area));
	}
	
	public void removeArea(Area area){
		removeArea(area.getAreaName().toLowerCase());
	}
	
	public void removeArea(String areaName){
		if(this.areasAndsubzones.containsKey(areaName.toLowerCase())){
			this.areasAndsubzones.remove(areaName.toLowerCase());
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
		return containsArea(area.getAreaName().toLowerCase());
	}
	
	public boolean containsArea(String areaName){
		return this.areasAndsubzones.containsKey(areaName.toLowerCase());
	}

	public Set<String> getAreas() {
		return areasAndsubzones.keySet();
	}
	
	public boolean isLocationInArea(int locx, int locy, int locz, World world){
		return isLocationInArea(new Location(world, locx, locy, locz));
	}
	
	public boolean isLocationInArea(Location loc){
		for(String s : this.areasAndsubzones.keySet()){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				System.out.print("isLocationInArea" + true);
				return true;
			}else{
				System.out.print("isLocationInArea - continue");
				continue;
			}	
		}
		System.out.print("isLocationInArea" + false);
		return false;
	}
	
	public Area getAreaByLocation(Location loc){
		for(String s : this.areasAndsubzones.keySet()){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				return a;
			}else{
				continue;
			}
		}
		return null;
	}
	
	public Subzone getSubzoneByLocation(Location loc){
		for(String s : this.areasAndsubzones.keySet()){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				for(String subzoneName : this.areasAndsubzones.get(a.getAreaName().toLowerCase()).getSubzones()){
					Subzone sub = a.getSubzoneManager().getSubzone(subzoneName);
					if(sub.containsLocation(loc)){
						return sub;
					}else{
						continue;
					}
				}
			}else{
				continue;
			}
		}
		return null;
	}
	
	public Subzone getSubzoneByLocation(Area area, Location loc){
		if(area.containsLocation(loc)){
			for(String subzoneName : this.areasAndsubzones.get(area.getAreaName().toLowerCase()).getSubzones()){
				Subzone sub = area.getSubzoneManager().getSubzone(subzoneName);
				if(sub.containsLocation(loc)){
					return sub;
				}else{
					continue;
				}
			}
		}
		return null;
	}
	
	public boolean isLocationInSubzone(Area area, int locx, int locy, int locz, World world){
		return isLocationInSubzone(area, new Location(world, locx, locy, locz));
	}
	
	public boolean isLocationInSubzone(int locx, int locy, int locz, World world){
		return isLocationInSubzone(new Location(world, locx, locy, locz));
	}
	
	public boolean isLocationInSubzone(Location loc){
		for(String s : this.areasAndsubzones.keySet()){
			Area a = Manager.getArea(s);
			if(a.containsLocation(loc)){
				for(String subzoneName : this.areasAndsubzones.get(a.getAreaName().toLowerCase()).getSubzones()){
					Subzone sub = a.getSubzoneManager().getSubzone(subzoneName);
					if(sub.containsLocation(loc)){
						return true;
					}else{
						continue;
					}
				}
			}else{
				continue;
			}
		}
		return false;
	}
	
	public boolean isLocationInSubzone(Area area, Location loc){
		if(area.containsLocation(loc)){
			for(String subzoneName : this.areasAndsubzones.get(area.getAreaName().toLowerCase()).getSubzones()){
				Subzone sub = area.getSubzoneManager().getSubzone(subzoneName);
				if(sub.containsLocation(loc)){
					return true;
				}else{
					continue;
				}
			}
		}
		return false;
	}
}
