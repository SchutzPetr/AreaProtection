package cz.Sicka.AreaProtection.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Commands.Commands.CreationCommand;
import cz.Sicka.AreaProtection.Commands.Commands.RemoveCommand;
import cz.Sicka.AreaProtection.Commands.Commands.SelectCommand;
import cz.Sicka.AreaProtection.Commands.Commands.SelectionToolCommand;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Utils;

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
			}else if(args[0].equalsIgnoreCase("select")){
				if((args == null) || (args.length < 2) || (args.length > 4) || (args.length == 3)){
					player.sendMessage("err");
				}else if(args.length == 2){
					if(Utils.isInteger(args[1])){
						SelectCommand.onSelectCommand(player, Integer.parseInt(args[1]));
					}else{
						player.sendMessage("must be int");
					}
				}else if(args.length == 4){
					if(Utils.isInteger(args[1]) && Utils.isInteger(args[2]) && Utils.isInteger(args[3])){
						SelectCommand.onSelectCommand(player,  Integer.parseInt(args[1]),  Integer.parseInt(args[2]),  Integer.parseInt(args[3]));
					}else if(Utils.isInteger(args[1]) && args[2].equals("vert") && Utils.isInteger(args[3])){
						SelectCommand.onSelectCommand(player,  Integer.parseInt(args[1]),  "vert",  Integer.parseInt(args[3]));
					}else{
						player.sendMessage("err");
					}
				}
				//-/area flag -p Sicka TestArea build t
				//-/area flag -p Sicka build t
				//-/area flag -a TestArea build t
			}else if(args[0].equalsIgnoreCase("flag")){
				//-/area remove <area>
			}else if(args[0].equalsIgnoreCase("remove")){
				if((args == null) || !(args.length == 2)){
					return true;
				}else{
					return RemoveCommand.onRemoveCommand(player, args[1]);
				}
			}
		}
		return false;
	}
}
