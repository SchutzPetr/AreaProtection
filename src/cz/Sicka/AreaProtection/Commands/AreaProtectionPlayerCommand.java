package cz.Sicka.AreaProtection.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.API.AreaProtectionAPI;
import cz.Sicka.AreaProtection.Commands.Commands.CreateSubzoneCommand;
import cz.Sicka.AreaProtection.Commands.Commands.CreationCommand;
import cz.Sicka.AreaProtection.Commands.Commands.InfoCommand;
import cz.Sicka.AreaProtection.Commands.Commands.PlayerSetCommand;
import cz.Sicka.AreaProtection.Commands.Commands.RemoveCommand;
import cz.Sicka.AreaProtection.Commands.Commands.SelectCommand;
import cz.Sicka.AreaProtection.Commands.Commands.TPCommand;
import cz.Sicka.AreaProtection.Commands.Commands.ToolsCommand;
import cz.Sicka.AreaProtection.Commands.Commands.SetCommand;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Replacer;
import cz.Sicka.AreaProtection.Utils.Utils;
import cz.Sicka.Core.Core;

public class AreaProtectionPlayerCommand {
	
	public static boolean onAreaProtectionPlayerCommand(Player player, String cmd, String commandLabel, String[] args){
		if(cmd.equalsIgnoreCase("areaprotection")){
			if((args == null) || (args.length < 1)){
				player.sendMessage(Replacer.replace("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++"));
				Messages.sendRawMessage(player, CommandsUtils.areaCreateCommand());
				Messages.sendRawMessage(player, CommandsUtils.areaRemoveCommand());
				Messages.sendRawMessage(player, CommandsUtils.areaInfoCommand());
				Messages.sendRawMessage(player, CommandsUtils.getToolsCommand());
				Messages.sendRawMessage(player, CommandsUtils.areaPlayerSetCommand());
				Messages.sendRawMessage(player, CommandsUtils.areaSetCommand());
				Messages.sendRawMessage(player, CommandsUtils.areaSelectCommand());
				return true;
			}else if(args[0].equalsIgnoreCase("tp")){
				if(args.length == 2){
					TPCommand.onTPCommand(args[1], player);
					return true;
				}else{
					return true;
				}
			}else if(args[0].equalsIgnoreCase("gettools")){
				ToolsCommand.onToolsCommand(player);
			}else if(args[0].equalsIgnoreCase("create")){
				if((args == null) || (args.length < 2)){
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area create <jmeno> - vytvori oblast");
				}else if(args.length == 2){
					CreationCommand.createArea(Core.getUserManager().getUser(player.getUniqueId()), args[1]);
				}else{
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area create <jmeno> - vytvori oblast");
				}
			}else if(args[0].equalsIgnoreCase("select")){
				if((args == null) || (args.length < 2) || (args.length > 4) || (args.length == 3)){
					player.sendMessage("err");
				}else if(args.length == 2){
					if(Utils.isInteger(args[1])){
						SelectCommand.onSelectCommand(player, Integer.parseInt(args[1]));
					}else{
						Messages.sendMessage(player, CommandsUtils.MustBeInt);
					}
				}else if(args.length == 4){
					if(Utils.isInteger(args[1]) && Utils.isInteger(args[2]) && Utils.isInteger(args[3])){
						SelectCommand.onSelectCommand(player,  Integer.parseInt(args[1]),  Integer.parseInt(args[2]),  Integer.parseInt(args[3]));
					}else if(Utils.isInteger(args[1]) && args[2].equals("vert") && Utils.isInteger(args[3])){
						SelectCommand.onSelectCommand(player,  Integer.parseInt(args[1]),  "vert",  Integer.parseInt(args[3]));
					}else{
						player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
						player.sendMessage("/area select <radius> - Vybere vertikální oblast. Radius = vzdalenost mezi hranici oblasti a støedem");
						player.sendMessage("/area select <x> <z> - Vybere vertikální oblast. x/z = vzdalenost mezi x/z hranici oblasti a støedem");
						player.sendMessage("/area select <x> <y> <z> - Vybere oblast. x/z = vzdalenost mezi x/z hranici oblasti a støedem. y = výška/hloubka oblasti");
					}
				}
				//-/area pset <area> <hrac> build t
			}else if(args[0].equalsIgnoreCase("createsubzone")){
				if((args == null) || (args.length < 2)){
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area create <jmeno> - vytvori oblast");
				}else if(args.length == 2){
					CreateSubzoneCommand.onCreateSubzoneCommand(player, args[1]);
				}else{
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area create <jmeno> - vytvori oblast");
				}
			}else if(args[0].equalsIgnoreCase("pset")){
				//-/area pset <hrac> build t
				if(args.length == 4){
					return PlayerSetCommand.onPlayerSetCommand(player, AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(player.getLocation()), args[1], args[2], args[3]);
				//-/area pset <area> <hrac> build t
				}else if(args.length == 5){
					return PlayerSetCommand.onPlayerSetCommand(player, args[1], args[2], args[3], args[4]);
				}else{
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area pset <jmeno oblasti> <jmeno hrace> <flag> t/f/r(true/false/remove)- nastavení vlajek pro hrace");
				}
			}else if(args[0].equalsIgnoreCase("set")){
				//-/area 0 1 2
				//-/area set build t
				if(args.length == 3){
					return SetCommand.onSetCommand(player,  AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(player.getLocation()), args[1], args[2]);
					//-/area 0 <1> 2 3
					//-/area set <area> build t
				}else if(args.length == 4){
					return SetCommand.onSetCommand(player, args[1], args[2], args[3]);
				}else{
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area set <jmeno oblasti> <flag> t/f/r(true/false/remove)- nastavení vlajek pro oblast");
				}
			}else if(args[0].equalsIgnoreCase("remove")){
				if((args == null) || !(args.length == 2)){
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area remove <jmeno> - odstrani oblast");
					return true;
				}else{
					return RemoveCommand.onRemoveCommand(player, args[1]);
				}
			}else if(args[0].equalsIgnoreCase("info")){
				if((args == null) || !(args.length == 2) && !(args.length ==1)){
					player.sendMessage("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++");
					player.sendMessage("/area remove <jmeno> - odstrani oblast");
					return true;
				}else if(args.length == 1){
					InfoCommand.onInfoCommand(AreaProtectionAPI.getAreaProtectionManager().getPermissionArea(player.getLocation()), player);
					return true;
				}else{
					InfoCommand.onInfoCommand(args[1], player);
					return true;
				}
			}
		}
		return false;
	}
}
