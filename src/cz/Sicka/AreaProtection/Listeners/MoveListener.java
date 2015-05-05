package cz.Sicka.AreaProtection.Listeners;

public class MoveListener implements org.bukkit.event.Listener {
	private java.util.Map<org.bukkit.entity.Player, cz.Sicka.AreaProtection.Area.Area> lastArea = new java.util.HashMap<org.bukkit.entity.Player, cz.Sicka.AreaProtection.Area.Area>();
	
	@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
		org.bukkit.Location to = event.getTo();
		if(cz.Sicka.AreaProtection.AreaProtection.isEnableWorld(to.getWorld().getName())){
			org.bukkit.Location from = event.getFrom();
			if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()){
				isChangeArea(event.getPlayer(), from, to);
			}else{
				
			}
		}
	}
	
	private void isChangeArea(org.bukkit.entity.Player player, org.bukkit.Location from, org.bukkit.Location to){
		cz.Sicka.AreaProtection.Area.Area ato = cz.Sicka.AreaProtection.API.AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(to);
		if(this.lastArea.containsKey(player)){
			cz.Sicka.AreaProtection.Area.Area afrom = this.lastArea.get(player);
			if(afrom == null){
				this.lastArea.remove(player);
				this.lastArea.put(player, ato);
				return;
			}
			if(!afrom.getName().equalsIgnoreCase(ato.getName())){
				this.lastArea.remove(player);
				this.lastArea.put(player, ato);
				cz.Sicka.AreaProtection.Compatibility.CompatibilityManager.sendActionBar(player, replace("&2Prave jsi vstoupil do oblasti &5<New_Area>&2, kterou vlastni &5<Owner>.", afrom, ato));
				return;
			}
			return;
		}else{
			this.lastArea.put(player, ato);
			return;
		}
		
	}
	
	private String replace(String toReplace, cz.Sicka.AreaProtection.Area.Area oldA, cz.Sicka.AreaProtection.Area.Area newA){
		if(toReplace == null){
			return null;
		}else{
			toReplace = toReplace.replace("<Old_Area>", oldA.getName());
			toReplace = toReplace.replace("<New_Area>", newA.getName());
			toReplace = toReplace.replace("<Owner>", newA.getOwnerName());
			toReplace = org.bukkit.ChatColor.translateAlternateColorCodes("&".charAt(0), toReplace);
			toReplace = toReplace.replace("<New_Line>", org.bukkit.ChatColor.RESET + "\n");
			return toReplace;
		}	
	}
}
