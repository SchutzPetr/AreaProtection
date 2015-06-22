/**
 * 
 */
package cz.Sicka.AreaProtection.Utils;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Petr Schutz
 *
 */
public class LocationSerialization {
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private World world;

	public LocationSerialization(int loc1X, int loc1Y, int loc1Z, int loc2X, int loc2Y, int loc2Z, World world){
		if(loc1X > loc2X){
			highX = loc1X;
			lowX = loc2X;
		}else{
			highX = loc2X;
			lowX = loc1X;
		}
		if(loc1Y > loc2Y){
			highY = loc1Y;
			lowY = loc2Y;
		}else{
			highY = loc2Y;
			lowY = loc1Y;
		}
		if(loc1Z > loc2Z){
			highZ = loc1Z;
			lowZ = loc2Z;
		}else{
			highZ = loc2Z;
			lowZ = loc1Z;
		}
		this.world = world;
	}
	
	public Location getLowPosition() {
		return new Location(this.world, this.lowX, this.lowY, this.lowZ);
	}

	public Location getHightPosition() {
		return new Location(this.world, this.highX, this.highY, this.highZ);
	}
}
