package cz.Sicka.AreaProtection.Commands.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class PlayerSetCommand {
	
	@SuppressWarnings("deprecation")
	public static boolean onPlayerSetCommand(Player sender, String areaName, String playerToChangeFlag, String flag, String tf){
		return onPlayerSetCommand(sender, Manager.getArea(areaName), Bukkit.getOfflinePlayer(playerToChangeFlag), flag, tf);
	}
	
	@SuppressWarnings("deprecation")
	public static boolean onPlayerSetCommand(Player sender, Area area, String playerToChangeFlag, String flags, String tf) {
		return onPlayerSetCommand(sender, area, Bukkit.getOfflinePlayer(playerToChangeFlag), flags, tf);
	}
	
	public static boolean onPlayerSetCommand(Player sender, Area area, OfflinePlayer offlinePlayer, String flags, String tf) {
		boolean value = false;
		Flag flag;
		if(tf.equals("t") || tf.equals("true")){
			value = true;
		}else if(tf.equals("f") || tf.equals("false")){
			value = false;
		}
		if(!area.isOwner(sender.getUniqueId())){
			return true;
		}
		if(FlagManager.getFlag(flags) == null){
			return true;
		}
		flag = FlagManager.getFlag(flags);
		area.getAreaPlayerFlags().addPlayerFlag(offlinePlayer.getUniqueId(), flag, value);
		return false;
	}
	
	public static boolean onPlayerSetCommand(Player sender, Area area, UUID offlinePlayer, String tf) {
		return false;
	}
}
