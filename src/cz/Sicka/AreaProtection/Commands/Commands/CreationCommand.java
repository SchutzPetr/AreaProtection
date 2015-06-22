package cz.Sicka.AreaProtection.Commands.Commands;

import java.util.List;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Commands.CommandsUtils.CommandMessageType;
import cz.Sicka.AreaProtection.Utils.Selections.Selection;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;


public class CreationCommand {
	
	public static boolean onCreationCommand(Player player, String areaName){
		if(CommandsUtils.isAreaExist(areaName)){
			CommandsUtils.sendCommandMessage(player, CommandsUtils.AreaExist, CommandMessageType.Error);
			return true;
		}
		if(!SelectionManager.alreadyInSelection(player)){
			return true;
		}
		Selection sel = SelectionManager.getPlayerSelection(player);
		if(!sel.isSelectionComplete()){
			return true;
		}
		List<Area> areas = AreaManager.getColisionAreas(sel.getFirstPosition(), sel.getSecondPosition());
		if(areas == null)System.out.print(areas==null);
		if(!areas.isEmpty() || areas.size() != 0){
			CommandsUtils.sendCommandMessage(player, CommandsUtils.Colision, CommandMessageType.Error);
			for(Area a : areas){
				player.sendMessage(a.getAreaName());
			}
			return true;
		}
		AreaManager.createArea(AreaType.MAIN, player.getUniqueId().toString(), areaName, sel.getFirstPosition(), sel.getSecondPosition(), sel.getFirstPosition().getWorld().getName());
		CommandsUtils.sendCommandMessage(player, CommandsUtils.SuccesCreate, CommandMessageType.Succes);
		return true;
	}

}
