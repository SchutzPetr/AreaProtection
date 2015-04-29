package cz.Sicka.AreaProtection.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AreaProtectionCommandManager implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmds, String commandLabel, String[] args) {
		String cmd = cmds.getName();
		if(sender instanceof Player){
			Player player = (Player) sender;
			return AreaProtectionPlayerCommand.onAreaProtectionPlayerCommand(player, cmd, commandLabel, args);
		}
		return false;
	}
}
