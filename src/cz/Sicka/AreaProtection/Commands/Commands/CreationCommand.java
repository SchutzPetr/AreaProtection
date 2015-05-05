package cz.Sicka.AreaProtection.Commands.Commands;

import java.util.List;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Area.AreaType;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Selections.Selection;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;


public class CreationCommand {
	
	public static void onCreationCommand(Player player, String name){
		if(Manager.isAreaExist(name)){
			return;
		}
		if(!SelectionManager.alreadyInSelection(player)){
			return;
		}
		Selection sel = SelectionManager.getPlayerSelection(player);
		if(!sel.isSelectionComplete()){
			return;
		}
		List<Area> areas = AreaManager.getColisionAreas(sel.getFirstPosition(player), sel.getSecondPosition(player));
		if(!areas.isEmpty() || areas.size() != 0){
			player.sendMessage("Oblast v kolizi s: ");
			for(Area a : areas){
				player.sendMessage(a.getName());
			}
			return;
		}
		AreaManager.createArea(AreaType.PLAYER_AREA, player.getUniqueId().toString(), name, sel.getFirstPosition(player), sel.getSecondPosition(player), sel.getFirstPosition(player).getWorld().getName());
		Messages.sendMessage(player, Lang.SuccesCreate);
	}

}
