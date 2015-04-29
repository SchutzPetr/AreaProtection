package cz.Sicka.AreaProtection.Chunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkAPSmallManager {
	private Map<String, ChunkAP> chunksAP = new HashMap<String, ChunkAP>();
	
	public ChunkAP getChunkAP(String chunkAPName){
		return chunksAP.get(chunkAPName);
	}
	
	public void addChunksAP(ChunkAP chunkAP){
		if(this.chunksAP.containsKey(chunkAP.getName())){
			return;
		}else{
			this.chunksAP.put(chunkAP.getName(), chunkAP);
		}
	}
	
	public void addAreaToChunkAP(ChunkAP chunkAP, String area){
		addChunksAP(chunkAP);
		getChunkAP(chunkAP.getName()).addArea(area);
	}
	
	public List<ChunkAP> getChunkAPs(int x1, int z1, int x2, int z2){
		List<ChunkAP> ch = new ArrayList<ChunkAP>();
		if(x1 > x2){
			for(int u = x1 >> 4; u >= x2 >> 4; u--){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu >= z2 >> 4; uu--){
						ch.add(new ChunkAP(u, uu));
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(new ChunkAP(u, uu));
					}
				}
			}
		}else{
			for(int u = x1 >> 4; u <= x2 >> 4; u++){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu--){
						ch.add(new ChunkAP(u, uu));
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						ch.add(new ChunkAP(u, uu));
					}
				}
			}
		}
		return ch;
	}
	
	
	public List<String> getChunkNames(int x1, int z1, int x2, int z2){
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
