package cz.Sicka.AreaProtection.Chunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.World;

import cz.Sicka.AreaProtection.Area.Area;

public class ChunkStorageManager {
	private Map<String, ChunkStorage> ChunkStorage = new HashMap<String, ChunkStorage>();
	
	private World world;

	public ChunkStorageManager(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
	
	public void addAreaToChunkStorages(Area area){
		List<String> s = calculateChunksAndgetChunkNames(area.getHighX(), area.getHighZ(), area.getLowX(), area.getLowZ());
		area.setChunks(s);
		for(String chunkname : s){
			getChunkStorage(chunkname).addArea(area);
		}
	}
	
	public void removeAreaFromChunkStorages(Area area){
		List<String> s = area.getChunks();
		for(String chunkname : s){
			getChunkStorage(chunkname).removeArea(area);
		}
	}
	
	/**
	 * @param ChunkStorage name that you want to get.
	 * @return ChunkStorage
	 * Note: if ChunkStorage not set -> Create new ChunkStorage and save them to map
	 */
	public ChunkStorage getChunkStorage(String chunkName){
		if(ChunkStorage.containsKey(chunkName)){
			return ChunkStorage.get(chunkName);
		}else{
			ChunkStorage ch = new ChunkStorage(chunkName);
			ChunkStorage.put(chunkName, ch);
			return ch;
		}
	}
	
	/*public List<ChunkStorage> calculateChunks(int x1, int z1, int x2, int z2){
		List<ChunkStorage> ch = new ArrayList<ChunkStorage>();
		if(x1 > x2){
			for(int u = x1 >> 4; u >= x2 >> 4; u--){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu >= z2 >> 4; uu--){
						ch.add(new ChunkStorage(u, uu));
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(new ChunkStorage(u, uu));
					}
				}
			}
		}else{
			for(int u = x1 >> 4; u <= x2 >> 4; u++){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu--){
						ch.add(new ChunkStorage(u, uu));
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(new ChunkStorage(u, uu));
					}
				}
			}
		}
		return ch;
	}*/
	
	
	public List<String> calculateChunksAndgetChunkNames(int x1, int z1, int x2, int z2){
		List<String> ch = new ArrayList<String>();
		if(x1 > x2){
			for(int u = x1 >> 4; u >= x2 >> 4; u--){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu >= z2 >> 4; uu--){
						ch.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(u + ", " + uu);
					}
				}
			}
		}else{
			for(int u = x1 >> 4; u <= x2 >> 4; u++){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu--){
						ch.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(u + ", " + uu);
					}
				}
			}
		}
		return ch;
	}
}
