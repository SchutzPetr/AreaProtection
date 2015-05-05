package cz.Sicka.AreaProtection.Area;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class ServerArea {
	private String areaName;
	private List<String> chunks;
	
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private String world;
	
	private String owner;
	private AreaPlayerFlags areaPlayerFlags;
	
	private long CreationDate;
	private FlagsMap areaFlags;
	
	public ServerArea(String areaName, String owner, int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z, String world){
		this.areaName = areaName;
		this.owner = owner;
		
		serialize(loc_1_x, loc_1_y, loc_1_z, loc_2_x, loc_2_y, loc_2_z);
		
		this.world = world;
		
		this.areaPlayerFlags = new AreaPlayerFlags();
	}

	public void serialize(int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z){
		if(loc_1_x > loc_2_x){
			highX = loc_1_x;
			lowX = loc_2_x;
		}else{
			highX = loc_2_x;
			lowX = loc_1_x;
		}
		if(loc_1_y > loc_2_y){
			highY = loc_1_y;
			lowY = loc_2_y;
		}else{
			highY = loc_2_y;
			lowY = loc_1_y;
		}
		if(loc_1_z > loc_2_z){
			highZ = loc_1_z;
			lowZ = loc_2_z;
		}else{
			highZ = loc_2_z;
			lowZ = loc_1_z;
		}
	}

	public int getHighX() {
        return highX;
    }

    public int getHighY() {
        return highY;
    }

    public int getHighZ() {
        return highZ;
    }

    public int getLowX() {
        return lowX;
    }

    public int getLowY() {
        return lowY;
    }

    public int getLowZ() {
        return lowZ;
    }
    
    public boolean containsLocation(int x, int y, int z) {
        if (lowX <= x && highX >= x) {
            if (lowZ <= z && highZ >= z) {
                if (lowY <= y && highY >= y) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public String getWorldName(){
		return world;
	}

	public String getAreaName() {
		return areaName;
	}

	public AreaPlayerFlags getAreaPlayerFlags() {
		return areaPlayerFlags;
	}

	public void setAreaPlayerFlags(AreaPlayerFlags areaPlayerFlags) {
		this.areaPlayerFlags = areaPlayerFlags;
	}

	public long getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(long creationDate) {
		CreationDate = creationDate;
	}

	public FlagsMap getAreaFlags() {
		return areaFlags;
	}

	public void setAreaFlags(FlagsMap areaFlags) {
		this.areaFlags = areaFlags;
	}
	
	public String getOwnerName() {
		return this.owner;
	}

	public List<String> getChunks() {
		return chunks;
	}

	public void setChunks(List<String> chunkNames) {
		this.chunks = chunkNames;
	}
	
	public Location getCenter() {
        return new Location(Bukkit.getWorld(getWorldName()), (getHighX() + getLowX()) / 2, (getHighY() + getLowY()) / 2, (getHighZ() + getLowZ()) / 2);
    }
}
