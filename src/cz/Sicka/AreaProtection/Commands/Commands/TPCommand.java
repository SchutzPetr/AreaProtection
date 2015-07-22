package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class TPCommand {
	
	public static void onTPCommand(String areaName, Player sender){
		if(areaName.contains(".")){
			String[] split = areaName.split("\\.");
			String mainArea = split[0];
			String sub = split[1];
			if(Manager.isAreaExist(mainArea)){
				Area area = Manager.getArea(mainArea);
				if(area.getSubzoneManager().isSubzoneExist(sub)){
					Subzone subzone = area.getSubzoneManager().getSubzone(sub);
					teleportToSubzone(sender, subzone);
				}else{
					sender.sendMessage("Subzone not exist!");
					return;
				}
			}else{
				sender.sendMessage("Area not exist!");
				return;
			}
		}else{
			if(Manager.isAreaExist(areaName)){
				Area area = Manager.getArea(areaName);
				teleportToArea(sender, area);
			}else{
				sender.sendMessage("Area not exist!");
				return;
			}
		}
	}
	
	public static void teleportToSubzone(Player sender, Subzone subzone){
		if(subzone.allowAction(sender.getUniqueId(), FlagManager.TELEPORT)){
			if(!subzone.getTeleportLocation().getChunk().isLoaded()){
				subzone.getTeleportLocation().getChunk().load();
			}
			sender.sendMessage("Teleporting....");
			sender.teleport(subzone.getTeleportLocation(), TeleportCause.COMMAND);
		}else{
			sender.sendMessage("Nejsi opravneny teleportovat se na tuto oblast!");
		}
	}
	
	public static void teleportToArea(Player sender, Area area){
		if(area.allowAction(sender.getUniqueId(), FlagManager.TELEPORT)){
			if(!area.getTeleportLocation().getChunk().isLoaded()){
				area.getTeleportLocation().getChunk().load();
			}
			sender.sendMessage("Teleporting....");
			sender.teleport(area.getTeleportLocation(), TeleportCause.COMMAND);
		}else{
			sender.sendMessage("Nejsi opravneny teleportovat se na tuto oblast!");
		}
	}

}
