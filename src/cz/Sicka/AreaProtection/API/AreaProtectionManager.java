package cz.Sicka.AreaProtection.API;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.PermissionArea;
import cz.Sicka.AreaProtection.Area.WorldArea;

public class AreaProtectionManager {
	
	public cz.Sicka.AreaProtection.Area.Area getAreaByLocation(org.bukkit.Location loc){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.Chunks.ChunkStorage ch = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(ch != null){
			cz.Sicka.AreaProtection.Area.Area a = ch.getAreaByLocation(loc);
			if(a != null){
				return a;
			}
		}
		return null;
	}
	
	public PermissionArea getPermissionArea(org.bukkit.Location loc){
		Area area = getAreaByLocation(loc);
		if(area != null){
			return new PermissionArea(area.getAreaName(), area.getType(), area.getAreaFlags(), area.getWorldName(), area.getOwner().getName());
		}else{
			WorldArea wa = Manager.getWorldArea(loc.getWorld().getName());
			return new PermissionArea(wa.getAreaName(), wa.getType(), wa.getWorldFlags(), wa.getWorldName(), "Nature");
		}
	}
	
	public boolean isPlayerOwner(Player player, Area a){
		return a.getOwnerUUID().equals(player.getUniqueId());
	}
	
	public cz.Sicka.AreaProtection.Area.Area getAreaByName(String name){
		return Manager.getArea(name);
	}
	
	public boolean isPlayerInArea(Player player){
		return getAreaByLocation(player.getLocation()) != null;
	}

	@SuppressWarnings("deprecation")
	public boolean allowAction(org.bukkit.entity.Player player, cz.Sicka.AreaProtection.Flags.Flag flag){
		cz.Sicka.AreaProtection.Area.Area area = getAreaByLocation(player.getLocation());
		if(area == null){
			return true;
		}else{
			return area.allowAction(player.getUniqueId(), flag);
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean allowAction(org.bukkit.Location loc, cz.Sicka.AreaProtection.Flags.Flag flag){
		cz.Sicka.AreaProtection.Area.Area area = getAreaByLocation(loc);
		if(area == null){
			return true;
		}else{
			return area.allowAction(flag);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean allowAction(org.bukkit.entity.Player player, org.bukkit.Location location, cz.Sicka.AreaProtection.Flags.Flag flag) {
		cz.Sicka.AreaProtection.Area.Area area = getAreaByLocation(location);
		if(area == null){
			return true;
		}else{
			return area.allowAction(player.getUniqueId(), flag);
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean allowAction(org.bukkit.entity.Player player, cz.Sicka.AreaProtection.Area.Area area, cz.Sicka.AreaProtection.Flags.Flag flag) {
		if(area == null){
			return true;
		}else{
			return area.allowAction(player.getUniqueId(), flag);
		}
	}
}
