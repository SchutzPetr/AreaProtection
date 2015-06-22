package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.Settings;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;

public class SelectCommand {
	
	public static void onSelectCommand(Player sender, int xsize, int ysize, int zsize){
		Location loc = sender.getLocation();
		Location loc1 = new Location(loc.getWorld(), loc.getBlockX() + xsize, loc.getBlockY() + ysize, loc.getBlockZ() + zsize);
	    Location loc2 = new Location(loc.getWorld(), loc.getBlockX() - xsize, loc.getBlockY() - ysize, loc.getBlockZ() - zsize);
	    SelectionManager.SelectFirstPosition(sender, loc1);
	    SelectionManager.SelectSecondPosition(sender, loc2);
	    CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesSelected, CommandMessageType.Succes);
	}

	public static void onSelectCommand(Player player, int radius) {
		onSelectCommand(player, radius, "vert", radius);
	}

	public static void onSelectCommand(Player sender, int xsize, String vert, int zsize) {
		if(!vert.equals("vert")){
			return;
		}
		Location loc = sender.getLocation();
		Location hloc = new Location(loc.getWorld(), loc.getBlockX() + xsize, Settings.getMaxY(), loc.getBlockZ() + zsize);
	    Location lloc = new Location(loc.getWorld(), loc.getBlockX() - xsize, Settings.getMinY(), loc.getBlockZ() - zsize);
	    SelectionManager.SelectFirstPosition(sender, hloc);
	    SelectionManager.SelectSecondPosition(sender, lloc);
	    CommandsUtils.sendCommandMessage(sender, CommandsUtils.SuccesSelected, CommandMessageType.Succes);
	}

}
