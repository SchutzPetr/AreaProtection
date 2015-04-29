package cz.Sicka.AreaProtection.Chunks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;

public class ChunkAP {
	private final int x;
	private final int z;
	private String name;
	private List<String> area = new ArrayList<String>();
	
	public ChunkAP(int x, int z){
		this.x = x;
		this.z = z;
		this.name = x + ", " + z;
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
	
	public void addArea(String a){
		if(this.area.contains(a)){
			return;
		}else{
			this.area.add(a);
		}
	}

	public List<String> getAreas() {
		return area;
	}
	
	public boolean isLocationInArea(int locx, int locy, int locz, World world){
		return isLocationInArea(new Location(world, locx, locy, locz));
	}
	
	public boolean isLocationInArea(Location loc){
		for(String s : this.area){
			Area a = AreaManager.getArea(s);
			if(a.containsLocation(loc)){
				return true;
			}else{
				continue;
			}	
		}
		return false;
	}
	
	public Area getAreaByLocation(Location loc){
		for(String s : this.area){
			Area a = AreaManager.getArea(s);
			if(a.containsLocation(loc)){
				return a;
			}else{
				continue;
			}
		}
		return null;
	}
}
