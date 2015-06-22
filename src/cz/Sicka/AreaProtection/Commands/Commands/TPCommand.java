package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;

public class TPCommand {
	
	public static boolean onTPCommand(String areaName, Player sender){
		if(!CommandsUtils.isAreaExist(areaName)){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotExist, CommandMessageType.Error);
			return true;
		}
		Area area = Manager.getArea(areaName);
		if(!area.isOwner(sender.getUniqueId())){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotPermission, CommandMessageType.NotPerm);
			return true;
		}
		if(!area.getTeleportLocation().getChunk().isLoaded()){
			area.getTeleportLocation().getChunk().load();
		}
		sender.teleport(area.getTeleportLocation(), TeleportCause.COMMAND);
		return true;
	}

}
