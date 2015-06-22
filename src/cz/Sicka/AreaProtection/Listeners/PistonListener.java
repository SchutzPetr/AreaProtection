package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class PistonListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getBlock().getLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
            return;
        }
        if (!event.isSticky()) {
            return;
        }
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getRetractLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getBlock().getLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
            return;
        }
        for (Block block : event.getBlocks()) {
            if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(block.getLocation(), FlagManager.PISTON)) {
                event.setCancelled(true);
                return;
            }
            Location blockto = block.getLocation();
            blockto.setX(blockto.getX() + event.getDirection().getModX());
            blockto.setY(blockto.getY() + event.getDirection().getModY());
            blockto.setZ(blockto.getZ() + event.getDirection().getModZ());
            if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(blockto, FlagManager.PISTON)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
