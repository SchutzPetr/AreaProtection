package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;

public class RemoveCommand {
	
	
	public static boolean onRemoveCommand(Player player, String args){
		Area a = AreaManager.getArea(args);
		if(a == null){
			return true;
		}
		if(!AreaProtectionAPI.getAreaProtectionManager().isPlayerOwner(player, a)){
			return true;
		}
		AreaManager.removeArea(player, a);
		return true;
	}
}
