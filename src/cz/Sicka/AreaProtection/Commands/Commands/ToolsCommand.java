package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;

public class ToolsCommand {
	
	public static void onToolsCommand(Player player){
		ItemStack stick = new ItemStack(Material.STICK, 1);
		ItemMeta stickMeta = stick.getItemMeta();
		stickMeta.setDisplayName(SelectionManager.SelectionTool);
		stick.setItemMeta(stickMeta);
		player.getInventory().addItem(stick);
	}
}
