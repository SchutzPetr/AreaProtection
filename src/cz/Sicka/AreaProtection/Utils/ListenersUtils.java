package cz.Sicka.AreaProtection.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ListenersUtils {
	private static java.util.Map<org.bukkit.entity.Player, cz.Sicka.AreaProtection.Area.PermissionArea> lastArea = new java.util.HashMap<org.bukkit.entity.Player, cz.Sicka.AreaProtection.Area.PermissionArea>();
	
	public static void handleNewLocation(org.bukkit.entity.Player player, org.bukkit.Location from, org.bukkit.Location to){
		cz.Sicka.AreaProtection.Area.PermissionArea pato = cz.Sicka.AreaProtection.API.AreaProtectionAPI.getAreaProtectionManager().getPermissionArea(to);
		if(lastArea.containsKey(player)){
			cz.Sicka.AreaProtection.Area.PermissionArea pafrom = lastArea.get(player);
			if(pafrom == null){
				lastArea.remove(player);
				lastArea.put(player, pato);
				return;
			}
			if(!pafrom.getAreaName().equalsIgnoreCase(pato.getAreaName())){
				lastArea.remove(player);
				lastArea.put(player, pato);
				cz.Sicka.AreaProtection.Compatibility.CompatibilityManager.sendActionBar(player, replace("&2Prave jsi vstoupil do oblasti &5<New_Area>&2, kterou vlastni &5<Owner>.", pafrom, pato));
				return;
			}
			return;
		}else{
			lastArea.put(player, pato);
			return;
		}
	}
	
	/*public static void handleNewLocation(org.bukkit.entity.Player player, org.bukkit.Location from, org.bukkit.Location to){
		cz.Sicka.AreaProtection.Area.PermissionArea pato = cz.Sicka.AreaProtection.API.AreaProtectionAPI.getAreaProtectionManager().getPermissionArea(to);
	}*/
	
	private static String replace(String toReplace, cz.Sicka.AreaProtection.Area.PermissionArea oldA, cz.Sicka.AreaProtection.Area.PermissionArea newA){
		if(toReplace == null){
			return null;
		}else{
			toReplace = toReplace.replace("<Old_Area>", oldA.getAreaName());
			toReplace = toReplace.replace("<New_Area>", newA.getAreaName());
			toReplace = toReplace.replace("<Owner>", newA.getOwnerName());
			toReplace = org.bukkit.ChatColor.translateAlternateColorCodes("&".charAt(0), toReplace);
			toReplace = toReplace.replace("<New_Line>", org.bukkit.ChatColor.RESET + "\n");
			return toReplace;
		}	
	}

	public static boolean canSpawn(Player player, Location to) {
		// TODO Auto-generated method stub
		return false;
	}
}
