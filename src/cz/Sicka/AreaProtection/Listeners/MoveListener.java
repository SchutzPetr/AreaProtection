package cz.Sicka.AreaProtection.Listeners;

public class MoveListener implements org.bukkit.event.Listener {
	
	@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
		org.bukkit.Location to = event.getTo();
		if(cz.Sicka.AreaProtection.AreaProtection.isEnableWorld(to.getWorld().getName())){
			org.bukkit.Location from = event.getFrom();
			if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()){
				cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(event.getPlayer(), from, to);
			}else{
				
			}
		}
	}
}
