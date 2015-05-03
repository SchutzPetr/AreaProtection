package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area.AreaType;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Selections.Selection;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;


public class CreationCommand {
	
	public static void onCreationCommand(Player player, String name){
		if(AreaManager.getAreas().containsKey(name)){
			return;
		}
		if(!SelectionManager.alreadyInSelection(player)){
			return;
		}
		Selection sel = SelectionManager.getPlayerSelection(player);
		if(!sel.isSelectionComplete()){
			return;
		}
		AreaManager.createArea(AreaType.PLAYER_AREA, player.getUniqueId().toString(), name, sel.getFirstPosition(player), sel.getSecondPosition(player), sel.getFirstPosition(player).getWorld().getName());
		Messages.sendMessage(player, Lang.SuccesCreate);
	}

}
