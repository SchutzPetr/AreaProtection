package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class VehicleMoveListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
        Entity vehicle = event.getVehicle();
        Entity passenger = vehicle.getPassenger();
        if (!(passenger instanceof Player)) {
            return;
        }
        Player player = (Player) passenger;
        Location loc = event.getTo();
        Area area = AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(event.getTo());
        if (area == null) {
        	 cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(player, event.getFrom(), event.getTo());
            return;
        }
        if (!AreaProtectionAPI.getAreaProtectionManager().allowAction(player, area, FlagManager.VEHICLEMOVE)) {
            vehicle.setVelocity(new Vector(0,0,0));
            vehicle.teleport(event.getFrom());
            return;
        }

        cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(player, event.getFrom(), loc);
    }
}
