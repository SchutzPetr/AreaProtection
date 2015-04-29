package cz.Sicka.AreaProtection.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Selection {
	private static Map<Player, Location> playerLoc1 = new HashMap<Player, Location>();
	private static Map<Player, Location> playerLoc2 = new HashMap<Player, Location>();
	
	private static List<Location> blockbreakeloc = new ArrayList<Location>();
	
	private static Map<Player, Block> playerBlock1 = new HashMap<Player, Block>();
	private static Map<Player, Block> playerBlock2 = new HashMap<Player, Block>();
	
	private static Map<Player, Location> playerHologram1 = new HashMap<Player, Location>();
	private static Map<Player, Location> playerHologram2 = new HashMap<Player, Location>();
	
	public static void Select(Player player, int xsize, int ysize, int zsize){
		Location loc = player.getLocation();
		Location loc1 = new Location(loc.getWorld(), loc.getBlockX() + xsize, loc.getBlockY() + ysize, loc.getBlockZ() + zsize);
	    Location loc2 = new Location(loc.getWorld(), loc.getBlockX() - xsize, loc.getBlockY() - ysize, loc.getBlockZ() - zsize);
	    loc1.getBlock().setType(Material.GLOWSTONE);
	    loc2.getBlock().setType(Material.GLOWSTONE);
	    blockbreakeloc.add(loc1);
	    blockbreakeloc.add(loc2);
	    playerLoc1.put(player, loc1);
	    playerLoc2.put(player, loc2);
	}
	
	public static List<Location> getProtektedBlock(){
		return blockbreakeloc;
	}
	
	public static void SelectFirstPosition(Player player, int x, int y, int z, World world){
		SelectFirstPosition(player, new Location(world, x, y, z));
	}
	
	public static void SelectFirstPosition(Player player, Location firstLocation){
		playerLoc1.put(player, firstLocation);
		firstLocation.getBlock().setType(Material.GLOWSTONE);
		blockbreakeloc.add(firstLocation);
		printInfo(player);
	}
	
	public static void SelectSecondPosition(Player player, int x, int y, int z, World world){
		SelectSecondPosition(player, new Location(world, x, y, z));
	}
	
	public static void SelectSecondPosition(Player player, Location secondLocation){
		playerLoc2.put(player, secondLocation);
		secondLocation.getBlock().setType(Material.GLOWSTONE);
		blockbreakeloc.add(secondLocation);
		printInfo(player);
	}
	
	public static void clear(Player player){
		if(playerLoc1.containsKey(player)){
			playerLoc1.remove(player);
		}
		if(playerLoc2.containsKey(player)){
			playerLoc2.remove(player);
		}
	}
	
	public static boolean isSelectionSucces(Player player){
		if(playerLoc1.containsKey(player) && playerLoc2.containsKey(player)){
			if(playerLoc1.get(player).getWorld().getName().equalsIgnoreCase(playerLoc2.get(player).getWorld().getName())){
				return true;
			}else{
				return false;
			}
		}else{
			if(!playerLoc1.containsKey(player) && !playerLoc2.containsKey(player)){
				player.sendMessage("Must select two points");
				return false;
			}else if(!playerLoc1.containsKey(player)){
				player.sendMessage("Must select first point");
				return false;
			}else{
				player.sendMessage("Must select second point");
				return false;
			}
		}
	}
	
	public static void printInfo(Player player){
		if(playerLoc1.containsKey(player) && playerLoc2.containsKey(player)){
			player.sendMessage("loc1" + " - "+ playerLoc1.get(player).getBlockX() + " - "+ playerLoc1.get(player).getBlockY() +  " - "+ playerLoc1.get(player).getBlockZ());
			player.sendMessage("loc2" + " - "+ playerLoc2.get(player).getBlockX() + " - "+ playerLoc2.get(player).getBlockY() +  " - "+ playerLoc2.get(player).getBlockZ());
		}
	}
	
	public static Location getFirstPosition(Player p){
		if(playerLoc1.containsKey(p)){
			return playerLoc1.get(p);
		}
		return null;
	}
	
	public static Location getSecondPosition(Player p){
		if(playerLoc2.containsKey(p)){
			return playerLoc2.get(p);
		}
		return null;
	}
	
}
