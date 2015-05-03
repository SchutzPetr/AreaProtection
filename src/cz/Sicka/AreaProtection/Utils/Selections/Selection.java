package cz.Sicka.AreaProtection.Utils.Selections;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Utils.Replacer;

@SuppressWarnings("deprecation")
public class Selection {
	private Player player;
	
	private Hologram h1;
	private Hologram h2;
	
	private Hologram cent;
	
	private Location loc1;
	private Location loc2;
	
	private Block block1;
	private Block block2;
	
	private Block central;

	private int id;
	
	public Selection(Player p){
		this.player =  p;
	}
	
	public Location getCenter() {
        return new Location(this.loc1.getWorld(), (loc1.getBlockX() + loc2.getBlockX()) / 2, (loc1.getBlockY() + loc2.getBlockY()) / 2, (loc1.getBlockZ() + loc2.getBlockZ()) / 2);
    }
	
	public void SelectFirstPosition(int x, int y, int z, World world){
		SelectFirstPosition(new Location(world, x, y, z));
	}
	
	public void SelectFirstPosition(Location firstLocation){
		if(this.loc1 != null){
			block1.getState().update();
			this.central.getState().update();
			h1.delete();
			if(cent != null)cent.delete();
			SelectionManager.removeProtectedLocation(this.loc1);
		}
		if(this.loc2 != null){
			if(this.loc2.equals(firstLocation)){
				player.sendMessage("Collision");
				return;
			}
		}
		h1 = HologramsAPI.createHologram(AreaProtection.getInstance(), firstLocation.clone().add(0, 3, 0));
		h1.appendTextLine(Replacer.replace("&5AreaProtection"));
		h1.appendTextLine(Replacer.replace("&aFirst Selection Point"));
		h1.appendTextLine(Replacer.replace("&3X: " + firstLocation.getBlockX()));
		h1.appendTextLine(Replacer.replace("&3Y: " + firstLocation.getBlockY()));
		h1.appendTextLine(Replacer.replace("&3Z: " + firstLocation.getBlockZ()));
		this.loc1 = firstLocation;
		this.block1 = firstLocation.getBlock();
		delaySendBlockChange(firstLocation, Material.GLOWSTONE);
		SelectionManager.addProtectedLocation(firstLocation);
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 20, 1);
		printSelectionInfo();
		delayClear();
	}
	
	public void printSelectionInfo(){
		System.out.print(isSelectionComplete());
		if(!isSelectionComplete()){
			return;
		}
		System.out.print(isSelectionComplete());
		System.out.print(getCenter().getBlockX());
		System.out.print(getCenter().getBlockY());
		System.out.print(getCenter().getBlockZ());
		this.central = getCenter().getBlock();
		delaySendBlockChange(getCenter(), Material.GLOWSTONE);
		cent = HologramsAPI.createHologram(AreaProtection.getInstance(), getCenter().clone().add(0, 3, 0));
		cent.appendTextLine(Replacer.replace("&5AreaProtection"));
		cent.appendTextLine(Replacer.replace("&aCenter"));
		cent.appendTextLine(Replacer.replace("&3X: " + getCenter().getBlockX()));
		cent.appendTextLine(Replacer.replace("&3Y: " + getCenter().getBlockY()));
		cent.appendTextLine(Replacer.replace("&3Z: " + getCenter().getBlockZ()));
	}
	
	public boolean isSelectionComplete(){
		if(this.loc1 != null && this.loc2 != null){
			return this.loc1.getWorld().getName().equalsIgnoreCase(this.loc2.getWorld().getName());
		}else{
			return false;
		}
	}
	
	private void delaySendBlockChange(final Location loc, final Material mat){
		delaySendBlockChange(loc, mat, (byte)0);
	}
	
	private void delaySendBlockChange(final Location loc, final Material mat, final byte data){
		Bukkit.getScheduler().scheduleSyncDelayedTask(AreaProtection.getInstance(), new Runnable(){

			@Override
			public void run() {
				player.sendBlockChange(loc, mat, data);
			}

		}, 5L);
	}
	
	private void delayClear(){
		if(Bukkit.getScheduler().isQueued(id)){
			Bukkit.getScheduler().cancelTask(id);
		}
		id = Bukkit.getScheduler().scheduleSyncDelayedTask(AreaProtection.getInstance(), new Runnable(){

			@Override
			public void run() {
				clear();
			}

		}, 30*20L);
	}
	
	public void SelectSecondPosition(int x, int y, int z, World world){
		SelectSecondPosition(new Location(world, x, y, z));
	}
	
	public void SelectSecondPosition(Location secondLocation){
		if(this.loc2 != null){
			h2.delete();
			if(cent != null)cent.delete();
			block2.getState().update();
			this.central.getState().update();
			SelectionManager.removeProtectedLocation(this.loc2);
		}
		if(this.loc1 != null){
			if(this.loc1.equals(secondLocation)){
				player.sendMessage("Collision");
				return;
			}
		}
		h2 = HologramsAPI.createHologram(AreaProtection.getInstance(), secondLocation.clone().add(0, 3, 0));
		h2.appendTextLine(Replacer.replace("&5AreaProtection"));
		h2.appendTextLine(Replacer.replace("&aSecond Selection Point"));
		h2.appendTextLine(Replacer.replace("&3X: " + secondLocation.getBlockX()));
		h2.appendTextLine(Replacer.replace("&3Y: " + secondLocation.getBlockY()));
		h2.appendTextLine(Replacer.replace("&3Z: " + secondLocation.getBlockZ()));
		this.loc2 = secondLocation;
		this.block2 = secondLocation.getBlock();
		delaySendBlockChange(secondLocation, Material.GLOWSTONE);
		SelectionManager.addProtectedLocation(secondLocation);
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 20, 1);
		printSelectionInfo();
		delayClear();
	}
	
	public void clear(){
		this.block1.getState().update();
		this.block2.getState().update();
		this.central.getState().update();
		if(h1 != null)h1.delete();
		if(h2 != null)h2.delete();
		if(cent != null)cent.delete();
	}

	public Location getFirstPosition(Player player) {
		return this.loc1;
	}

	public Location getSecondPosition(Player player) {
		return this.loc2;
	}
}
