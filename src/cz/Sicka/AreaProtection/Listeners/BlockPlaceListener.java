package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.FlagManager;


public class BlockPlaceListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlaceEvent(BlockPlaceEvent event){
		if(cz.Sicka.AreaProtection.AreaProtection.isEnableWorld(event.getBlock().getWorld().getName())){
			if(!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getPlayer(), event.getBlock().getLocation(), FlagManager.PLACE)){
				event.getPlayer().sendMessage("You Havent perm");
				event.setCancelled(true);
			}else{
				event.getPlayer().sendMessage("You Have perm");
			}
		}
	}
}
