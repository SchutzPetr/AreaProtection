package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class FlowListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        Material mat = event.getBlock().getType();
        Flag flag = null;
        if (mat == Material.LAVA || mat == Material.STATIONARY_LAVA) {
            flag = FlagManager.LAVAFLOW;
        }
        if (mat == Material.WATER || mat == Material.STATIONARY_WATER) {
            flag = FlagManager.WATERFLOW;
        }
        if (flag == null) {
            return;
        }
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getBlock().getLocation(), flag)) {
            event.setCancelled(true);
        }
    }
}
