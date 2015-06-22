package cz.Sicka.AreaProtection.Utils.Selections;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SelectionManager {
	private static Map<Player, Selection> playerSelection = new HashMap<Player, Selection>();
	public static String SelectionTool = ChatColor.RED + "AreaProtection" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Selection tool";
	
	public static boolean alreadyInSelection(Player player){
		return playerSelection.containsKey(player);
	}
	
	public static Selection getPlayerSelection(Player player){
		return playerSelection.get(player);
	}
	
	public static void addSelection(Player player, Selection selection){
		if(playerSelection.containsKey(player)){
			playerSelection.remove(player);
		}
		playerSelection.put(player, selection);
	}

	public static Map<Player, Selection> getPlayersSelections() {
		return playerSelection;
	}
	
	public static void clearAll(){
		for(Selection selection : playerSelection.values()){
			selection.clear();
		}
	}
	
	public static void SelectFirstPosition(Player player, Location location) {
		if(alreadyInSelection(player)){
			getPlayerSelection(player).SelectFirstPosition(location);
		}else{
			Selection sel = new Selection(player);
			sel.SelectFirstPosition(location);
			addSelection(player, sel);
		}
	}
	
	public static void SelectSecondPosition(Player player, Location location) {
		if(alreadyInSelection(player)){
			getPlayerSelection(player).SelectSecondPosition(location);
		}else{
			Selection sel = new Selection(player);
			sel.SelectSecondPosition(location);
			addSelection(player, sel);
		}
	}
}
