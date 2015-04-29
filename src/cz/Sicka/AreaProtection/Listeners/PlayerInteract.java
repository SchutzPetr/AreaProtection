package cz.Sicka.AreaProtection.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import cz.Sicka.AreaProtection.Utils.Selection;

public class PlayerInteract implements Listener{
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(player.getItemInHand().getType() == Material.STICK && player.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.RED + "AreaProtection")){
			if(event.getAction() ==  Action.LEFT_CLICK_BLOCK){
				event.getPlayer().sendMessage("F");
				Selection.SelectFirstPosition(player, event.getClickedBlock().getLocation());
			}else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				event.getPlayer().sendMessage("S");
				Selection.SelectSecondPosition(player, event.getClickedBlock().getLocation());
			}
		}
	}
}