package cz.Sicka.AreaProtection.API;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Area.PermissionArea;
import cz.Sicka.AreaProtection.Area.WorldArea;

public class AreaProtectionManager {
	
	public cz.Sicka.AreaProtection.Area.Area getAreaByLocation(org.bukkit.Location loc){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage ch = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(ch != null){
			cz.Sicka.AreaProtection.Area.Area a = ch.getAreaByLocation(loc);
			if(a != null){
				return a;
			}
		}
		return null;
	}
	
	public cz.Sicka.AreaProtection.Area.Subzone getSubzoneByLocation(org.bukkit.Location loc){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage ch = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(ch != null){
			cz.Sicka.AreaProtection.Area.Subzone subzone = ch.getSubzoneByLocation(loc);
			if(subzone != null){
				return subzone;
			}
		}
		return null;
	}
	
	public cz.Sicka.AreaProtection.Area.Subzone getSubzoneByLocation(org.bukkit.Location loc, cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunk, cz.Sicka.AreaProtection.Area.Area area){
		if(chunk != null){
			cz.Sicka.AreaProtection.Area.Subzone subzone = chunk.getSubzoneByLocation(area, loc);
			if(subzone != null){
				return subzone;
			}
		}
		return null;
	}
	
	public PermissionArea getPermissionArea(org.bukkit.Location loc){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunkStorage = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(chunkStorage != null){
			cz.Sicka.AreaProtection.Area.Area area = chunkStorage.getAreaByLocation(loc);
			if(area != null){
				if(area.getSubzoneManager().getSubzones().isEmpty()){
					return new PermissionArea(area.getAreaName(), AreaType.MAIN, area.getAreaFlags(), area.getWorldName(), area.getOwner().getName());
				}else{
					cz.Sicka.AreaProtection.Area.Subzone subzone = chunkStorage.getSubzoneByLocation(area, loc);
					if(subzone != null){
						new PermissionArea(subzone.getSubzoneName(), area.getAreaName(), AreaType.SUBZONE, subzone.getAreaFlags(), subzone.getWorldName(), subzone.getOwner().getName());
					}else{
						return new PermissionArea(area.getAreaName(), AreaType.MAIN, area.getAreaFlags(), area.getWorldName(), area.getOwner().getName());
					}
				}
			}else{
				WorldArea wa = cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName());
				return new PermissionArea(wa.getAreaName(), AreaType.WORLD_AREA, wa.getWorldFlags(), wa.getWorldName(), "Nature");
			}
		}
		return null;
	}
	
	public boolean isPlayerOwner(Player player, Area a){
		return a.getOwnerUniqueId().equals(player.getUniqueId());
	}
	
	public cz.Sicka.AreaProtection.Area.Area getAreaByName(String name){
		return cz.Sicka.AreaProtection.Manager.getArea(name);
	}
	
	public boolean isPlayerInArea(Player player){
		return getAreaByLocation(player.getLocation()) != null;
	}
	
	public boolean allowAction(org.bukkit.entity.Player player, cz.Sicka.AreaProtection.Flags.Flag flag){
		org.bukkit.Location loc = player.getLocation();
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunkStorage = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(chunkStorage != null){
			cz.Sicka.AreaProtection.Area.Area area = chunkStorage.getAreaByLocation(loc);
			if(area != null){
				if(area.getSubzoneManager().getSubzones().isEmpty()){
					return area.allowAction(player.getUniqueId(), flag);
				}else{
					cz.Sicka.AreaProtection.Area.Subzone subzone = chunkStorage.getSubzoneByLocation(area, loc);
					if(subzone != null){
						return subzone.allowAction(player.getUniqueId(), flag);
					}else{
						return area.allowAction(player.getUniqueId(), flag);
					}
				}
			}else{
				return cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName()).allowAction(flag);
			}
		}
		return false;
	}
	
	public boolean allowAction(org.bukkit.Location loc, cz.Sicka.AreaProtection.Flags.Flag flag){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunkStorage = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(chunkStorage != null){
			cz.Sicka.AreaProtection.Area.Area area = chunkStorage.getAreaByLocation(loc);
			if(area != null){
				if(area.getSubzoneManager().getSubzones().isEmpty()){
					return area.allowAction(flag);
				}else{
					cz.Sicka.AreaProtection.Area.Subzone subzone = chunkStorage.getSubzoneByLocation(area, loc);
					if(subzone != null){
						return subzone.allowAction(flag);
					}else{
						return area.allowAction(flag);
					}
				}
			}else{
				return cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName()).allowAction(flag);
			}
		}
		return false;
	}

	public boolean allowAction(org.bukkit.entity.Player player, org.bukkit.Location loc, cz.Sicka.AreaProtection.Flags.Flag flag) {
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunkStorage = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(chunkStorage != null){
			cz.Sicka.AreaProtection.Area.Area area = chunkStorage.getAreaByLocation(loc);
			if(area != null){
				if(area.getSubzoneManager().getSubzones().isEmpty()){
					return area.allowAction(player.getUniqueId(), flag);
				}else{
					cz.Sicka.AreaProtection.Area.Subzone subzone = chunkStorage.getSubzoneByLocation(area, loc);
					if(subzone != null){
						return subzone.allowAction(player.getUniqueId(), flag);
					}else{
						return area.allowAction(player.getUniqueId(), flag);
					}
				}
			}else{
				return cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName()).allowAction(flag);
			}
		}
		return false;
	}

	public boolean allowAction(org.bukkit.entity.Player player, org.bukkit.Location loc, cz.Sicka.AreaProtection.Area.Area area, cz.Sicka.AreaProtection.Flags.Flag flag) {
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.ChunkStorage.ChunkStorage chunkStorage = cz.Sicka.AreaProtection.Manager.getChunkStorageManager(loc.getWorld().getName()).getChunkStorage(chunk.getX() + ", " + chunk.getZ());
		if(chunkStorage != null){
			if(area != null){
				if(area.getSubzoneManager().getSubzones().isEmpty()){
					return area.allowAction(player.getUniqueId(), flag);
				}else{
					cz.Sicka.AreaProtection.Area.Subzone subzone = chunkStorage.getSubzoneByLocation(area, loc);
					if(subzone != null){
						return subzone.allowAction(player.getUniqueId(), flag);
					}else{
						return area.allowAction(player.getUniqueId(), flag);
					}
				}
			}else{
				return cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName()).allowAction(flag);
			}
		}
		return false;
	}
	

}
