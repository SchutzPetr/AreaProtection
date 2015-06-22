package cz.Sicka.AreaProtection.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChunkCalculate {
	public static List<String> calculateChunksAndgetChunkNames(int x1, int z1, int x2, int z2){
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
