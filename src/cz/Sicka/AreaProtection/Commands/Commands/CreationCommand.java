package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.Messages;
import cz.Sicka.AreaProtection.Utils.Selection;

public class CreationCommand {
	
	public static void onCreationCommand(Player player, String name){
		if(AreaManager.getAreas().containsKey(name)){
			return;
		}
		if(!Selection.isSelectionSucces(player)){
			return;
		}
		AreaProtection.getInstance().getConfigurationManager().createPlayerArea(player, name, Selection.getFirstPosition(player), Selection.getSecondPosition(player), Selection.getFirstPosition(player).getWorld().getName());
		Messages.sendMessage(player, Lang.SuccesCreate);
	}

}
