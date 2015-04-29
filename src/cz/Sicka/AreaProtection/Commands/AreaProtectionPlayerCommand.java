package cz.Sicka.AreaProtection.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Commands.Commands.CreationCommand;
import cz.Sicka.AreaProtection.Commands.Commands.SelectionToolCommand;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Messages;

public class AreaProtectionPlayerCommand {
	
	public static boolean onAreaProtectionPlayerCommand(Player player, String cmd, String commandLabel, String[] args){
		if(cmd.equalsIgnoreCase("areaprotection")){
			if((args == null) || (args.length < 1)){
				return true;
			}else if(args[0].equalsIgnoreCase("gettool")){
				SelectionToolCommand.onSelectionToolCommand(player);
			}else if(args[0].equalsIgnoreCase("create")){
				if((args == null) || (args.length < 2)){
					Messages.sendMessage(player, Lang.CreationUsageCommand);
				}else if(args.length == 2){
					CreationCommand.onCreationCommand(player, args[1]);
				}else{
					Messages.sendMessage(player, Lang.CreationUsageCommand);
				}
			}
		}
		return false;
	}
}
