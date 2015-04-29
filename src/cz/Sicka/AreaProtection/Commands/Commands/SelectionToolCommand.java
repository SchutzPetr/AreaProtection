package cz.Sicka.AreaProtection.Commands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SelectionToolCommand {
	
	public static void onSelectionToolCommand(Player player){
		ItemStack stick = new ItemStack(Material.STICK, 1);
		ItemMeta stickMeta = stick.getItemMeta();
		stickMeta.setDisplayName(ChatColor.RED + "AreaProtection");
		stick.setItemMeta(stickMeta);
		player.getInventory().addItem(stick);
	}
}
