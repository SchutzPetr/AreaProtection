package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;

public class RemoveCommand {
	
	
	public static boolean onRemoveCommand(Player sender, String areaName){
		if(!CommandsUtils.isAreaExist(areaName)){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotExist, CommandMessageType.Error);
			return true;
		}
		Area area = Manager.getArea(areaName);
		if(!area.isOwner(sender.getUniqueId())){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotPermission, CommandMessageType.NotPerm);
			return true;
		}
		AreaManager.removeArea(sender, area);
		CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesRemove, CommandMessageType.Succes);
		return true;
	}
}
