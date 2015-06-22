package cz.Sicka.AreaProtection.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Commands.Commands.ConversionCommand;
import cz.Sicka.AreaProtection.Commands.Commands.ConversionCommand.ConversionType;
import cz.Sicka.AreaProtection.Utils.Replacer;

public class AreaProtectionCommandManager implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmds, String commandLabel, String[] args) {
		String cmd = cmds.getName();
		if(sender instanceof Player){
			Player player = (Player) sender;
			return AreaProtectionPlayerCommand.onAreaProtectionPlayerCommand(player, cmd, commandLabel, args);
		}else{
			ConsoleCommandSender console = (ConsoleCommandSender)sender;
			if(cmd.equalsIgnoreCase("areaprotection")){
				if((args == null) || (args.length < 1)){
					console.sendMessage(Replacer.replace("&7++++&2-----------[ &6" + AreaProtection.getInstance().getDescription().getName() + "&2 ]-----------&7++++"));
					return true;
				}else if(args[0].equalsIgnoreCase("convert")){
					if(args.length == 2){
						if(ConversionType.valueOf(args[1].toUpperCase()) != null){
							return ConversionCommand.Convert(ConversionType.valueOf(args[1].toUpperCase()));
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}
}
