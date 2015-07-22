package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Selections.Selection;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;

public class CreateSubzoneCommand {
	
	public static void onCreateSubzoneCommand(Player sender, String subzoneName){
		if(!SelectionManager.alreadyInSelection(sender)){
			Messages.sendMessage(sender, "Error! Vyber oblasti neni kompletni!");
			return;
		}
		Selection sel = SelectionManager.getPlayerSelection(sender);
		if(!sel.isSelectionComplete()){
			Messages.sendMessage(sender, "Error! Vyber oblasti neni kompletni!");
			return;
		}
		boolean succes = AreaManager.createArea(AreaType.SUBZONE, sender, subzoneName, sel.getFirstPosition(), sel.getSecondPosition(), sel.getFirstPosition().getWorld().getName());
		if(succes){
			Messages.sendMessage(sender, "Succes! Subzona uspesne vytvorena!");
		}else{
			Messages.sendMessage(sender, "Error! Nekde se stala chyba!");
		}
	}
}
