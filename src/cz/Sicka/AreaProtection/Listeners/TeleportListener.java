package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.ListenersUtils;

public class TeleportListener implements Listener {
	
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (!ListenersUtils.canSpawn(player, event.getTo())) {
            event.setCancelled(true);
            return;
        }
        if (AreaProtectionAPI.getAreaProtectionManager().allowAction(player, event.getTo(), FlagManager.TELEPORT)) {
            event.setCancelled(true);
            return;
        }
        cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(event.getPlayer(), event.getFrom(), event.getTo());
    }
}
