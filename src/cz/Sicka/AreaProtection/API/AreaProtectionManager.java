package cz.Sicka.AreaProtection.API;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area;

public class AreaProtectionManager {
	
	public cz.Sicka.AreaProtection.Area.Area getAreaByLocation(org.bukkit.Location loc){
		org.bukkit.Chunk chunk = loc.getChunk();
		cz.Sicka.AreaProtection.Chunks.ChunkAP ch = cz.Sicka.AreaProtection.Chunks.ChunkAPManager.getChunkAPManagerForWorld(loc.getWorld()).getChunkAP(chunk.getX() + ", " + chunk.getZ());
		if(ch != null){
			cz.Sicka.AreaProtection.Area.Area a = ch.getAreaByLocation(loc);
			if(a != null){
				return a;
			}
		}
		return cz.Sicka.AreaProtection.Area.AreaManager.getWorldArea(loc.getWorld().getName());
	}
	
	public boolean isPlayerOwner(Player player, Area a){
		return a.getOwnerUUID().equals(player.getUniqueId());
	}
}
