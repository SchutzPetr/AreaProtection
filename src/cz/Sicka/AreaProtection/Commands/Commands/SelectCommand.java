package cz.Sicka.AreaProtection.Commands.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SelectCommand {
	private Map<Player, Location> playerLoc1 = new HashMap<Player, Location>();
	private Map<Player, Location> playerLoc2 = new HashMap<Player, Location>();
	
	public void onSelectCommand(Player player, int xsize, int ysize, int zsize){
		Location loc = player.getLocation();
		Location loc1 = new Location(loc.getWorld(), loc.getBlockX() + xsize, loc.getBlockY() + ysize, loc.getBlockZ() + zsize);
	    Location loc2 = new Location(loc.getWorld(), loc.getBlockX() - xsize, loc.getBlockY() - ysize, loc.getBlockZ() - zsize);
	    this.playerLoc1.put(player, loc1);
	    this.playerLoc2.put(player, loc2);
	}

}
