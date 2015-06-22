package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;
import cz.Sicka.AreaProtection.Configuration.Action.EditArea;
import cz.Sicka.AreaProtection.Configuration.Action.SaveArea;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class PlayerSetCommand {
	
	@SuppressWarnings("deprecation")
	public static boolean onPlayerSetCommand(Player sender, String areaName, String playerToChangeFlag, String flagToChange, String tf){
		if(!CommandsUtils.isAreaExist(areaName)){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotExist, CommandMessageType.Error);
			return true;
		}
		return onPlayerSetCommand(sender, Manager.getArea(areaName), Bukkit.getOfflinePlayer(playerToChangeFlag), flagToChange, tf);
	}
	
	@SuppressWarnings("deprecation")
	public static boolean onPlayerSetCommand(Player sender, Area area, String playerToChangeFlag, String flagToChange, String tf) {
		return onPlayerSetCommand(sender, area, Bukkit.getOfflinePlayer(playerToChangeFlag), flagToChange, tf);
	}
	
	public static boolean onPlayerSetCommand(Player sender, Area area, OfflinePlayer playerToChangeFlag, String flagToChange, String tf) {
		boolean value = false;
		boolean remove = false;
		Flag flag;
		if(!area.isOwner(sender.getUniqueId())){
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.AreaNotPermission, CommandMessageType.NotPerm);
			return true;
		}
		if(CommandsUtils.isValidFlag(flagToChange)){
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
			area.getAreaPlayerFlags().removePlayerFlag(sender.getUniqueId(), flag);
			EditArea.removePlayerFlag(area, sender, flag.getName());
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesFlagSet, CommandMessageType.Succes);
			return false;
		}else{
			area.getAreaPlayerFlags().addPlayerFlag(playerToChangeFlag.getUniqueId(), flag, value);
			CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesFlagSet, CommandMessageType.Succes);
		}
		SaveArea.savePlayerFlags(area);
		return false;
	}
}
