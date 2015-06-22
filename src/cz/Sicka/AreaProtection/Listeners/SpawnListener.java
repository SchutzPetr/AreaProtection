package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.Utils;

public class SpawnListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.CUSTOM /*&& ConfigManager.getInstance().ignorePluginSpawns()*/) {
            return;
        }
        EntityType type = event.getEntity().getType();
        if(type != EntityType.ARMOR_STAND && type != EntityType.ITEM_FRAME){
        	Flag flag = null;
            if (Utils.isAnimal(type)) {
                flag = FlagManager.ANIMALS;
            } else {
                flag = FlagManager.MONSTERS;
            }
            if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(event.getLocation(), flag)) {
                event.setCancelled(true);
            }
        }
    }
}
