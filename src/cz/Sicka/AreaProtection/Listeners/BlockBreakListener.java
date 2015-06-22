package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class BlockBreakListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockBreakeEvent(BlockBreakEvent event){
		if(cz.Sicka.AreaProtection.AreaProtection.isEnableWorld(event.getBlock().getWorld().getName())){
			breakBlock(event.getPlayer(), event.getBlock().getLocation(), event);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        Player player = null;
        Entity remover = event.getRemover();
        if (remover instanceof Player) {
            player = (Player) remover;
        } else if (remover instanceof Projectile) {
            Projectile projectile = (Projectile) remover;
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Player) {
                player = (Player) shooter;
            }
        }
        if (player != null) {
            breakBlock(player, event.getEntity().getLocation(), event);
        }
    }
	
	private void breakBlock(Player player, Location location, Cancellable cancellable) {
		if(!AreaProtectionAPI.getAreaProtectionManager().allowAction(player, location, FlagManager.DESTROY)){
			player.sendMessage("You Havent perm");
			cancellable.setCancelled(true);
		}
	}
}
