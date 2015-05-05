package cz.Sicka.AreaProtection.API;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area;

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
		return cz.Sicka.AreaProtection.Manager.getWorldArea(loc.getWorld().getName());
	}
	
	public boolean isPlayerOwner(Player player, Area a){
		return a.getOwnerUUID().equals(player.getUniqueId());
	}
}
