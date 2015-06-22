package cz.Sicka.AreaProtection.Area;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Area.AreaPlayerFlags;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class Area {
	private String areaName;
	
	private List<String> chunks;
	
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private Location teleportLocation;
	
	private String world;
	
	private UUID owner;
	private AreaPlayerFlags areaPlayerFlags;
	
	private long CreationDate;
	private FlagsMap areaFlags;
	
	private AreaType type;
	private String ownerName;

	public Area(String areaName, String owner, AreaType type, int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z, String worldName){
		this.areaName = areaName;
		this.owner = UUID.fromString(owner);
		this.ownerName = getOwner().getName();
		this.type = type;
		serialize(loc_1_x, loc_1_y, loc_1_z, loc_2_x, loc_2_y, loc_2_z);
		
		this.world = worldName;
		
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
    
    public boolean containsLocation(Location loc) {
		return containsLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
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

	public OfflinePlayer getOwner() {
		return AreaProtection.getInstance().getServer().getOfflinePlayer(this.owner);
	}
	
	public UUID getOwnerUUID() {
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
	
	@Deprecated
	private boolean allowActionPlayerFlag(UUID uuid, Flag flag){
		if(getAreaPlayerFlags().containsPlayer(uuid)){
			FlagsMap fm = getAreaPlayerFlags().getPlayerFlags(uuid);
			if(fm.getFlags().contains(flag.getName())){
				return fm.getFlagValue(flag.getName());
			}else{
				return allowAction(flag);
			}
		}else{
			return allowAction(flag);
		}
	}
	@Deprecated
	public boolean allowAction(Flag flag){
		if(getAreaFlags().getFlags().contains(flag.getName())){
			return getAreaFlags().getFlagValue(flag.getName());
		}else{
			return FlagManager.getAreaFlag(flag.getName()).getDefaultAreaFlagValue();
		}
	}
	@Deprecated
	public boolean allowAction(UUID player, Flag flag){
		if(isOwner(player)){
			return true;
		}else{
			return allowActionPlayerFlag(player, flag);
		}
	}
	
	public boolean isOwner(UUID player){
		return getOwnerUUID().equals(player);
	}

	public AreaType getType() {
		return type;
	}

	public Location getTeleportLocation() {
		return teleportLocation;
	}

	public void setTeleportLocation(Location teleportLocation) {
		this.teleportLocation = teleportLocation;
	}

	public String getOwnerName() {
		return ownerName;
	}
}
