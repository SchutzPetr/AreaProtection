package cz.Sicka.AreaProtection.Chunks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;

public class ChunkAPManager {
	private static Map<String, ChunkAPSmallManager> worldChunkAPSmallManager = new HashMap<String, ChunkAPSmallManager>();
	
	public static void init(){
		for(World w : AreaProtection.getInstance().getServer().getWorlds()){
			worldChunkAPSmallManager.put(w.getName(), new ChunkAPSmallManager());
		}
	}
	
	public static ChunkAPSmallManager getChunkAPManagerForWorld(World w){
		return getChunkAPManagerForWorld(w.getName());
	}
	
	public static ChunkAPSmallManager getChunkAPManagerForWorld(String w){
		return worldChunkAPSmallManager.get(w);
	}
}
