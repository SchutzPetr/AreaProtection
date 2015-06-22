package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class BucketListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        Flag flag = null;
        if (event.getBlockClicked().getType() == Material.WATER) {
            flag = FlagManager.WATERBUCKET;
        }
        if (event.getBlockClicked().getType() == Material.LAVA) {
            flag = FlagManager.LAVABUCKET;
        }
        if (flag == null) {
            return;
        }
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getPlayer(), event.getBlockClicked().getLocation(), flag)){
            event.setCancelled(true);
            event.getPlayer().sendMessage("ffff");
        }else{
        	event.getPlayer().sendMessage("ssss");
        }
    }
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Flag flag = null;
        if (event.getBucket() == Material.LAVA_BUCKET) {
            flag = FlagManager.LAVABUCKET;
        }
        if (event.getBucket() == Material.WATER_BUCKET) {
            flag = FlagManager.WATERBUCKET;
        }
        if (flag == null) {
            return;
        }
        BlockFace face = event.getBlockFace();
        Location blockLocation = event.getBlockClicked().getLocation().add(face.getModX(), face.getModY(), face.getModZ());
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getPlayer(), blockLocation, flag)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("ffff");
        }else{
        	event.getPlayer().sendMessage("ssss");
        }
    }
}
