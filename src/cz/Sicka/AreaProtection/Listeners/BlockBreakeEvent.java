package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;

public class BlockBreakeEvent implements Listener {
	/*
	public void onBlockBreakeEventT(BlockBreakEvent e){
		Area a = AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(e.getBlock().getLocation());
		if(a != null){
			if(AreaProtectionAPI.getAreaProtectionManager().isPlayerHasPermissionToActionInArea(e.getPlayer(), a, "Breake")){
				e.getPlayer().sendMessage("[AreaProtection][DEBUG] You can breake this block");
				e.getPlayer().sendMessage("[AreaProtection][DEBUG] " + a.getAreaName());
			}else{
				e.getPlayer().sendMessage("[AreaProtection][DEBUG] You can´nt breake this block");
				e.getPlayer().sendMessage("[AreaProtection][DEBUG] " + a.getAreaName());
				e.setCancelled(true);
			}
		}else{
			e.getPlayer().sendMessage("[AreaProtection][DEBUG] You can´nt breake this block");
			e.getPlayer().sendMessage("[AreaProtection][DEBUG] " + "WorldArea");
		}
		WorldArea wa = AreaProtectionAPI.getAreaProtectionManager().getWorldArea(e.getPlayer().getLocation().getWorld().getName());
	}*/
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreakeEvent(BlockBreakEvent e){
		if(cz.Sicka.AreaProtection.AreaProtection.isEnableWorld(e.getBlock().getWorld().getName())){
			if(SelectionManager.getProtectedLocations().contains(e.getBlock().getLocation())){
				e.setCancelled(true);
				e.getPlayer().sendMessage("You cant breake this block");
			}else{
				Area a = AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(e.getBlock().getLocation());
				if(!a.allowAction(e.getPlayer().getUniqueId(), FlagManager.getFlag("Build"))){
					e.getPlayer().sendMessage("You Havent perm");
					e.setCancelled(true);
				}else{
					e.getPlayer().sendMessage("You Have perm");
					
					e.getPlayer().sendMessage(a.getName());
				}
			}
		}
	}
}
