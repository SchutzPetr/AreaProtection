package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;
import cz.Sicka.AreaProtection.Configuration.Action.EditArea;
import cz.Sicka.AreaProtection.Configuration.Action.SaveArea;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class SetCommand {
	
	public static boolean onSetCommand(Player sender, String areaName, String flagToChange, String tf){
		if(!CommandsUtils.isAreaExist(areaName)){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotExist, CommandMessageType.Error);
			return true;
		}
		return onSetCommand(sender, Manager.getArea(areaName), flagToChange, tf);
	}
	
	public static boolean onSetCommand(Player sender, Area area, String flagToChange, String tf) {
		boolean value = false;
		boolean remove = false;
		Flag flag;
		if(!area.isOwner(sender.getUniqueId())){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotPermission, CommandMessageType.NotPerm);
			return true;
		}
		if(!CommandsUtils.isValidFlag(flagToChange)){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.NotValidFlag, CommandMessageType.Error);
			return true;
		}
		flag = FlagManager.getFlag(flagToChange);
		if(tf.equals("t") || tf.equals("true")){
			value = true;
		}else if(tf.equals("f") || tf.equals("false")){
			value = false;
		}else if(tf.equals("r") || tf.equals("remove")){
			remove = true;
		}else{
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.NotValidBoolean, CommandMessageType.Error);
		}
		flag = FlagManager.getFlag(flagToChange);
		if(remove){
			area.getAreaFlags().removeFlag(flag);
			EditArea.removeAreaFlag(area, flag.getName());
			
			area.getAreaPlayerFlags().removePlayerFlag(sender.getUniqueId(), flag);
			EditArea.removePlayerFlag(area, sender, flag.getName());
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesFlagSet, CommandMessageType.Succes);
			return false;
		}else{
			area.getAreaFlags().addFlag(flag, value);
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesFlagSet, CommandMessageType.Succes);
		}
		SaveArea.saveFlags(area);
		return false;
	}
}
